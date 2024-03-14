package ru.geekbrains.clients;

import ru.geekbrains.server.ServerController;

public class Client{

    public Client(ClientView clientView, ServerController serverController) {
        ClientController clientController = new ClientController(serverController);
        clientController.setClientView(clientView);
        clientView.setClientController(clientController);
    }
}
