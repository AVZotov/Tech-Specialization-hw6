package ru.geekbrains.server;

import ru.geekbrains.clients.Observer;
import ru.geekbrains.services.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerController implements Observed {
    private boolean isServerActive;
    private final List<Observer> observers = new ArrayList<>();
    private List<String> loggedMessages = new ArrayList<>();
    private ServerView serverView;
    private final Repository repository;

    protected ServerController(Repository repository){
        this.repository = repository;
    }

    public void setServerView(ServerView serverView) {
        this.serverView = serverView;
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
    public void notifyObservers(Observer notificationOwner, String notification) {
            serverView.showMessage(notification);

            for (Observer observer : observers){
                if (!observer.equals(notificationOwner)){
                observer.handleEvent(notification);
            }
        }
    }

    public boolean startServer(){
        if (isServerActive){
            return false;
        }

        isServerActive = true;
        loadLoggedMessages();
        return true;
    }

    public boolean stopServer(){
        if (!isServerActive){
            return false;
        }

        isServerActive = false;
        return true;
    }

    public boolean requestToConnect(Observer observer, String name) {
        if (!isServerActive){
            return false;
        }
        if (observers.contains(observer)){
            return false;
        }

        addObserver(observer);
        sendLoggedMessages(observer);
        notifyObservers(observer, name + " connected to server");
        return true;
    }

    public boolean requestToDisconnect(Observer observer, String observerName) {
        removeObserver(observer);
        notifyObservers(observer,observerName + " disconnected from server");

        return true;
    }

    public void receiveMessage(Observer observer, String from, String message){
        notifyObservers(observer, String.format("%s: %s", from, message));
        saveMessageToLog(String.format("%s: %s", from, message));
    }

    private void sendLoggedMessages(Observer observer) {
        observer.handleEvent("*".repeat(40));
        observer.handleEvent("LOGGED MESSAGES");

        for (String message : loggedMessages){
            observer.handleEvent(message);
        }

        observer.handleEvent("*".repeat(40));
    }

    private void loadLoggedMessages() {
        try {
            loggedMessages = repository.load();
        } catch (IOException e) {
            serverView.showMessage(e.getMessage());
        }
    }

    private void saveMessageToLog(String message){
        try {
            repository.save(message + System.lineSeparator());
        } catch (IOException e) {
            serverView.showMessage(e.getMessage());
        }
    }
}
