package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.nio.channels.ConnectionPendingException;
import java.sql.SQLException;

@Slf4j
public class UncheckedAppTest {

    @Test
    void uncheked() {

        Controller controller = new Controller();
        Assertions.assertThatThrownBy(() -> controller.request())
                .isInstanceOf(Exception.class);
    }

    @Test
    void pringEx() {
        Controller controller = new Controller();
        try {
            controller.request();
        } catch (Exception e) {
            log.info("ex", e);
        }
    }

    static class Controller {
        Service service = new Service();
        public void request() {
            service.logic();
        }
    }

    static class Service {
        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void logic() {
            repository.call();
            networkClient.call();
        }
    }

    /**
     * Exception을 상속 받은 예외는 체크 예외가 된다.
     */
    static class RuntimeSQLException extends RuntimeException {
        public RuntimeSQLException(Throwable cause) {
            super(cause);
        }
    }

    /**
     * Exception을 상속 받은 예외는 체크 예외가 된다.
     */
    static class RuntimeConnectionException extends RuntimeException {
        public RuntimeConnectionException(String message) {
            super(message);
        }
    }

    static class NetworkClient {
        public void call() {
            throw new RuntimeConnectionException("연결 싪패");
        }
    }

    // 여기서 SQL 을 변경해서 익셉션을 던지는 이유느
    // 종속이 안되도록 하기 위해서다
    static class Repository {
        public void call() {
            try {
                runSQL();
            } catch (SQLException e) {
                throw new RuntimeSQLException(e);
            }
        }
        public void runSQL () throws SQLException{
            throw new SQLException("ex");
        }
    }
}
