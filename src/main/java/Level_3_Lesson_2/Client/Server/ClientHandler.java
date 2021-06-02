package Level_3_Lesson_2.Client.Server;

//Класс клиента

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private String login;

    private FileReader reader;

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

            ExecutorService service = Executors.newFixedThreadPool(8);
            service.execute(() -> {
                try {
                    if (!this.socket.isClosed()){
                        this.socket.setSoTimeout(5000);
                        authentication();
                        this.socket.setSoTimeout(0);
                        getLastHundredMsg();
                        readMessages();
                    }
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        closeConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
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

    public void closeConnection() throws IOException {
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

    private void getLastHundredMsg() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("allHistory_.txt"))) {
            String str;
            long countLines = 0L;
            while ((reader.readLine()) != null) {
                countLines++;
            }
            reader.close();
            BufferedReader readerTwice = new BufferedReader(new FileReader("allHistory_.txt"));
            long countReadLines = countLines - 20L;
            if (countReadLines < 0){
                countReadLines = 0;
            }
            long i=0;
            while ((str = readerTwice.readLine()) != null) {
                i++;
                if((i>=countReadLines)&&(i<countLines)){
                    if (!(str.startsWith("/w"))){
                        sendMsg(str);
                    }
                    if (str.startsWith("/w "+name)){
                        String[] parts = str.split("\\s");
                        System.out.println(parts.length);
                        for (int j = 2; j < parts.length; j++) {
                            System.out.println(parts[j]+ " ");
                        }
                        str = "";
                        for (int j =2; j < parts.length; j++) {
                            if (j == 2) {
                                str = parts[j];
                            }else {
                                str = str +" "+ parts[j];
                            }
                        }
                        sendMsg(str);
                    }
                }
            }
            readerTwice.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    };
}

