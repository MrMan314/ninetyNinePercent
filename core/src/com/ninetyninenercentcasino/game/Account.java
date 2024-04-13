package com.ninetyninenercentcasino.game;

public class Account {
    String username, salt, hash;
    public Account(String username, String salt, String hash) {
        this.username=username;
        this.salt=salt;
        this.hash=hash;
    }
}