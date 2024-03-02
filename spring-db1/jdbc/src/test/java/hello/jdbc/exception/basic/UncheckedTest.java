package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

@Slf4j
public class UncheckedTest {

    @Test
    void unchecked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void unchecked_throw() {
        Service service = new Service();
        assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyUncheckedException.class);//예외가 올라와서 터저야함
    }

    /**
     * RuntimeException을 상속받은 예외는 언체크 예외가 된다.
     */
    static class MyUncheckedException extends RuntimeException {
        public MyUncheckedException(String message) {
            super(message);
        }
    }

    /**
     * UnChecked 예외는
     * 예외를 잡거나, 던지지 않아도 된다.
     * 예외를 잡지 않으면 자동으로 밖으로 던진다.
     */
    static class Service {
        Repository repository = new Repository();

        /**
         * 필요한 경우 예외를 잡아서 처리하면 된다.
         */
        public void callCatch() {
            try {
                repository.call();
            } catch (MyUncheckedException e) {
                //예외 처리 로직
                log.info("예외 처리, message={}", e.getMessage(), e);
            }
        }

        /**
         * 예외를 잡지 않아도 된다. 자연스럽게 상위로 넘어간다.
         * 체크 예외와 다르게 throws 예외 선언을 하지 않아도 된다.
         */
        public void callThrow() {
            repository.call();
        }
    }

    static class Repository {
        public void call() {//throws 생략,
            // throws를 생략안하고 중요한 예외의 경우 이렇게 선언해두면 해당 코드를 호출하는 개발자가
            // 이런 예외가 발생한다는 점을 IDE를 통해 좀 더 편리하게 인지할 수 있다.
            // (컴파일 시점에 막을 수 있는 것은 아니고, IDE를 통해서 인지할 수 있는 정도이다.)
            throw new MyUncheckedException("ex");
        }
    }
}
