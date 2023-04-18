package ru.kkushaeva.net;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectedClient {
    private Socket s;
    private ChatIO cio;
    private static final ArrayList<ConnectedClient> clients = new ArrayList<>();
    private String name = null;
    public ConnectedClient(Socket client) throws IOException {
        s = client;
        cio = new ChatIO(s);
        clients.add(this);
        sendMessage(Command.INTRODUCE, "");
    }
    public void start(){
        new Thread(()->{
            try{
                cio.startReceiving(this::parse);
            } catch (IOException e){
                clients.remove(this);
                for (var client: clients){
                    client.sendMessage(Command.LOGGED_OUT, name);
                }
            }
        }).start();
    }

    private Void parse(String msg){
        if (name != null){
            for(var client: clients){
                client.sendMessage(Command.MESSAGE, ((client != this)? name: "Вы") + ": " + msg);
            }
        }
        else{
            boolean setName = true;
            for (var client: clients){
                if (msg.equalsIgnoreCase(client.name)){
                    setName = false;
                    break;
                }
            }
            /*if (msg == "Вы"){
                sendMessage(Command.INTRODUCE, "Такое имя использовать нельзя");
                setName = true;
            }*/
            setName &= (!msg.equalsIgnoreCase("Вы"));
            if (setName) {
                name = msg;
                for (var client: clients){
                    client.sendMessage(Command.LOGGED_IN, name);
                }
            }
            else{
                sendMessage(Command.INTRODUCE, "Это имя нельзя использовать");
            }
        }
        return null;
    }

    public void sendMessage(Command cmd, String msg){
        if (name != null || cmd == Command.INTRODUCE){
            cio.sendMessage(cmd + ":" + msg);
        }
    }
}
