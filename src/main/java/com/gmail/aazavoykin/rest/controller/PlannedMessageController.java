package com.gmail.aazavoykin.rest.controller;

import com.gmail.aazavoykin.rest.dto.CommonResponse;
import com.gmail.aazavoykin.rest.dto.CreatePlannedMessageRequest;
import com.gmail.aazavoykin.rest.dto.PlannedMessageDto;
import com.gmail.aazavoykin.service.PlannedMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlannedMessageController {

    private static final String API_ROOT_V1 = "/api/v1";

    private static final String MESSAGE = API_ROOT_V1 + "/message";

    private static final String MESSAGE_ID = "messageId";

    private static final String BY_ID = MESSAGE + "/{" + MESSAGE_ID + "}";

    private static final String REMOVE = BY_ID + "/remove";

    private final PlannedMessageService plannedMessageService;

    @GetMapping(path = BY_ID)
    public CommonResponse<PlannedMessageDto> getById(@PathVariable(MESSAGE_ID) Long id) {
        return plannedMessageService.getById(id);
    }

    @PostMapping(path = MESSAGE)
    public CommonResponse<PlannedMessageDto> create(@RequestBody CreatePlannedMessageRequest request) {
        return plannedMessageService.save(request);
    }

    @PostMapping(path = REMOVE)
    public CommonResponse<Void> remove(@PathVariable(MESSAGE_ID) Long id) {
        return plannedMessageService.remove(id);
    }

}
