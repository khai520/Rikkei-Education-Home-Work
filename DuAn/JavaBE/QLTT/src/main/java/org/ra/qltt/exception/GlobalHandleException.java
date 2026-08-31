package org.ra.qltt.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
                                "error.validation.invalid",
                                HttpStatus.BAD_REQUEST.value()
                        )
                );
    }

    /**
     * Không tìm thấy URL
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ResponseWrapper<Void>>
    handleNoResourceFoundException() {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ResponseWrapper.error(
                                null,
                                "error.resource.not_found",
                                HttpStatus.NOT_FOUND.value()
                        )
                );
    }

    /**
     * Không tìm thấy resource
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseWrapper<Void>>
    handleResourceNotFoundException(ResourceNotFoundException e) {

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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseWrapper<Void>>
    handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ResponseWrapper.error(
                                null,
                                "Tham số định dạng sai",
                                HttpStatus.NOT_FOUND.value()
                        )
                );
    }
    /**
     * Resource đã tồn tại
     */
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ResponseWrapper<Void>>
    handleResourceAlreadyExistsException(ResourceAlreadyExistsException e) {

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

    /**
     * Không có quyền
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseWrapper<Void>>
    handleAccessDeniedException(AccessDeniedException e) {

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        ResponseWrapper.error(
                                null,
                                e.getMessage(),
                                HttpStatus.FORBIDDEN.value()
                        )
                );
    }

    /**
     * Lỗi nghiệp vụ
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseWrapper<Void>>
    handleIllegalArgumentException(IllegalArgumentException e) {

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

    /**
     * Thiếu RequestParam
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseWrapper<Void>>
    handleMissingServletRequestParameter(
            MissingServletRequestParameterException e
    ) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ResponseWrapper.error(
                                null,
                                "error.request.parameter_missing",
                                HttpStatus.BAD_REQUEST.value(),
                                e.getParameterName()
                        )
                );
    }

    /**
     * RequestParam sai kiểu dữ liệu
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseWrapper<Void>>
    handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException e
    ) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ResponseWrapper.error(
                                null,
                                "error.request.parameter_invalid",
                                HttpStatus.BAD_REQUEST.value(),
                                e.getName()
                        )
                );
    }

    /**
     * Sai username/password
     */
    @ExceptionHandler({
            BadCredentialsException.class,
            UsernameNotFoundException.class
    })
    public ResponseEntity<ResponseWrapper<Void>>
    handleAuthenticationException() {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ResponseWrapper.error(
                                null,
                                "error.auth.invalid_credentials",
                                HttpStatus.UNAUTHORIZED.value()
                        )
                );
    }

    /**
     * Lỗi hệ thống
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper<Void>>
    handleException() {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ResponseWrapper.error(
                                null,
                                "error.system.internal",
                                HttpStatus.INTERNAL_SERVER_ERROR.value()
                        )
                );
    }
}