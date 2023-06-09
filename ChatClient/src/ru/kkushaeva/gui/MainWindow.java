package ru.kkushaeva.gui;

import ru.kkushaeva.net.ChatEvent;
import ru.kkushaeva.net.ChatListener;
import ru.kkushaeva.net.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class MainWindow extends JFrame {
    private JLabel lbl;
    private JTextField tf;
    private JButton btn;
    private JTextArea ta;
    private JScrollPane sp;
    private Client client;
    public MainWindow(Client client){
        this.client = client;
        setSize(600,450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        lbl = new JLabel("");
        tf = new JTextField();
        btn = new JButton("Отправить");
        ta = new TextAreaImp();
        sp = new JScrollPane(ta);

        //region HorVer
        GroupLayout gl = new GroupLayout(getContentPane());
        setLayout(gl);
        gl.setHorizontalGroup(
                gl.createSequentialGroup()
                        .addGap(8)
                        .addGroup(
                                gl.createParallelGroup()
                                        .addComponent(lbl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                                        .addGroup(
                                                gl.createSequentialGroup()
                                                        .addComponent(tf, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                                                        .addGap(4)
                                                        .addComponent(btn, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                        )
                                        .addComponent(sp, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE))
                        .addGap(8)
        );
        gl.setVerticalGroup(
                gl.createSequentialGroup()
                        .addGap(8)
                        .addComponent(lbl, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(8)
                        .addGroup(
                                gl.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(tf, 27, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btn, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        )
                        .addGap(8)
                        .addComponent(sp, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                        .addGap(8)
        );
        //endregion
        try {
            client.start();
        } catch (IOException e) {
            getMessage("Ошибка: " + e.getMessage());
        }
        tf.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    sendMessage(tf.getText());
                    tf.setText("");
                }
            }
        });
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(tf.getText());
                tf.setText("");
            }
        }
        );
        client.addListener(new ChatListener() {
            @Override
            public void chatAvailiable(ChatEvent e) {
                getMessage(e.getMessage1());
            }
        });
    }
    public void getMessage(String msg)
    {
        ta.append(msg);
    }
    public void sendMessage(String msg)
    {
        client.send(msg);
    }
}