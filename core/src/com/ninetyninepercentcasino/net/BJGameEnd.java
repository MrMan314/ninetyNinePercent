package com.ninetyninepercentcasino.net;

import com.ninetyninepercentcasino.game.bj.BJDealer;

public class BJGameEnd {


    private double netMoney;
    private int winner;
    private BJDealer dealer;
    public BJGameEnd(int winner, double netMoney, BJDealer dealer){
        this.winner = winner;
        this.netMoney = netMoney;
        this.dealer = dealer;
    }
    public int getWinner(){
        return winner;
    }
    public double getNetMoney() {
        return netMoney;
    }
    public BJDealer getDealer(){
        return dealer;
    }
}
