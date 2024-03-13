package ru.geekbrains.server;

import ru.geekbrains.services.Repository;

public class Server {
    private final ServerView serverView;
    private final ServerController serverController;

    public Server(Repository repository, ServerView serverView) {
        this.serverView = serverView;

        serverController = new ServerController(repository);
        serverController.setServerView(serverView);
        this.serverView.setServerController(serverController);
    }

    public ServerController getServerController(){
        return serverController;
    }

    public ServerView getServerView(){
        return serverView;
    }
}
