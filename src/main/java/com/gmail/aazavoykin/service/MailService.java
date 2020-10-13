package com.gmail.aazavoykin.service;

import com.gmail.aazavoykin.config.property.MailProperties;
import com.gmail.aazavoykin.data.enums.PlannedMessageStatus;
import com.gmail.aazavoykin.data.model.PlannedMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.EnumSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private static final String ATTACHMENT_NAME = "attachment";

    private static final String MESSAGE_SUFFIX = "\n\nThis message was sent automatically. Do not answer it.";

    private final JavaMailSender mailSender;

    private final PlannedMessageStorageService plannedMessageStorageService;

    private final MailProperties mailProperties;

    @Scheduled(fixedDelayString = "${mail.resendPeriod}")
    public void sendMessages() {
        plannedMessageStorageService.getAllByStatus(EnumSet.of(PlannedMessageStatus.NEW, PlannedMessageStatus.IN_TRY))
                .forEach(this::sendMessage);
    }

    @Async
    @Transactional
    void sendMessage(PlannedMessage message) {
        final var lock = plannedMessageStorageService.getLock(message.getId());
        try {
            final var mimeMessage = createMimeMessage(message);
            mailSender.send(mimeMessage);
            message.setStatus(PlannedMessageStatus.SENT);
        } catch (MessagingException e) {
            log.error("Could not form email message for message {}. Got exception {}", message.getId(), e);
            message.setStatus(PlannedMessageStatus.FAILED);
        } catch (MailSendException e) {
            log.info("Could not send message {}", message.getId());
            final int currentResendCounter = message.getResendCounter() + 1;
            message.setResendCounter(currentResendCounter);
            if (currentResendCounter == mailProperties.getResendLimit()) {
                log.error("Expired resend tries for message {}", message.getId());
                message.setStatus(PlannedMessageStatus.FAILED);
            }
        } finally {
            plannedMessageStorageService.save(message);
            plannedMessageStorageService.removeLock(lock);
        }
    }

    private MimeMessage createMimeMessage(PlannedMessage message) throws MessagingException {
        final var mimeMessage = mailSender.createMimeMessage();
        final var mimeMessagehelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessagehelper.setTo(message.getAddresses().toArray(new String[0]));
        mimeMessagehelper.setSubject(message.getSubject());
        mimeMessagehelper.setText(message.getMessage() + MESSAGE_SUFFIX);

        if (message.getAttachment() != null) {
            final var inputStream = new ByteArrayInputStream(message.getAttachment());
            try {
                final var dataSource = new ByteArrayDataSource(inputStream, MediaType.APPLICATION_OCTET_STREAM_VALUE);
                mimeMessagehelper.addAttachment("attachment", dataSource);
            } catch (IOException e) {
                log.error("Will send message {} without attachment, because got exception {}", message.getId(), e);
                message.setAttachment(null);
            }
        }

        return mimeMessage;
    }

}
