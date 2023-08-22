package project_pet_backEnd.exceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.validation.ConstraintViolationException;

/**
 * 全域 異常處理 防止部分驗證回傳 500
 */
@ControllerAdvice
public class GlobalExceptionHandler  {

    /**
     * 處理丟出的 ResponseStatusException
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException ex) throws JsonProcessingException {
        String errorMessage = ex.getReason();
        HttpStatus httpStatus = ex.getStatus();
        ResultResponse rs = new ResultResponse();
        rs.setCode(httpStatus.value());//改成將異常code丟入訊息中
        rs.setMessage(errorMessage);
        //return new ResponseEntity<>(rs, httpStatus);
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

//    /**
//     * 驗證參數異常
//     */
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<?> handleRequestValidError(ConstraintViolationException ex) throws JsonProcessingException {
//        // 返回錯誤訊息和 400 Bad Request 狀態碼
//        ResultResponse rs = new ResultResponse();
//        String msg = ex.getMessage();
//        rs.setMessage(msg);
//        rs.setCode(400);//改成將異常code丟入訊息中
//        return new ResponseEntity<>(rs, HttpStatus.OK);
//    }

    /**
     * 驗證參數異常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleRequestValidError(MethodArgumentNotValidException  ex) throws JsonProcessingException {
        ResultResponse rs = new ResultResponse();
        String msg = ex.getBindingResult().getFieldError().getDefaultMessage();
        rs.setMessage(msg);
        rs.setCode(400);//改成將異常code丟入訊息中
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }
}