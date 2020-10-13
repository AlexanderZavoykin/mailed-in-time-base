package com.gmail.aazavoykin.service;

import com.gmail.aazavoykin.data.model.PlannedMessage;
import com.gmail.aazavoykin.data.enums.PlannedMessageStatus;
import com.gmail.aazavoykin.rest.dto.CommonResponse;
import com.gmail.aazavoykin.rest.dto.CreatePlannedMessageRequest;
import com.gmail.aazavoykin.rest.dto.PlannedMessageDto;
import com.gmail.aazavoykin.service.exception.ErrorCode;
import com.gmail.aazavoykin.service.exception.MailedInTimeException;

public class PlannedMessageService {

    private final PlannedMessageStorageService plannedMessageStorageService;

    private final PlannedMessageConverter converter;

    public PlannedMessageService(PlannedMessageStorageService plannedMessageStorageService) {
        this.plannedMessageStorageService = plannedMessageStorageService;
        this.converter = new PlannedMessageConverter();
    }

    public CommonResponse<PlannedMessageDto> getById(Long id) {
        final var message = plannedMessageStorageService.getById(id)
                .orElseThrow(() -> new MailedInTimeException(ErrorCode.MESSAGE_NOT_FOUND, "Could not find message by id"));
        return CommonResponse.ok(converter.convert(message));
    }

    public CommonResponse<PlannedMessageDto> save(CreatePlannedMessageRequest request) {
        final var message = plannedMessageStorageService.save(new PlannedMessage()
                .setSendDateTime(request.getSendDateTime())
                .setAddresses(request.getAddresses())
                .setSubject(request.getSubject())
                .setMessage(request.getMessage())
                .setAttachment(request.getAttachment())
                .setStatus(PlannedMessageStatus.NEW)
                .setResendCounter(0));
        return CommonResponse.ok(converter.convert(message));
    }

    public CommonResponse<Void> remove(Long id) {
        final var message = plannedMessageStorageService.getById(id)
                .orElseThrow(() -> new MailedInTimeException(ErrorCode.MESSAGE_NOT_FOUND, "Could not find message by id"));
        plannedMessageStorageService.remove(message);
        return CommonResponse.ok();
    }

}
