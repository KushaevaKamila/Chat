package ru.kkushaeva.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    private String host;
    private int port;
    private Socket s;
    private boolean stop;
    private ChatIO cio;
    public Client(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void start() throws IOException {
        stop = false;
        s = new Socket(host, port);
        cio = new ChatIO(s);
        new Thread(()->{
            try {
                cio.startReceiving(this::parse);
            } catch (IOException e) {
                System.out.println("Ошибка подключённого клиента: " + e.getMessage());
            }
        }).start();
        var cbr = new BufferedReader(new InputStreamReader(System.in));
        while (!stop){
            cio.sendMessage(cbr.readLine());
        }
    }

    public Void parse(String msg){
        var data = msg.split(":", 2);
        //System.out.println(data[0] + " " + data[1]);
        Command cmd = null;
        try{
            cmd = Command.valueOf(data[0]);
        }
        catch(Exception ignored){
        }
        switch (cmd){
            case INTRODUCE:{
                if (data.length > 1 && data[1].trim().length() > 0) {
                    System.out.println(data[1]);
                }
                else{
                    System.out.println("Представьтесь, пожалуйста: ");
                }
                break;
            }
            case MESSAGE:{
                if (data.length > 1 && data[1].trim().length() > 0) {
                    System.out.println(data[1]);
                }
                break;
            }
            case LOGGED_IN:{
                if (data.length > 1 && data[1].trim().length() > 0) {
                    System.out.println("Пользователь " + data[1] + " вошёл в чат");
                }
                break;
            }
            case LOGGED_OUT:{
                if (data.length > 1 && data[1].trim().length() > 0) {
                    System.out.println("Пользователь " + data[1] + " покинул чат");
                }
                break;
            }
            case null:{
            }
        }
        return null;
    }

    public void send(String userData) {
        cio.sendMessage(userData);
    }

    public void stop(){
        stop = true;
        cio.stopReceiving();
    }
}
