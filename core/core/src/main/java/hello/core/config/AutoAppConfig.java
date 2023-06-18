package hello.core.config;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "hello.core",//아무것도 지정하지않으면 이 ComponentScan이 붙은 클래스 하위로 찾음
        //basePackageClasses = AutoAppConfig.class, //지정한 클래스가 있는 패키지 하위로 찾는것
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes=Configuration.class)
        //@Component,controller, service, repository, Configuration// Configuration애노테이션 붙은 클래스만 대상에서 제외
)
public class AutoAppConfig {

    @Bean(name = "memoryMemberRepository")
    MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }

}
