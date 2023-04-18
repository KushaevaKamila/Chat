package ru.kkushaeva;

import ru.kkushaeva.net.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        var s = new Server(5003);
        s.start();
        while (true){
            var cbr = new BufferedReader(new InputStreamReader(System.in));
            String userCommand = null;
            userCommand = cbr.readLine();
            if (userCommand == null || userCommand.equals("STOP")){
                s.stop();
                break;
            }
        }
    }
}
