package ru.geekbrains.clients;

import ru.geekbrains.server.Server;

import javax.swing.*;
import java.awt.*;

public class Client extends JFrame implements Observer
{
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final String PORT = "8189";
    private static final String PASSWORD = "12345";
    private static final int WIDTH = 555;
    private static final int HEIGHT = 507;
    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField textFieldIpAddress = new JTextField(IP_ADDRESS);
    private final JTextField textFieldPort = new JTextField(PORT);
    private final JPasswordField passwordFieldPassword = new JPasswordField(PASSWORD);
    private final JTextField textFieldName;
    private final JButton buttonLogin = new JButton("Login");
    private final JTextArea textAreaClientLog = new JTextArea();
    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JTextField textFieldMessage = new JTextField();
    private final JButton buttonSend = new JButton("Send");
    private boolean isLoggedIn;
    private final Server server;


    public Client(String name, Server server) {
        textFieldName = new JTextField(name);
        this.server = server;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Client Chat");

        panelTop.add(textFieldIpAddress);
        panelTop.add(textFieldPort);
        panelTop.add(new Label());
        panelTop.add(textFieldName);
        panelTop.add(passwordFieldPassword);
        panelTop.add(buttonLogin);
        add(panelTop, BorderLayout.NORTH);

        textAreaClientLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textAreaClientLog);
        add(scrollPane);

        panelBottom.add(textFieldMessage, BorderLayout.CENTER);
        panelBottom.add(buttonSend, BorderLayout.EAST);
        add(panelBottom, BorderLayout.SOUTH);

        buttonLogin.addActionListener(e -> login());
        buttonSend.addActionListener(e -> sendMessage());

        setVisible(true);
    }

    @Override
    public void handleEvent(String message) {
        textAreaClientLog.append(message + "\n");
    }
    public void logout(){
        panelTop.setVisible(true);
        isLoggedIn = false;
        textAreaClientLog.append("You are disconnected from server!\n");
    }

    private void sendMessage(){
        if (textFieldMessage.getText().isEmpty()){
            return;
        }

        if (!isLoggedIn){
            textAreaClientLog.append("You must login to start conversation!\n");
            return;
        }

        server.notifyObservers(textFieldMessage.getText());
        textFieldMessage.setText("");
    }

    private void login(){
        if (server.getServerStatus()){
            panelTop.setVisible(false);
            isLoggedIn = true;
            server.addObserver(this);
            textAreaClientLog.append("You are logged in\n");
            server.notifyObservers(textFieldName.getText() + " connected to Server");
            return;
        }
        textAreaClientLog.append("Remote server do not response!\n");
    }

}
