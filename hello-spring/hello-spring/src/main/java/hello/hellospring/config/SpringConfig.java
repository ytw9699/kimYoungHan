package hello.hellospring.config;


import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemverService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

/*@Controller
public class SpringConfig {
    @Bean
    public MemverService memverService(){
        return new MemverService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}*/


