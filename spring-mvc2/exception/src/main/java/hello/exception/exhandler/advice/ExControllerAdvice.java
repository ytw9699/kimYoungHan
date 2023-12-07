package hello.exception.exhandler.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "hello.exception.api")//대상 지정 = 이 패키지 이하
public class ExControllerAdvice {//여러 컨트롤러에서 발생한 예외들을 여기서 처리

    @ResponseStatus(HttpStatus.BAD_REQUEST)//여기서 상태코드 바꿈. 하지만 동적불가
    @ExceptionHandler(IllegalArgumentException.class)//이 컨트롤러에서 예외발생시 불러짐
    public ErrorResult illegalExHandler(IllegalArgumentException e){//IllegalArgumentException의 자식 다잡아줌
        log.error("ExceptionHandler", e);
        return new ErrorResult("BAD", e.getMessage());//정상적으로 바꿔서 200 리턴
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e){//UserException의 자식 다 잡아줌
        log.error("ExceptionHandler", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
        //ResponseEntity를 사용해서 HTTP메시지 바디에 직접응답. 장점은 동적으로 사용가능
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler//ExceptionHandler의 예외를 생략하면 메소드 파라미터의 예외가 지정됨
    public ErrorResult exHandler(Exception e){//Exception의 자식 다잡아줌
        log.error("ExceptionHandler", e);
        return new ErrorResult("EX", "내부 오류");
    }
}
