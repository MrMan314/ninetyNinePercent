package com.ninetyninepercentcasino.game.gameparts;

/**
 * Models a chip, an item commonly used in place of cash at a casino
 * @author Grant Liang
 */
public class Chip {
    private final double value;
    public Chip(double value){
        this.value = value;
    }
    public double getValue() {
        return value;
    }
}
