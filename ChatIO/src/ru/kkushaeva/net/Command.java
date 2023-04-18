package ru.kkushaeva.net;

public enum Command {
    INTRODUCE,
    MESSAGE,
    LOGGED_IN,
    LOGGED_OUT;
    public String toString(){
        return this.name();
    }
}
