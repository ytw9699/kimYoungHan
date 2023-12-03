package hello.login.web.session;

import hello.login.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {

        //세션 생성 - 서버에서 생성후 응답한다 본다
        MockHttpServletResponse response = new MockHttpServletResponse();//직접사용할수업어 가짜로
        Member member = new Member();
        sessionManager.createSession(member, response);

        //요청에 응답 쿠키 저장 - 웹브라우저의 요청이라 본다
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        //세션 조회
        Object result = sessionManager.getSession(request);
        assertThat(result).isEqualTo(member);

        //세션 만료
        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        assertThat(expired).isNull();//null 이어야 한다

    }
}
