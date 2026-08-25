package org.ra.qltt.model.dto.response;

import lombok.*;

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

    public static <T> ResponseWrapper<?> success(T data, String message, int httpCode) {
        return ResponseWrapper.builder().
                httpCode(httpCode).
                success(true).
                message(message)
                .data(data)
                .build();
    }

    public static <T> ResponseWrapper<?> error(T errors, String message, int httpCode){
        return ResponseWrapper.builder().
                httpCode(httpCode).
                success(false).
                message(message)
                .errors(errors)
                .build();
    }
}
