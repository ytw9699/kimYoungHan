package hello.typeconverter;

import hello.typeconverter.converter.IntegerToStringConverter;
import hello.typeconverter.converter.IpPortToStringConverter;
import hello.typeconverter.converter.StringToIntegerConverter;
import hello.typeconverter.converter.StringToIpPortConverter;
import hello.typeconverter.formatter.MyNumberFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {//스프링 mvc에 등록

    @Override//스프링은 내부서 사용하는 conversionservice에 컨버터를 추가한다. 우선순위가 기본 컨버터보다 높아짐
    public void addFormatters(FormatterRegistry registry) {//addFormatters는 컨버터에서 더 확장된것

        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());

        //registry.addConverter(new StringToIntegerConverter());//컨버터가 포맷터보다 우선순위가 높아서 주석처리
        //registry.addConverter(new IntegerToStringConverter());
        registry.addFormatter(new MyNumberFormatter());
    }
}
