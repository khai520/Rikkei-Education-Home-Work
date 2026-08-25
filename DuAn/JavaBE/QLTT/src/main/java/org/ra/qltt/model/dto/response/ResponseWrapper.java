package org.ra.qltt.model.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResponseWrapper<T> {

    private int httpCode;

    private boolean success;

    private String message;

    private T data;

    private Object errors;

    private LocalDateTime time;

    public static <T> ResponseWrapper<T> success(
            T data,
            String message,
            int httpCode
    ) {
        return ResponseWrapper.<T>builder()
                .httpCode(httpCode)
                .success(true)
                .message(message)
                .data(data)
                .errors(null)
                .time(LocalDateTime.now())
                .build();
    }

    public static <T> ResponseWrapper<T> error(
            T errors,
            String message,
            int httpCode
    ) {
        return ResponseWrapper.<T>builder()
                .httpCode(httpCode)
                .success(false)
                .message(message)
                .data(null)
                .errors(errors)
                .time(LocalDateTime.now())
                .build();
    }
}