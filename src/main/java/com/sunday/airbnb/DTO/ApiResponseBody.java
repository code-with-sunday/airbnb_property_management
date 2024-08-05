package com.sunday.airbnb.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sunday.airbnb.enums.InternalCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseBody<T> {
    private final boolean success;
    private final String message;
    private final InternalCodeEnum internalCode;
    private final String timestamp = new Timestamp(System.currentTimeMillis())
            .toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+0000"));
    private final T data;
}