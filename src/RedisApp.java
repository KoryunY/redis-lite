import model.Session;

import java.io.*;


public class RedisApp {
    public static void main(String[] args) {
        Session server = new Session();
        try {
            server.start(4444);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                server.stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
