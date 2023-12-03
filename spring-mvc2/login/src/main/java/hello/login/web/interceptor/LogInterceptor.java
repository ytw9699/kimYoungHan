package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        request.setAttribute(LOG_ID, uuid);

        if (handler instanceof HandlerMethod) { //@RequestMapping: HandlerMethod
            HandlerMethod hm = (HandlerMethod) handler;//HandlerMethod는 호출할 컨트롤러 메서드의 모든 정보가 포함되어 있다.
            //위와 다르게 정적 리소스의 경우는: ResourceHttpRequestHandler 다. 필요하면 분기처리 
        }

        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);
        return true;//false 가 아닌 true 주면 핸들러 handler가 그대로 호출됨
    }

    @Override//예외가 발생하면 postHandle안됨. postHandle은 핸들러 어댑터가 modelAndView를 디스패처 서블릿으로 반환해준후 호출됨
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                                                       Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView);
    }

    @Override//완전히 끝날때 호출. 예외가 발생해도 호출됨. 뷰가 렌더링 된다음에 afterCompletion호출
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                                            Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = (String) request.getAttribute(LOG_ID);
        log.info("RESPONSE [{}][{}][{}]", logId, requestURI, handler);

        if (ex != null) {//에러 찍기
            log.error("afterCompletion error!!", ex);
        }
    }
}
