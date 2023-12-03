package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //서블릿은 원래 HTTP요청 말고 다른것도 받을수 있게 설계되어 ServletRequest사용함
        log.info("log filter doFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;//HttpServletRequest로 다운 캐스팅
        String requestURI = httpRequest.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        try {
            log.info("REQUEST [{}][{}]", uuid, requestURI);
            chain.doFilter(request, response);//다음필터 있으면 호출하고 없으면 서블릿 호출
        } catch (Exception e) {
            throw e;
        } finally {//서블릿이 컨트롤러 까지 다호출하고 끝나면 일로온다
            log.info("RESPONSE [{}][{}]", uuid, requestURI);
        }
    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }
}
