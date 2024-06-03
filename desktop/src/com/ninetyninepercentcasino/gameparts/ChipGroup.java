package com.ninetyninepercentcasino.gameparts;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.ninetyninepercentcasino.game.gameparts.Chip;

public class ChipGroup extends Group {
    private ChipCalculator calculator;
    public ChipGroup(int whiteChips, int redChips, int blueChips, int greenChips, int blackChips, int numHolders){
        calculator = new ChipCalculator();
        addStack(1, whiteChips);
        addStack(5, redChips);
        addStack(10, blueChips);
        addStack(25, greenChips);
        addStack(100, blackChips);
        setupHolders(numHolders);
    }
    public void setupHolders(int numHolders){
        for(int i = 0; i < numHolders; i++){
            ChipHolder chipHolder = new ChipHolder();
            calculator.addChipHolder(chipHolder);
            addActor(chipHolder);
        }
    }
    public void addStack(int value, int numChips){
        ChipActor chipBelow = new ChipActor(new Chip(value));
        chipBelow.setName("1");
        addActor(chipBelow);
        for(int i = 0; i < numChips; i++){
            ChipActor chipAbove = new ChipActor(new Chip(value));
            chipAbove.setName(Integer.toString(i));
            addActor(chipAbove);
            chipAbove.attachToChip(chipBelow);
            chipBelow = chipAbove;
        }
    }
    public double calculate(){
        return calculator.calculate();
    }
}
