package hello.hellospring.config;


import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemverService memverService(){
        return new MemverService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        /*return new MemoryMemberRepository();*/
        return new JdbcMemberRepository(dataSource);
    }
}


