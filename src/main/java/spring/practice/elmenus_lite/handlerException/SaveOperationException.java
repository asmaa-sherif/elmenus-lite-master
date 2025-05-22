package spring.practice.elmenus_lite.handlerException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Save operation failed")
public class SaveOperationException extends RuntimeException {
    public SaveOperationException() {
    }

    public SaveOperationException(String message) {
        super(message);
    }

    public SaveOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaveOperationException(Throwable cause) {
        super(cause);
    }

    public SaveOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
