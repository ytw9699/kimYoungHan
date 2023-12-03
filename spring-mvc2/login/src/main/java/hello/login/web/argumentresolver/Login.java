package hello.login.web.argumentresolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)//파라미터에만 사용. 다른곳에서는 클래스에 사용하기도함
@Retention(RetentionPolicy.RUNTIME)//이렇게 설정하면 리플렉션 등을 활용할 수 있도록 애노테이션 정보가 남아있음
public @interface Login {
}
