package ru.geekbrains.services;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    public static final String FILE_NAME = "log.txt";
    public void save(String message) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME, true)) {
            fileOutputStream.write(message.getBytes(StandardCharsets.UTF_8));
        }
    }

    public List<String> load() throws IOException {
        List<String> messages = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME))){
            String message = bufferedReader.readLine();

            while (message != null){
                messages.add(message);
                message = bufferedReader.readLine();
            }
        }

        return messages;
    }
}
