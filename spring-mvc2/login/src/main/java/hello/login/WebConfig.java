package hello.login;

import hello.login.web.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean logFilter() {//FilterRegistrationBean 등록필요
        //스프링 부트가 와스를 띄울때(서블릿 컨테이너 올릴때) 필터를 넣어줌
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
                                       filterRegistrationBean.setFilter(new LogFilter());//필터 넣어주고
                                       filterRegistrationBean.setOrder(1);//필터 순서정해줌 낮을수록 먼저 동작
                                       filterRegistrationBean.addUrlPatterns("/*");//모든 url 에 적용
        return filterRegistrationBean;
    }
}
