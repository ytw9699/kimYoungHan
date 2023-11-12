package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //[status-line]
        response.setStatus(HttpServletResponse.SC_OK);//200
        //response.setStatus(HttpServletResponse.SC_BAD_REQUEST);//400

        //[response-headers]
        response.setHeader("Content-Type", "text/plain;charset=utf-8");//한글 안깨지게 설정
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");//캐시 무효화
        response.setHeader("Pragma", "no-cache");//과거버전 캐시 없앤다
        response.setHeader("my-header", "hello");//임의의 헤더 생성

        //[Header 편의 메서드]
        //content(response);
        //cookie(response);
        //redirect(response);

        PrintWriter writer = response.getWriter();
        writer.println("한글 안깨짐");
    }

    private void content(HttpServletResponse response) {
        //response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        //response.setContentLength(2); //(생략시 자동 생성)  //Content-Length: 2
    }

    private void cookie(HttpServletResponse response) {
        //response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); //600초
        response.addCookie(cookie);
    }

    private void redirect(HttpServletResponse response) throws IOException {
        //response.setStatus(HttpServletResponse.SC_FOUND); //302로 먼저 주고
        //response.setHeader("Location", "/basic/hello-form.html");//리다이렉트
        response.sendRedirect("/basic/hello-form.html");// 위 2줄을 한번에
    }
}
