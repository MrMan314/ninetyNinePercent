package com.ninetyninepercentcasino.database;

/**
 * handles a player account with a balance, username, and password
 */
public class Account {
    String username;
    byte[] address;

    public Account(String username) {
        this.username=username;
    }
    public void addBalance(double amountAdded){
    }
    public void withdraw(double withdrawAmount){

    }
}