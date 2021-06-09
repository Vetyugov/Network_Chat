package Network_Chat.Server;

//Серверное приложение

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MyServer {
    private final int PORT = 8189;

    private List<ClientHandler> clients;
    private AuthService authService;
    private JDBCApp jdbcManager;

    private FileWriter writer;

    private static final Logger LOGGER = LogManager.getLogger(MyServer.class);

    public AuthService getAuthService() {
        return authService;
    }

    public MyServer() {
        try (ServerSocket server = new ServerSocket(PORT)) {
            jdbcManager = new JDBCApp();
            jdbcManager.connectDB();
            jdbcManager.createDB();
//            jdbcManager.writeDB();
            jdbcManager.readDB();
            //Подключаемся к файлу
            writer = new FileWriter("allHistory_.txt", true);
            authService = new BaseAuthService();
            authService.start();
            clients = new ArrayList<>();
            while (true) {
                LOGGER.info("Сервер ожидает подключения");
                Socket socket = server.accept();
                LOGGER.info("Клиент подключен");
                new ClientHandler(this, socket);
            }
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.fatal("Ошибка в работе сервера");
        } catch (SQLException e) {
            LOGGER.fatal("Ошибка подключения к БД");
        } finally
         {
            if (authService != null) {
                authService.stop();

            }
        }
    }

    public synchronized boolean isNickBusy(String nick) {
        for (ClientHandler o : clients) {
            if (o.getName().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void broadcastMsg(String msg) throws IOException {
        writer.append("\n"+msg);
        LOGGER.info("\n"+msg);
        writer.flush();
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }

    public synchronized void privateMsg(String ToName, String msg) throws IOException {
        writer.append("\n"+"/w " +ToName+" "+msg);
        LOGGER.info("\n"+"/w " +ToName+" "+msg);
        writer.flush();
        for (ClientHandler o: clients) {
            if (o.getName().equals(ToName)){
                o.sendMsg(msg);
                break;
            }

        }
    }

    public synchronized void unsubscribe(ClientHandler o) {
        clients.remove(o);
    }

    public synchronized void subscribe(ClientHandler o) {
        clients.add(o);
    }

    public synchronized void changeNick(String prevNick, String newNick) throws SQLException {
        jdbcManager.changeNick(prevNick, newNick);
    }

        }
