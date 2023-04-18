package ru.kkushaeva.net;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private int port;
    private boolean stop;
    ServerSocket ss;
    public Server(int port){
        this.port = port;
    }
    public void start() throws IOException {
        ss = new ServerSocket(port);
        stop = false;
        new Thread(()->{
            while (!stop) {
                try {
                    var s = ss.accept();
                    new ConnectedClient(s).start();
                } catch (IOException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            }
        }).start();
    }

    public void stop() throws IOException {
        stop = true;
        ss.close();
    }
}
