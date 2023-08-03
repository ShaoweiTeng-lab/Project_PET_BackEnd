package project_pet_backEnd.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
/**
 * 全域 異常處理 防止部分驗證回傳 500
 * */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 處理丟出的 ResponseStatusException
     * */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        String errorMessage = ex.getReason();
        HttpStatus httpStatus = ex.getStatus();
        return new ResponseEntity<>(errorMessage, httpStatus);
    }
    /**
     * 驗證參數異常
     * */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleRequestValidError(ConstraintViolationException ex) {
        // 返回錯誤訊息和 400 Bad Request 狀態碼
        return new ResponseEntity<>("使用者輸入異常", HttpStatus.BAD_REQUEST);
    }
}