package hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j//Exception을 해결한다. 하지만 여기는 와스에서 다시 response.sendError 때문에 예외 프로세스를 탄다
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {
        log.info("call resolver", ex);

        try {
            if (ex instanceof IllegalArgumentException) {//IllegalArgumentException이 예외를 그냥 여기서 먹어버림
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                //여기서 새롭게 400으로 가기위해 설정함. 서블릿컨테이너는 sendError가 호출된거면 오류페이지 찾게됨
                return new ModelAndView();//이렇게 리턴하면 정상흐름으로 가는것이고 뷰에서는 렌더링 할게없다.
                // 그런후 정상적으로 생각하고 서블릿 컨테이너 까지 정상응답 간다.
            }//결국 Exception을 sendError로 바꿔치기한거
        } catch (IOException e) {
            log.error("resolver ex", e);
        }
        return null;//null 이면 예외를 못잡고 다시 터져서 날라감
    }
}
