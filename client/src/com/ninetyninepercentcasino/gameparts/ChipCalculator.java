package com.ninetyninepercentcasino.gameparts;

import java.util.ArrayList;

/**
 * class to calculate the values of multiple stacks of chips located on top of ChipHolders
 * @author Grant Liang
 */
public class ChipCalculator {
    private ArrayList<ChipHolder> chipHolders;
    public ChipCalculator(){
        chipHolders = new ArrayList<>();
    }
    public double calculate(){
        double total = 0;
        for(ChipHolder chipHolder : chipHolders){
            total += chipHolder.calculate();
        }
        return total;
    }
    public void addChipHolder(ChipHolder chipHolder){
        chipHolders.add(chipHolder);
    }
}
