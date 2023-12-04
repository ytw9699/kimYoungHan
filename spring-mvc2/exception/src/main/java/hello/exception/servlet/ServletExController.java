package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class ServletExController {

    @GetMapping("/error-ex")
    public void errorEx() {//Exception의 경우 와스(톰캣)까지 전달되면 서버내부에서 처리할수 없는 오류로 생각하고 http상태코드 500반환
        throw new RuntimeException("예외 발생!");
    }//was까지 전파

    @GetMapping("/error-404")// response.sendError는 상태코드 지정가능
    public void error404(HttpServletResponse response) throws IOException {
        response.sendError(404, "404 오류!");
        //이 메소드 호출해서 당장 예외가 발생하는것은 아니지만 http상태코드와 오류 메시지와 함께
        //오류가 발생했다고 response를 서블릿 컨테이너에게 알려준다
    }

    @GetMapping("/error-400")
    public void error400(HttpServletResponse response) throws IOException {
        response.sendError(400, "400 오류!");
    }

    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500);
    }
}
