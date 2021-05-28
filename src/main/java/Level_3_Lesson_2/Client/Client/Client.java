package Level_3_Lesson_2.Client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/*
Новый коммит
Формат сообщения для авторизации клиента    ->     /auth login1 pass1
Формат для отправли личного сообщения       ->     /w nick1 сообщение
Выйти из чата                               ->     /end
Сменить ник                                 ->     /change новый_ник
Сменить nik                                 ->     /change новый_ник
 */


public class Client {
    private JTextArea ta;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public Client() {
        try {
            clientWindow();
            openConnection();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public void openConnection() throws IOException, InterruptedException {
        String SERVER_ADDR = "localhost";
        int SERVER_PORT = 8189;
        socket = new Socket(SERVER_ADDR, SERVER_PORT);
        System.out.println("Сокет запущен");
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        //Поток, который читает, что пришло с сервера и пишет в консоль
        Thread thread1 = new Thread(() -> {
            try {
                while (true) {
                    String strFromServer = in.readUTF();
                    if (strFromServer.equalsIgnoreCase("/end")) {
                        break;
                    }
                    ta.append("\n"+strFromServer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread1.join();
    }

    public void closeConnection() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clientWindow(){
        JFrame frame = new JFrame("Chat Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        //Верхняя панель
        JPanel panelTop = new JPanel();
        //Поле логина
        JTextField login = new JTextField(8);
        login.setText("login1");
        //Поле пароля
        JTextField password = new JTextField(8);
        password.setText("pass1");
        //Кнопка входа
        JButton auth = new JButton("Войти в чат");
        auth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    out.writeUTF("/auth "+login.getText()+" "+ password.getText());
                    login.setText("");
                    password.setText("");
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
        panelTop.add(login);
        panelTop.add(password);
        panelTop.add(auth);

        //Нижняя панель
        JPanel panelBottom = new JPanel();
        //Поле ввода сообщения
        JTextField message = new JTextField(20);
        message.setBounds(20,20, 120, 20);
        //Кнопка отправки
        JButton send = new JButton("Отправить");
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    out.writeUTF(message.getText());
                    message.setText("");
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
        //Кнопка очистки текста
        JButton reset = new JButton("Сброс");

        panelBottom.add(message);
        panelBottom.add(send);
        panelBottom.add(reset);

        ta = new JTextArea();


        frame.getContentPane().add(BorderLayout.NORTH, panelTop);
        frame.getContentPane().add(BorderLayout.SOUTH, panelBottom);
        frame.getContentPane().add(BorderLayout.CENTER, ta);
        frame.setVisible(true);
    }

}
