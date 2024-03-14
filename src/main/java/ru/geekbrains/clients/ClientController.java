package ru.geekbrains.clients;

import ru.geekbrains.server.ServerController;

public class ClientController implements Observer{
    private ClientView clientView;
    private final ServerController serverController;
    private boolean isConnected;

    protected ClientController(ServerController serverController) {
        this.serverController = serverController;
    }

    public boolean connectServer(String name) {
        if (!isConnected){
            isConnected = serverController.requestToConnect(this, name);
            return isConnected;
        }

        return false;
    }

    public void disconnectFromServer(String clientName) {
        if (!isConnected){
            return;
        }

        if(serverController.requestToDisconnect(this, clientName)){
            handleEvent("You disconnected from the server");
        }
    }

    public void sendMessage(String name, String message) {
        if (!isConnected){
            handleEvent("You must login to send messages");
            return;
        }

        serverController.receiveMessage(this, name, message);
        handleEvent("Your message: " + message);
    }

    @Override
    public void handleEvent(String message) {
        clientView.showMessage(message);
    }

    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
    }
}
