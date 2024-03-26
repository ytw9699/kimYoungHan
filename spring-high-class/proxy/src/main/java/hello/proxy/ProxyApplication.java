package hello.proxy;

import hello.proxy.app.config.AppV1Config;
import hello.proxy.app.config.AppV2Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

//@Import(AppV1Config.class)
@Import({AppV1Config.class, AppV2Config.class})
@SpringBootApplication(scanBasePackages = "hello.proxy.app")
//주의 : hello.proxy.app하위만 컴포넌트 스캔하도록하는 설정 왜냐하면 수동으로 빈등록해서 v1~5까지 바꿀것이라서
public class ProxyApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}
}
