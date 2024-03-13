package ru.geekbrains;

import ru.geekbrains.server.Server;
import ru.geekbrains.server.ServerGUI;
import ru.geekbrains.services.FileRepository;

public class Main {
    public static void main(String[] args) {

        Server server = new Server(new FileRepository(), new ServerGUI());
    }
}