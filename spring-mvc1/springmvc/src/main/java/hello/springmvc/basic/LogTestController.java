package hello.springmvc.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Slf4j
@RestController
public class LogTestController {

    //private final Logger log = LoggerFactory.getLogger(getClass());//getClass는 내 클래스
    private final Logger log = LoggerFactory.getLogger(LogTestController.class);//위와 같음

    @RequestMapping("/log-test")
    public String logTest() {

        String name = "Spring";
        System.out.println("name = " + name);//모두 다 출력 왠만해선 안씀

        log.trace(" trace my log="+ name);
        //이런식으로 적어두면 trace 를 출력 안해도 연산을 하고 메모리를 써서 좋은 방식아니다,쓸모없는 리소스 씀

        log.trace("trace log={}", name);//로컬에서 본다
        log.debug("debug log={}", name);//개발서버서 본다
        log.info(" info log={}", name);//운영시스템에서 봐야할 중요한 정보다
        log.warn(" warn log={}", name);//위험 경고
        log.error("error log={}", name);//에러라 빨리 봐야함

        return "ok";
    }
}
