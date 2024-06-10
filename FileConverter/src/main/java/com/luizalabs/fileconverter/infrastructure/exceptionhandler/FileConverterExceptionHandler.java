package com.luizalabs.fileconverter.infrastructure.exceptionhandler;

import com.luizalabs.fileconverter.core.exception.BadRequestException;
import com.luizalabs.fileconverter.core.exception.NotFoundException;
import com.luizalabs.fileconverter.infrastructure.entrypoint.vo.MensageResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class FileConverterExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleBadRequestException(NotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MensageResponseVO> exception(Exception ex, WebRequest request) {
        MensageResponseVO message = new MensageResponseVO(LocalDateTime.now(), "please, contact admin.");
        log.error(message.getMessage(), ex);
        return new ResponseEntity<MensageResponseVO>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String errorMessage = String.format("Failed to convert value of type '%s' to required type '%s'. %s",
                ex.getValue().getClass().getSimpleName(), ex.getRequiredType().getSimpleName(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleMissingParams(MissingServletRequestParameterException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(ex.getParameterName(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<String> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        String errorMessage = "Content-Type " + ex.getContentType() + " is not supported. Supported content types are: " + ex.getSupportedMediaTypes();
        return new ResponseEntity<>(errorMessage, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxUploadSizeExceeded(MaxUploadSizeExceededException ex) {
        String errorMessage = "The uploaded file exceeds the maximum permitted size.";
        return new ResponseEntity<>(errorMessage, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> handleNoResourceFoundException(NoResourceFoundException ex) {
        String errorMessage = "The requested resource could not be found.";
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<?> handleBadRequestException2(HandlerMethodValidationException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getValueResults(), HttpStatus.BAD_REQUEST);
    }
}
