package Level_3_Lesson_2.Client.Server;
//Реализация инферфейса авторизации

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseAuthService implements AuthService {

//    private class Entry {
//        private String login;
//        private String pass;
//        private String nick;
//
//        public Entry(String login, String pass, String nick) {
//            this.login = login;
//            this.pass = pass;
//            this.nick = nick;
//        }
//    }


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
//        for (Entry o : entries) {
//            if (o.login.equals(login) && o.pass.equals(pass)) return o.nick;
//        }
//        return null;
    }

}
