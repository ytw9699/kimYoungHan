package hello.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad")//500예외를 400으로 변경
public class BadRequestException extends RuntimeException {
    //response.senderror으로 해결하기 때문에 결국 와스에서 다시 /error 오류페이지 내부 재요청한다.
}
