package Level_3_Lesson_2.Client.Server;
<<<<<<< HEAD
//Реализация инферфейса авторизации
=======
//Реализация инферфейса авторизации.
>>>>>>> d508d91 (Сетевой чат с подключением к БД и возможностью смены ника. Если в коммите не будет БД, то на первом запуске сервера необходимо раскоментитить метод jdbcManager.writeDB(); на 30 строке класса MyServer)

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseAuthService implements AuthService {

    @Override
    public void start() {
        System.out.println("Сервис аутентификации запущен");
    }

    @Override
    public void stop() {
        System.out.println("Сервис аутентификации остановлен");
    }

    @Override
    public String getNickByLoginPass(String login, String pass) throws SQLException {
        JDBCApp myJDBCapp = new JDBCApp();
        return myJDBCapp.getNickFromDB(login, pass);
    }

}
