package hello.core.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import static org.assertj.core.api.Assertions.assertThat;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {

        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //ThreadA: 사용자A 10,000원 주문
        statefulService1.order("userA", 10000);

        //ThreadB: 사용자B 20,000원 주문
        statefulService1.order("userB", 20000);

        //ThreadA: 사용자A 주문 금액 조회
        int price = statefulService1.getPrice();
        
        System.out.println("price = " + price);

        assertThat(statefulService1.getPrice()).isEqualTo(20000);//하지만 b의 주문 금액조회되는 문제생김
    }

    static class TestConfig {
        @Bean
        StatefulService statefulService() {

            return new StatefulService();
        }
    }
}
