package Network_Chat.Server;

//Класс для работы с БД

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class JDBCApp {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    private static final Logger LOGGER = LogManager.getLogger(MyServer.class);

    public void connectDB() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:mydatabase.db");
        LOGGER.info("База Подключена!");
    }

    // --------Создание таблицы--------
    public void createDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'users' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'login' text NOT NULL," +
                "'password' text NOT NULL," +
                "'nick' text NOT NULL);");

        LOGGER.info("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public void writeDB() throws SQLException
    {
        statmt.execute("INSERT INTO 'users' ('login', 'password', 'nick') VALUES ('login1', 'pass1', 'Nikita'); ");
        statmt.execute("INSERT INTO 'users' ('login', 'password', 'nick') VALUES ('login2', 'pass2', 'Kolia'); ");
        statmt.execute("INSERT INTO 'users' ('login', 'password', 'nick') VALUES ('login3', 'pass3', 'Maks'); ");

        System.out.println("Таблица заполнена");
    }

    public String getNickFromDB(String userLogin, String userPass) throws SQLException
    {
        resSet = statmt.executeQuery("SELECT * FROM users WHERE (login = '" + userLogin+ "') AND (password = '" +userPass+ "');");
        try {
            return resSet.getString("nick");
        } catch (SQLException e){
            return null;
        }
    }

    public void changeNick(String prevNick, String newNick) throws SQLException {
        int result = statmt.executeUpdate("UPDATE users SET nick = '" + newNick + "' WHERE nick = '" + prevNick + "';");
        System.out.println("Обновлено "+result);


    }
    public void readDB() throws SQLException {
        System.out.println("Сейчас в БД:");
        resSet = statmt.executeQuery("SELECT * FROM users");
        while (resSet.next()) {                     // Пока есть строки
            System.out.println(resSet.getString(1)+"    "+resSet.getString(2)+"    "+resSet.getString(3)+"    "+resSet.getString(4));
        }
    }
}
