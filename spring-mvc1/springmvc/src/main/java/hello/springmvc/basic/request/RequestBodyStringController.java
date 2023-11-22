package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletInputStream inputStream = request.getInputStream();

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        //Stream은 바이트 코드인데 이를 문자로 바꿀때는 어떤 인코딩으로 문자를 바꿀지 지정 필요

        log.info("messageBody={}", messageBody);

        response.getWriter().write("ok");
    }

    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        responseWriter.write("ok");
    }

    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        //HttpEntity는 : http 메시지를 의미 헤더와 바디 조회가능
        //http 바디에있는것을 문자로 바꿔서 넣어줌, http메시지 컨버터 동작함
        String messageBody = httpEntity.getBody();//http메시지에있는 바디를 꺼냄
        log.info("messageBody={}", messageBody);

        return new HttpEntity<>("ok");//바디 메시지를 넣는다.
        //메시지 바디 정보 직접 반환, 헤더정보 포함 가능
        //위 방식은 뷰를 조회하지 않고, 이렇게 http 메시지를 주고받는다.
    }

    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) {//바디를 깔끔하게 받음
        //요청오는건 RequestBody 응답 가는건 ResponseBody 로 깔끔
        log.info("messageBody={}", messageBody);
        return "ok";
        //http 메시지 컨버터는 http메시지를 읽어서 조작해서 처리해주는것
    }
}
