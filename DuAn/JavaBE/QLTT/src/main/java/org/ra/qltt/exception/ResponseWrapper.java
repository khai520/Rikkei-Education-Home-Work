package org.ra.qltt.exception;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Component
public class ResponseWrapper<T> {

    private int httpCode;

    private boolean success;

    private String message;

    private T data;

    private Object errors;

    private LocalDateTime time;

    private static MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        ResponseWrapper.messageSource = messageSource;
    }

    public static <T> ResponseWrapper<T> success(
            T data,
            String messageKey,
            int httpCode
    ) {
        return ResponseWrapper.<T>builder()
                .httpCode(httpCode)
                .success(true)
                .message(getMessage(messageKey))
                .data(data)
                .errors(null)
                .time(LocalDateTime.now())
                .build();
    }

    public static <T> ResponseWrapper<T> error(
            T errors,
            String messageKey,
            int httpCode
    ) {
        return ResponseWrapper.<T>builder()
                .httpCode(httpCode)
                .success(false)
                .message(getMessage(messageKey))
                .data(null)
                .errors(errors)
                .time(LocalDateTime.now())
                .build();
    }

    public static String getMessage(String messageKey) {
        return messageSource.getMessage(
                messageKey,
                null,
                LocaleContextHolder.getLocale()
        );
    }
}