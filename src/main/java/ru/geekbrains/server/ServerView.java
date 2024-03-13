package ru.geekbrains.server;

public interface ServerView {
    void startServerCommand();
    void stopServerCommand();
    void showMessage(String message);
    void setServerController(ServerController serverController);
}
