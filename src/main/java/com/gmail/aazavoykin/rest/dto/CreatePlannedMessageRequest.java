package com.gmail.aazavoykin.rest.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Accessors(chain = true)
public class CreatePlannedMessageRequest {

    private LocalDateTime sendDateTime;

    private Set<String> addresses;

    private String subject;

    private String message;

    private byte[] attachment;

}
