package com.ninetyninepercentcasino.gameparts;

import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

import java.util.Stack;

public class ChipStack extends VerticalGroup {
    public ChipStack(){
    }
    public void addChip(ChipActor chip){
        this.addActor(chip);
        invalidateHierarchy();
    }

}
