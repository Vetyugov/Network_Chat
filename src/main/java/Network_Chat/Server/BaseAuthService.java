package Network_Chat.Server;

//Реализация инферфейса авторизации

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class BaseAuthService implements AuthService {

    private static final Logger LOGGER = LogManager.getLogger(MyServer.class);

    @Override
    public void start() {
        LOGGER.info("Сервис аутентификации запущен");
    }

    @Override
    public void stop() {
        LOGGER.info("Сервис аутентификации остановлен");
    }

    @Override
    public String getNickByLoginPass(String login, String pass) throws SQLException {
        JDBCApp myJDBCapp = new JDBCApp();
        return myJDBCapp.getNickFromDB(login, pass);
    }

}
