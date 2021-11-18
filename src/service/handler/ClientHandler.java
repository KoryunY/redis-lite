package service.handler;

import controller.RequestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private final RequestController requestController = new RequestController();

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String inputLine = null;
        while (true) {
            try {
                inputLine = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (inputLine == null) break;
            if ("ex".equals(inputLine)) {
                out.println("bye");
                try {
                    in.close();
                    out.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            try {
                out.println(requestController.parseInput(inputLine));
            } catch (NullPointerException ex) {
                out.println("(null)");
            } catch (ClassCastException ex) {
                out.println("Wrong Type of argument");
            } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
                out.println(ex.getMessage());
            }
        }
    }
}
