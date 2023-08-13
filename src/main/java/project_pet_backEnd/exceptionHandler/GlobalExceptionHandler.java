package project_pet_backEnd.exceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import project_pet_backEnd.user.dto.ResultResponse;

import javax.validation.ConstraintViolationException;

/**
 * 全域 異常處理 防止部分驗證回傳 500
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 處理丟出的 ResponseStatusException
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException ex) throws JsonProcessingException {
        String errorMessage = ex.getReason();
        HttpStatus httpStatus = ex.getStatus();
        ResultResponse rs = new ResultResponse();
        rs.setMessage(errorMessage);
        System.out.println("執行異常");
        return new ResponseEntity<>(rs, httpStatus);
    }

    /**
     * 驗證參數異常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleRequestValidError(ConstraintViolationException ex) throws JsonProcessingException {
        // 返回錯誤訊息和 400 Bad Request 狀態碼
        ResultResponse rs = new ResultResponse();
        rs.setMessage("使用者輸入異常");
        return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
    }
}