package com.gmail.aazavoykin.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    MESSAGE_NOT_FOUND(1L);

    private final Long code;

}
