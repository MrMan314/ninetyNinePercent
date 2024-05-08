package com.ninetyninepercentcasino.gameparts;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ninetyninepercentcasino.game.gameparts.Chip;

public class ChipActor extends Actor {
    private Chip chip;
    public ChipActor(Chip chip){
        this.chip = chip;
    }
}
