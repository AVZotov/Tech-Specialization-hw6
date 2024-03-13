package ru.geekbrains.services;

import java.io.IOException;
import java.util.List;

public interface Repository {
    void save(String message) throws IOException;
    List<String> load() throws IOException;
}
