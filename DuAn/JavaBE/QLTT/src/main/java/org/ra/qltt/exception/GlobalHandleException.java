package org.ra.qltt.exception;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.model.dto.response.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalHandleException {

    /**
     * Lỗi @Valid
     */
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


    /**
     * Không tìm thấy URL / endpoint
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ResponseWrapper<Void>>
    handleNoResourceFoundException(
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


    /**
     * Không tìm thấy resource trong database
     */
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


    /**
     * Không có quyền thực hiện
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseWrapper<Void>>
    handleAccessDeniedException(
            AccessDeniedException e
    ) {

        String message = e.getMessage() != null
                ? e.getMessage()
                : "Bạn không có quyền thực hiện thao tác này";

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        ResponseWrapper.error(
                                null,
                                message,
                                HttpStatus.FORBIDDEN.value()
                        )
                );
    }


    /**
     * Lỗi nghiệp vụ / tham số không hợp lệ
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseWrapper<Void>>
    handleIllegalArgumentException(
            IllegalArgumentException e
    ) {

        String message = e.getMessage() != null
                ? e.getMessage()
                : "Dữ liệu truyền vào không hợp lệ";

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ResponseWrapper.error(
                                null,
                                message,
                                HttpStatus.BAD_REQUEST.value()
                        )
                );
    }


    /**
     * Thiếu @RequestParam bắt buộc
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseWrapper<Void>>
    handleMissingServletRequestParameter(
            MissingServletRequestParameterException e
    ) {

        String message = String.format(
                "Tham số '%s' là bắt buộc",
                e.getParameterName()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ResponseWrapper.error(
                                null,
                                message,
                                HttpStatus.BAD_REQUEST.value()
                        )
                );
    }


    /**
     * RequestParam truyền sai kiểu dữ liệu
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseWrapper<Void>>
    handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException e
    ) {

        String message = String.format(
                "Tham số '%s' không hợp lệ",
                e.getName()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ResponseWrapper.error(
                                null,
                                message,
                                HttpStatus.BAD_REQUEST.value()
                        )
                );
    }


    /**
     * Lỗi hệ thống không xác định
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper<Void>>
    handleException() {

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