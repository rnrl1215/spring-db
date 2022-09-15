package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.SplittableRandom;

@Slf4j
public class CheckedTest {

    @Test
    void checked_catch() {
        Service service = new Service();
        service.callCatch();
        Assertions.assertThatThrownBy(()-> service.callThrow()).isInstanceOf(MyCheckedException.class);
    }

    /**
     * Exception을 상속 받은 예외는 체크 예외가 된다.
     */
    static class MyCheckedException extends Exception {
        public MyCheckedException(String message) {
            super(message);
        }
    }

    /**
     * Checked 예외는
     * 예외를 던지거나 잡아야 한다.
     */
    static class Service {
        Repository repository = new Repository();

        /**
         * 예외를 잡아서 처리하는 코드
         */
        public void callCatch() {
            try {
                repository.call();

            } catch (MyCheckedException e) {
                log.info("error 발생");
            }
        }

        /**
         * 체크 예외를 밖으로 던진다.
         * @throws MyCheckedException
         */
        public void callThrow() throws MyCheckedException {
            repository.call();
        }
    }

    static class Repository {
        public void call() throws MyCheckedException{
            throw new MyCheckedException("ex");
        }
    }
}
