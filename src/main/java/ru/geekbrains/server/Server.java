package ru.geekbrains.server;

import ru.geekbrains.clients.Client;
import ru.geekbrains.clients.Observer;
import ru.geekbrains.services.FileService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Server extends JFrame implements Observed {
    private static final int WIDTH = 555;
    private static final int HEIGHT = 507;

    private final JTextArea serverLog = new JTextArea();
    private boolean isServerActive;
    private final List<Observer> observers = new ArrayList<>();
    private List<String> loggedMessages = new ArrayList<>();
    private final FileService fileService = new FileService();


    public Server() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Server Chat");
        setAlwaysOnTop(true);

        serverLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(serverLog);
        add(scrollPane);

        JPanel grid = new JPanel(new GridLayout(1, 2, 5, 0) );
        JButton buttonStart = new JButton("Start Server");
        grid.add(buttonStart);
        JButton buttonStop = new JButton("Stop Server");
        grid.add(buttonStop);
        JPanel flowDirection = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        flowDirection.add(grid);
        Container container = getContentPane();
        container.add(flowDirection, BorderLayout.SOUTH);

        buttonStart.addActionListener(e -> {
            if (isServerActive){
                serverLog.append("Server already started\n");
                return;
            }

            isServerActive = true;
            serverLog.append("Server Started\n");
            loadLoggedMessages();
        });

        buttonStop.addActionListener(e -> {
            if (isServerActive){
                isServerActive = false;
                serverLog.append("Server Stopped\n");

                for (Observer observer : observers){
                    ((Client) observer).logout();
                }

                for (int i = 0; i < observers.size(); i++) {
                    ((Client) observers.get(i)).logout();
                    removeObserver(observers.get(i));
                }

                return;
            }

            serverLog.append("Server already stopped\n");
        });

        setVisible(true);
    }

    @Override
    public void addObserver(Observer observer) {

        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        serverLog.append(message + "\n");

        for (Observer observer : observers){
            observer.handleEvent(message);
        }
    }

    public boolean getServerStatus(){
        return isServerActive;
    }

    public List<String> getLoggedMessages(){
        return loggedMessages;
    }

    private void loadLoggedMessages() {
        try {
            loggedMessages = fileService.load();
        } catch (IOException e) {
            serverLog.append(e.getMessage() + System.lineSeparator());
        }
    }
}
