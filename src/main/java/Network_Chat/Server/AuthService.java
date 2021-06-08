package Network_Chat.Server;
//Интерфейс аунтентификации

import java.sql.SQLException;

public interface AuthService {
    void start();
    String getNickByLoginPass(String login, String pass) throws SQLException;
    void stop();
}
