package Level_3_Lesson_2.Client.Server;
<<<<<<< HEAD
//Интерфейс аунтентификации.
=======
//Интерфейс аунтентификации
>>>>>>> d508d91 (Сетевой чат с подключением к БД и возможностью смены ника. Если в коммите не будет БД, то на первом запуске сервера необходимо раскоментитить метод jdbcManager.writeDB(); на 30 строке класса MyServer)

import java.sql.SQLException;

public interface AuthService {
    void start();
    String getNickByLoginPass(String login, String pass) throws SQLException;
    void stop();
}
