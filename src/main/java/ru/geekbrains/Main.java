package ru.geekbrains;

import ru.geekbrains.clients.Client;
import ru.geekbrains.clients.ClientController;
import ru.geekbrains.clients.ClientGUI;
import ru.geekbrains.server.Server;
import ru.geekbrains.server.ServerGUI;
import ru.geekbrains.services.FileRepository;

public class Main {
    public static void main(String[] args) {

        Server server = new Server(new FileRepository(), new ServerGUI());
        Client client1 = new Client(new ClientGUI("Alexey"), server.getServerController());
        Client client2 = new Client(new ClientGUI("Peter"), server.getServerController());
        Client client3 = new Client(new ClientGUI("Ivan"), server.getServerController());
    }
}