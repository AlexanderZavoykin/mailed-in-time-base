package com.gmail.aazavoykin.rest.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CommonResponse<V> {

    private final V payload;

    private final String message;

    public static <V> CommonResponse<V> ok(V payload) {
        return new CommonResponse<>(payload, "OK");
    }

    public static <V> CommonResponse<V> ok() {
        return new CommonResponse<>(null, "OK");
    }

    public static CommonResponse<Void> fail() {
        return new CommonResponse<>(null, "FAILED");
    }

}
