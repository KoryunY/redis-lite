package model;

import service.handler.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;

public class Session {
    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true)
            new Thread(
                    new ClientHandler(serverSocket.accept()))
                    .start();

    }

    public void stop() throws IOException {
        serverSocket.close();
    }
}
