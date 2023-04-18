package ru.kkushaeva.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class ChatIO {
    private boolean stop;
    private Socket s;
    private BufferedReader br;
    private PrintWriter pw;
    public ChatIO(Socket s) throws IOException {
        this.s = s;
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        pw = new PrintWriter(s.getOutputStream());
    }

    public void startReceiving(Function<String, Void> parser) throws IOException {
        stop = false;
        while (!stop){
            var msg = br.readLine();
            parser.apply(msg);
        }
    }

    public void sendMessage(String msg){
        pw.println(msg);
        pw.flush();
    }

    public void stopReceiving(){
        stop = true;
    }
}
