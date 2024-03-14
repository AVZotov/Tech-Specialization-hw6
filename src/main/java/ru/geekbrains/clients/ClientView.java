package ru.geekbrains.clients;

public interface ClientView {
    void connectServerCommand();
    void disconnectFromServerCommand();
    void sendMessageCommand();
    void showMessage(String message);
    void setClientController(ClientController clientController);
}
