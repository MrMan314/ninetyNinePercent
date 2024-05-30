package com.ninetyninepercentcasino.net;

public class BJPayout extends DTO {
    private final double winnings;
    public BJPayout(double winnings){
        this.winnings = winnings;
    }
    public double getWinnings(){
        return winnings;
    }
}
