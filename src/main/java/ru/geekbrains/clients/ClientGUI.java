package ru.geekbrains.clients;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientGUI extends JFrame implements ClientView{
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final String PORT = "8189";
    private static final String PASSWORD = "12345";
    private static final int WIDTH = 555;
    private static final int HEIGHT = 507;
    private JPanel panelTop;
    private JPanel panelBottom;
    private JTextField textFieldName;
    private JTextArea textAreaClientLog;
    private JTextField textFieldMessage;
    private ClientController clientController;

    public ClientGUI(String name) {
        createMainWindow(name);
    }

    @Override
    public void connectServerCommand() {
        if (clientController.connectServer(textFieldName.getText())){
            showMessage("You are connected to the server");
            panelTop.setVisible(false);
            return;
        }

        showMessage("Remote server do not response, connection failed");
    }

    @Override
    public void disconnectFromServerCommand() {
        clientController.disconnectFromServer(textFieldName.getText());
    }

    @Override
    public void sendMessageCommand() {
        clientController.sendMessage(textFieldName.getText(), textFieldMessage.getText());
        textFieldMessage.setText("");
    }

    @Override
    public void showMessage(String message) {
        textAreaClientLog.append(message + System.lineSeparator());
    }

    @Override
    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    private void createTopPanel(){
        panelTop = new JPanel(new GridLayout(2, 3));
        JTextField textFieldIpAddress = new JTextField(IP_ADDRESS);
        panelTop.add(textFieldIpAddress);
        JTextField textFieldPort = new JTextField(PORT);
        panelTop.add(textFieldPort);
        panelTop.add(new Label());
        textFieldName = new JTextField();
        panelTop.add(textFieldName);
        JPasswordField passwordFieldPassword = new JPasswordField(PASSWORD);
        panelTop.add(passwordFieldPassword);
        JButton buttonLogin = new JButton("Login");
        panelTop.add(buttonLogin);
        buttonLogin.addActionListener(e -> connectServerCommand());
    }

    private void createBottomPanel(){
        panelBottom = new JPanel(new BorderLayout());
        textFieldMessage = new JTextField();
        panelBottom.add(textFieldMessage, BorderLayout.CENTER);
        JButton buttonSend = new JButton("Send");
        panelBottom.add(buttonSend, BorderLayout.EAST);
        buttonSend.addActionListener(e -> sendMessageCommand());
    }

    private void createMainWindow(String name){
        createTopPanel();
        createBottomPanel();
        textFieldName.setText(name);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if(JOptionPane.showConfirmDialog(e.getWindow(),"Are sure to close chat window?", "", JOptionPane.YES_NO_OPTION) == 0){
                    disconnectFromServerCommand();
                    e.getWindow().dispose();
                }
            }
        });

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Client Chat");

        textAreaClientLog = new JTextArea();
        textAreaClientLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textAreaClientLog);
        add(scrollPane);

        add(panelTop, BorderLayout.NORTH);
        add(panelBottom, BorderLayout.SOUTH);

        setVisible(true);
    }
}
