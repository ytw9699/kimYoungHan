package hello.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "hello.core",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes=Configuration.class)
        //@Component가 붙은 클래스를 모두 빈으로 등록해준다                                //Configuration애노테이션 붙은 클래스만 대상에서 제외
)
public class AutoAppConfig {
}
