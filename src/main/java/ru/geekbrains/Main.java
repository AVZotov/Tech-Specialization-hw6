package ru.geekbrains;

import ru.geekbrains.clients.Client;
import ru.geekbrains.server.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();

        Client client1 = new Client("Sergey", server);
        Client client2 = new Client("Ivan", server);

    }
}