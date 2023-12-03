package hello.login.web.argumentresolver;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {//2번째 부터는 캐시때매 호출안됨
        log.info("supportsParameter 실행");

        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);//로그인 어노테이션이 붙어있는가
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());//멤버 클래스이냐

        return hasLoginAnnotation && hasMemberType;
    }

    @Override//supportsParameter가 투르면 실행한다. 아규먼트를 만들어서 반환한다.
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("resolveArgument 실행");
        //즉, 컨트롤러 호출직전에 호출되어 필요한 파라미터 정보를 생성
        //이후 스프링 mvc 가 컨트롤러의 메서드를 호출하면서 여기에서 반환된 멤버 객체를 파라미터에 전달함
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);

        if (session == null) {
            return null;
        }

        return session.getAttribute(SessionConst.LOGIN_MEMBER);//없으면 null 반환
    }
}
