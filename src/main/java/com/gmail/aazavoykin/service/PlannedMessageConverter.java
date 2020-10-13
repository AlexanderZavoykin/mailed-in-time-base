package com.gmail.aazavoykin.service;

import com.gmail.aazavoykin.data.model.PlannedMessage;
import com.gmail.aazavoykin.rest.dto.PlannedMessageDto;

import java.time.format.DateTimeFormatter;

public class PlannedMessageConverter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public PlannedMessageDto convert(PlannedMessage message) {
        return new PlannedMessageDto()
                .setId(message.getId())
                .setSendTime(message.getSendDateTime().format(FORMATTER))
                .setCreated(message.getCreated().format(FORMATTER))
                .setUpdated(message.getUpdated().format(FORMATTER))
                .setAddresses(message.getAddresses())
                .setSubject(message.getSubject())
                .setMessage(message.getMessage())
                .setAttachment(message.getAttachment())
                .setStatus(message.getStatus().name());
    }

}
