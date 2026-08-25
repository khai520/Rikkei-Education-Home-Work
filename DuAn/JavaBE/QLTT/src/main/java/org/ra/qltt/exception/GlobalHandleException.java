package org.ra.qltt.exception;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.model.dto.response.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalHandleException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseWrapper<Map<String, String>>>
    handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {

        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.put(
                    fieldError.getField(),
                    fieldError.getDefaultMessage()
            );
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ResponseWrapper.error(
                                errors,
                                "Dữ liệu không hợp lệ",
                                HttpStatus.BAD_REQUEST.value()
                        )
                );
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ResponseWrapper<Void>>
    handleNoResourceFoundException(
            NoResourceFoundException e
    ) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ResponseWrapper.error(
                                null,
                                "Không tìm thấy tài nguyên hoặc đường dẫn yêu cầu",
                                HttpStatus.NOT_FOUND.value()
                        )
                );
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseWrapper<Void>>
    handleResourceNotFoundException(
            ResourceNotFoundException e
    ) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ResponseWrapper.error(
                                null,
                                e.getMessage(),
                                HttpStatus.NOT_FOUND.value()
                        )
                );
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseWrapper<Void>>
    handleAccessDeniedException(
            AccessDeniedException e
    ) {

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        ResponseWrapper.error(
                                null,
                                e.getMessage() != null
                                        ? e.getMessage()
                                        : "Bạn không có quyền thực hiện thao tác này",
                                HttpStatus.FORBIDDEN.value()
                        )
                );
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseWrapper<Void>>
    handleIllegalArgumentException(
            IllegalArgumentException e
    ) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ResponseWrapper.error(
                                null,
                                e.getMessage(),
                                HttpStatus.BAD_REQUEST.value()
                        )
                );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper<Void>>
    handleException(Exception e) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ResponseWrapper.error(
                                null,
                                "Đã xảy ra lỗi trong hệ thống",
                                HttpStatus.INTERNAL_SERVER_ERROR.value()
                        )
                );
    }
}