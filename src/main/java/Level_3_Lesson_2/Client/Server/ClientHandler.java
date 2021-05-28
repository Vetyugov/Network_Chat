package Level_3_Lesson_2.Client.Server;
//Класс клиента.

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class ClientHandler {
    private MyServer myServer;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private String name;
    private String privateName;
    private String privateMsg;

    private long startTime;
    private String newName;

    public String getName() {
        return name;
    }

    public ClientHandler(MyServer myServer, Socket socket) {
        try {
            this.myServer = myServer;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.name = "";
            this.startTime = System.currentTimeMillis();
            new Thread(()->{
                while (true){
                    try {
                        if ((System.currentTimeMillis() - startTime > 30000) && (this.name == "")){
                            out.writeUTF("/end");
                            this.socket.close();
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }).start();

            new Thread(() -> {
                try {
                    if (!this.socket.isClosed()){
                        authentication();
                        readMessages();
                    }
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException("Проблемы при создании обработчика клиента");
        }
    }

    public void authentication() throws IOException, SQLException {

        while (true) {
            String str = in.readUTF();
            if (str.startsWith("/auth")) {
                String[] parts = str.split("\\s");
                String nick = myServer.getAuthService().getNickByLoginPass(parts[1], parts[2]);
                if (nick != null) {
                    if (!myServer.isNickBusy(nick)) {
                        sendMsg("/authok " + nick);
                        name = nick;
                        myServer.broadcastMsg(name + " зашел в чат");
                        myServer.subscribe(this);
                        return;
                    } else {
                        sendMsg("Учетная запись уже используется");
                    }
                } else {
                    sendMsg("Неверные логин/пароль");
                }
            }
        }
    }

    public void readMessages() throws IOException, SQLException {
        while (true) {
            String strFromClient = in.readUTF();
            System.out.println("от " + name + ": " + strFromClient);
            if (strFromClient.equals("/end")) {
                return;
            }
            //Отправляет личное сообщение
            if (strFromClient.startsWith("/w")){
                String[] parts = strFromClient.split("\\s");
                privateName = parts[1];
                privateMsg = this.name + ":";
                for (int i = 2; i < parts.length; i++) {
                        privateMsg = privateMsg + " " + parts[i];
                }
                myServer.privateMsg(privateName, privateMsg);
            } else {
                myServer.broadcastMsg(name + ": " + strFromClient);
            }

//            Смена ника
            if (strFromClient.startsWith("/change")){
                String[] parts = strFromClient.split("\\s");
                newName = parts[1];
                myServer.changeNick(this.name, newName);
                this.name = newName;
            }
        }
    }

    //Отправляет в поток сообщения
    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        myServer.unsubscribe(this);
        if(this.name != ""){
            myServer.broadcastMsg(name + " вышел из чата");
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

