package hello.login.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Enumeration;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        //HttpSession은 서블릿이 제공하는것

        if (session == null) {
            return "세션이 없습니다.";
        }
        //Enumeration<String> attributeNames = session.getAttributeNames();
        //세션 데이터 출력
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name={}, value={}", name, session.getAttribute(name)));

        log.info("sessionId={}", session.getId());
        //session.setMaxInactiveInterval(1800);//특정 세션만 초 설정 1800초
        log.info("getMaxInactiveInterval={}", session.getMaxInactiveInterval());//특정 세션만
        //이 기간 동안 클라이언트와의 상호 작용이 없으면 세션이 만료되어 세션 데이터가 삭제
        log.info("creationTime={}", new Date(session.getCreationTime()));
        log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime()));//마지막 접근한 시간
        //이 마지막 접근 시간을 기준으로 유지 시간을 체크하여 시간이 지나면 와스가 해당 세션 삭제
        log.info("isNew={}", session.isNew());//지금만든 세션인지 아니면 생성되어있는거 가져온건지
        return "세션 출력";
    }
}
