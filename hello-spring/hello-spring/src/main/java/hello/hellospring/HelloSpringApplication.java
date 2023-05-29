package hello.hellospring;//이 패키지 하위를 스프링이 찾아서 컴포넌트로 등록한다

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //톰캣 웹서버 내장
public class HelloSpringApplication {

	public static void main(String[] args) { //메인 메소드로부터 프로젝트 시작
		SpringApplication.run(HelloSpringApplication.class, args);
	}

}
