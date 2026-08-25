package org.ra.qltt.model.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ErrorResponse {
    private String message;
    private LocalDateTime time;
    private int httpCode;
}

