package hello.jdbc.connection;

import lombok.extern.slf4j.Slf4j;
import java.sql.Connection;//jdbc 표준 인터페이스가 제공하는 Connection
import java.sql.DriverManager;
import java.sql.SQLException;
import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class DBConnectionUtil {

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            //Connection은 인터페이스이고 구현체는 DB마다 달라짐
            //DriverManager가 의존성에 등록된 드라이버(h2, mysql)등을 찾으면서 접속할수있는지 확인하여 맞는 connection반환
            log.info("get connection={}, class={}", connection, connection.getClass());
            return connection;
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new IllegalStateException(e);//체크드 Exception 을 런타임 Exception 으로 바꿔서 던져둠
        }
    }
}
