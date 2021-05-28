package Level_3_Lesson_2.Client.Server;
<<<<<<< HEAD
//Запуск сервера.
=======
//Запуск сервера
>>>>>>> d508d91 (Сетевой чат с подключением к БД и возможностью смены ника. Если в коммите не будет БД, то на первом запуске сервера необходимо раскоментитить метод jdbcManager.writeDB(); на 30 строке класса MyServer)

public class ServerApp {
    public static void main(String[] args) {
        new MyServer();
    }
}
