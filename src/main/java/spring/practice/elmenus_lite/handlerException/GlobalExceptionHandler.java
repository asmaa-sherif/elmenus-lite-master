package spring.practice.elmenus_lite.handlerException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import spring.practice.elmenus_lite.dto.BaseResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handles @Valid validation on @RequestBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        log.error("Validation failed: {}", message);
        return ResponseEntity.badRequest().body(new BaseResponse<>(false, message, null));
    }

    // Handles @Validated validation on @PathVariable or @RequestParam
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseResponse<Object>> handleConstraintViolation(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().iterator().next().getMessage();
        log.error("Validation failed: {}", message);
        return ResponseEntity.badRequest().body(new BaseResponse<>(false, message, null));
    }

    // Handles not found entities
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleEntityNotFound(EntityNotFoundException ex) {
        log.error("Entity not found: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new BaseResponse<>(false, ex.getMessage(), null));
    }

    // Handles Database Exceptions
    @ExceptionHandler(DatabaseOperationException.class)
    public ResponseEntity<BaseResponse<Object>> handleDatabaseOperationException(DatabaseOperationException ex) {
        log.error("Database operation failed: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new BaseResponse<>(false, ex.getMessage(), null));
    }

    // Fallback for any exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleGenericException(Exception ex) {
        log.error("Runtime exception: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new BaseResponse<>(false, "Internal server error: " + ex.getMessage(), null));
    }
}

