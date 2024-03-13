package ru.geekbrains.clients;

import ru.geekbrains.server.ServerController;
import ru.geekbrains.services.FileRepository;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class Client extends JFrame implements Observer
{
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final String PORT = "8189";
    private static final String PASSWORD = "12345";
    private static final int WIDTH = 555;
    private static final int HEIGHT = 507;
    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField textFieldName;
    private final JTextArea textAreaClientLog = new JTextArea();
    private final JTextField textFieldMessage = new JTextField();
    private boolean isLoggedIn;
    private final ServerController serverController;
    private final FileRepository fileRepository = new FileRepository();


    public Client(String name, ServerController serverController) {
        textFieldName = new JTextField(name);
        this.serverController = serverController;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Client Chat");

        JTextField textFieldIpAddress = new JTextField(IP_ADDRESS);
        panelTop.add(textFieldIpAddress);
        JTextField textFieldPort = new JTextField(PORT);
        panelTop.add(textFieldPort);
        panelTop.add(new Label());
        panelTop.add(textFieldName);
        JPasswordField passwordFieldPassword = new JPasswordField(PASSWORD);
        panelTop.add(passwordFieldPassword);
        JButton buttonLogin = new JButton("Login");
        panelTop.add(buttonLogin);
        add(panelTop, BorderLayout.NORTH);

        textAreaClientLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textAreaClientLog);
        add(scrollPane);

        JPanel panelBottom = new JPanel(new BorderLayout());
        panelBottom.add(textFieldMessage, BorderLayout.CENTER);
        JButton buttonSend = new JButton("Send");
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
        textAreaClientLog.append("You are disconnected from serverController!\n");
    }

    private void sendMessage(){
        if (textFieldMessage.getText().isEmpty()){
            return;
        }

        if (!isLoggedIn){
            textAreaClientLog.append("You must login to start conversation!\n");
            return;
        }

        String message = textFieldName.getText() + " said: " + textFieldMessage.getText();
        serverController.notifyObservers(message);
        logger(message);
        textFieldMessage.setText("");
    }

    private void login(){
        if (serverController.getServerStatus()){
            panelTop.setVisible(false);
            isLoggedIn = true;
            serverController.addObserver(this);
            textAreaClientLog.append("You are logged in\n");
            //getLoggedMessages();
            serverController.notifyObservers(textFieldName.getText() + " connected to ServerController");
            return;
        }
        textAreaClientLog.append("Remote server do not response!\n");
    }

    private void logger(String message){
        try {
            fileRepository.save(message + "\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

//    private void getLoggedMessages(){
//        List<String> messages = serverController.getLoggedMessages();
//
//        if (messages == null){
//            return;
//        }
//
//        textAreaClientLog.append("*".repeat(20));
//        textAreaClientLog.append(System.lineSeparator() + "LOGGED MESSAGES" + System.lineSeparator());
//
//        for (String message : messages){
//            textAreaClientLog.append(message);
//            textAreaClientLog.append(System.lineSeparator());
//        }
//
//        textAreaClientLog.append("*".repeat(20));
//        textAreaClientLog.append(System.lineSeparator());
//    }

}
