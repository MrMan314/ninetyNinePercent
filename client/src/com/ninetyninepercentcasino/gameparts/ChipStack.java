package com.ninetyninepercentcasino.gameparts;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ChipStack extends VerticalGroup{
    public ChipStack(){
        this.space(-88);
        this.reverse();
    }
    public void addChip(ChipActor chip){
        this.addActor(chip);
        this.invalidateHierarchy();
    }

}
