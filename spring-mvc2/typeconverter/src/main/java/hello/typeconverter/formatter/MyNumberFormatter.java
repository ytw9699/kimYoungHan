package hello.typeconverter.formatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Slf4j//스트링은 기본으로 변환이 되기 때문에 숫자를 해보자
public class MyNumberFormatter implements Formatter<Number> {

    @Override//locale정보 활용해 나라별로 다른 숫자 포맷 만듬
    public Number parse(String text, Locale locale) throws ParseException {//문자를 숫자로
        log.info("text={}, locale={}", text, locale);
        //"1,000" -> 1000
        NumberFormat format = NumberFormat.getInstance(locale);
        return format.parse(text);
    }

    @Override
    public String print(Number object, Locale locale) {//객체를 문자로
        log.info("object={}, locale={}", object, locale);
        /*NumberFormat instance = NumberFormat.getInstance(locale);
        String fomat = instance.format(object);*/
        return NumberFormat.getInstance(locale).format(object);
    }
}
