package hello.exception;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

//@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    //서블릿 컨테이너가 제공해준걸 사용하자. web.xml대신 사용하는것. 웹서버(톰캣)를 커스터마이징하자
    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        //스프링부트까 뜰때 톰캣에 아래를 등록해둠. 그래서 톰캣까지 예외발생해서오면 다시 해당 컨트롤러 호출
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");
        ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/error-page/500");//RuntimeException또는 그자식 타입

        factory.addErrorPages(errorPage404, errorPage500, errorPageEx);
    }
}
