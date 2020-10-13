package com.gmail.aazavoykin.rest.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final int MAX_PAYLOAD_LENGTH = 10000;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final var wrappedRequest = new ContentCachingRequestWrapper(request);
        final var wrappedResponse = new ContentCachingResponseWrapper(response);

        log.debug("Received " + wrappedRequest.getMethod()
                + " request on " + wrappedRequest.getRequestURL()
                + Optional.ofNullable(wrappedRequest.getQueryString()).orElse("")
                + Optional.ofNullable(getContentAsString(wrappedRequest.getContentAsByteArray(), request.getCharacterEncoding()))
                .map(payload -> ", payload = " + payload)
                .orElse(""));

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            log.debug("For " + wrappedRequest.getMethod()
                    + " request on " + wrappedRequest.getRequestURL()
                    + " responsed with code " + wrappedResponse.getStatus()
                    + Optional.ofNullable(getContentAsString(wrappedResponse.getContentAsByteArray(), response.getCharacterEncoding()))
                    .map(payload -> " and payload = " + payload)
                    .orElse("."));

            wrappedResponse.copyBodyToResponse();
        }
    }

    private String getContentAsString(byte[] byteArray, String charsetName) {
        if (byteArray == null || byteArray.length == 0) {
            return null;
        }

        final var charset = Optional.ofNullable(charsetName)
                .orElse(StandardCharsets.UTF_8.displayName());

        final int length = Math.min(byteArray.length, MAX_PAYLOAD_LENGTH);
        try {
            return new String(byteArray, 0, length, charset);
        } catch (UnsupportedEncodingException ex) {
            return "{ Could not read request payload }";
        }
    }

}
