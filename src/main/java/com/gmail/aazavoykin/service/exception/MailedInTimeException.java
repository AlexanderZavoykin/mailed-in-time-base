package com.gmail.aazavoykin.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MailedInTimeException extends RuntimeException {

    private final ErrorCode errorCode;

    private final String description;

}
