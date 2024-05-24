package com.ninetyninepercentcasino.net;

public class BJGameEnd {


    private double netMoney;
    private int winner;
    public BJGameEnd(int winner, double netMoney){
        this.winner = winner;
        this.netMoney = netMoney;
    }
    public int getWinner(){
        return winner;
    }
    public double getNetMoney() {
        return netMoney;
    }
}
