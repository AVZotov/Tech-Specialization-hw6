package ru.geekbrains.server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerGUI extends JFrame implements ServerView {

    private static final int WIDTH = 555;
    private static final int HEIGHT = 507;
    private final JTextArea txtAreaServerLog = new JTextArea();
    private ServerController serverController;

    public ServerGUI() throws HeadlessException {
        createWindow();
    }

    public void setServerController(ServerController serverController) {
        this.serverController = serverController;
    }

    @Override
    public void startServerCommand() {
        boolean response = serverController.startServer();
        showMessage(response ? "Server successfully started" : "Server already started");
    }

    @Override
    public void stopServerCommand() {
        if (serverController.stopServer()){
            showMessage("Server stopped");
        }
    }

    @Override
    public void showMessage(String message) {
        txtAreaServerLog.append(message + System.lineSeparator());
    }

    private void createWindow(){
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if(JOptionPane.showConfirmDialog(e.getWindow(),"Are sure to close server?", "", JOptionPane.YES_NO_OPTION) == 0){
                    e.getWindow().dispose();
                }
            }
        });
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Server Window");
        setAlwaysOnTop(true);

        txtAreaServerLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaServerLog);
        add(scrollPane);

        JPanel bottomPanel = createBottomPanel();
        Container container = getContentPane();
        container.add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createBottomPanel(){
        JPanel grid = new JPanel(new GridLayout(1, 2, 5, 0) );
        JButton buttonStart = new JButton("Start Server");
        grid.add(buttonStart);
        JButton buttonStop = new JButton("Stop Server");
        grid.add(buttonStop);
        JPanel flowDirection = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        flowDirection.add(grid);
        buttonStart.addActionListener(e -> startServerCommand());
        buttonStop.addActionListener(e -> stopServerCommand());

        return flowDirection;
    }
}
