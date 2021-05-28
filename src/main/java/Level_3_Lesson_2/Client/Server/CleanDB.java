package Level_3_Lesson_2.Client.Server;

//Этот класс создал для проведения экспериментов)))

import java.sql.*;

public class CleanDB {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        connectDB();
        createDB();
        writeDB();
        System.out.println("Сейчас в БД:");
        readDB();

        int result = statmt.executeUpdate("DROP TABLE users;");

        conn.close();
    }

    public static void connectDB() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:mydatabase.db");
        System.out.println("База Подключена!");
    }
    public static void createDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'users' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'login' text NOT NULL," +
                "'password' text NOT NULL," +
                "'nick' text NOT NULL );");

        System.out.println("Таблица создана или уже существует.");
    }
    public static void writeDB() throws SQLException
    {
        statmt.execute("INSERT INTO 'users' ('login', 'password', 'nick') VALUES ('login1', 'pass1', 'Nikita'); ");
        statmt.execute("INSERT INTO 'users' ('login', 'password', 'nick') VALUES ('login2', 'pass2', 'Kolia'); ");
        statmt.execute("INSERT INTO 'users' ('login', 'password', 'nick') VALUES ('login3', 'pass3', 'Maks'); ");

        System.out.println("Таблица заполнена");
    }
    public static void readDB() throws SQLException {
        resSet = statmt.executeQuery("SELECT * FROM users");
        while (resSet.next()) {                     // Пока есть строки
            String name = resSet.getString(2);        // Или rs.getString("Name");
            System.out.println(resSet.getString(1)+"    "+resSet.getString(2)+"    "+resSet.getString(3)+"    "+resSet.getString(4));
        }
    }
}
