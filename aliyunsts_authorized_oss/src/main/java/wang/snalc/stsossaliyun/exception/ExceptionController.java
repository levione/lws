package wang.snalc.stsossaliyun.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class ExceptionController {

    @ExceptionHandler(PublicException.class)
    public ResponseEntity NotFoundException(Exception e) {
        PublicException myException = (PublicException) e;
        ResultUnit resultUnit = ResultUnit.builder().timestamp(LocalDateTime.now()).path(myException.getUrl()).message(myException.getExceptionEnum().getMessage()).data("").code(myException.getExceptionEnum().getCode()).build();
        return new ResponseEntity<>(resultUnit, HttpStatus.OK);
    }
}
