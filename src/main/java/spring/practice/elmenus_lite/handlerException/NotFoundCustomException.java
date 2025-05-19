package spring.practice.elmenus_lite.handlerException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Value not found")
public class NotFoundCustomException extends RuntimeException {
    public NotFoundCustomException() {
    }

    public NotFoundCustomException(String message) {
        super(message);
    }

    public NotFoundCustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundCustomException(Throwable cause) {
        super(cause);
    }

    public NotFoundCustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
