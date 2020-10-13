package com.gmail.aazavoykin.data.model;

import com.gmail.aazavoykin.data.enums.PlannedMessageStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Accessors(chain = true)
public class PlannedMessage {

    private Long id;

    private LocalDateTime sendDateTime;

    private LocalDateTime created;

    private LocalDateTime updated;

    private Set<String> addresses;

    private String subject;

    private String message;

    private byte[] attachment;

    private PlannedMessageStatus status;

    private int resendCounter;

}
