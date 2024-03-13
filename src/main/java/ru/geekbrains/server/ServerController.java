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
    public void notifyObservers(Object notification) {
        if (notification instanceof String message){
            serverView.showMessage(message);

            for (Observer observer : observers){
                observer.handleEvent(message);
            }
        }
    }

    public boolean startServer(){
        if (isServerActive){
            return false;
        }

        isServerActive = true;
        return true;
    }

    public boolean stopServer(){
        if (!isServerActive){
            return false;
        }

        isServerActive = false;
        return true;
    }

    public void receiveMessage(String message){
        notifyObservers(message);
    }

    public boolean getServerStatus(){
        return isServerActive;
    }

    private void loadLoggedMessages() {
        try {
            loggedMessages = repository.load();
        } catch (IOException e) {
            serverView.showMessage(e.getMessage() + System.lineSeparator());
        }
    }
}
