package com.gmail.aazavoykin.rest.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class PlannedMessageDto {

    private Long id;

    private String sendTime;

    private String created;

    private String updated;

    private Set<String> addresses;

    private String subject;

    private String message;

    private byte[] attachment;

    private String status;

    private int resendCounter;

}
