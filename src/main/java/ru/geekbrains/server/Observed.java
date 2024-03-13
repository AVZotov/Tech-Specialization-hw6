package ru.geekbrains.server;

import ru.geekbrains.clients.Observer;

public interface Observed {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(Object notification);
}
