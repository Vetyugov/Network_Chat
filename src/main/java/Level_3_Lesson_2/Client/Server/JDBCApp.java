package Level_3_Lesson_2.Client.Server;

import java.sql.*;

public class JDBCApp {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    public void connectDB() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:mydatabase.db");
        System.out.println("База Подключена!");
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

        System.out.println("Таблица создана или уже существует.");
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
            String name = resSet.getString(2);        // Или rs.getString("Name");
            System.out.println(resSet.getString(1)+"    "+resSet.getString(2)+"    "+resSet.getString(3)+"    "+resSet.getString(4));
        }
    }
}
