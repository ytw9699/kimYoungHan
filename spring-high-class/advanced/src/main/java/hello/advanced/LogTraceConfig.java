package hello.advanced;

import hello.advanced.trace.logtrace.FieldLogTrace;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

  /*  @Bean
    public LogTrace logTrace() {
        return new FieldLogTrace();//싱글톤 등록
    }*/

    @Bean//수동으로 스프링 빈으로 등록하자. 수동으로 등록하면 향후 구현체를 편리하게 변경할 수 있다는장점이 있다
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}
