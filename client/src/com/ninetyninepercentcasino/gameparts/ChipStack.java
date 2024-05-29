package com.ninetyninepercentcasino.gameparts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ChipStack extends VerticalGroup{
    private Vector2 lastTouch;
    public ChipStack(){
        this.space(-88);
        this.reverse();
        lastTouch = new Vector2();
        addListener(new ClickListener(){
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                lastTouch.set(screenX, screenY);
                return true;
            }
            public void touchDragged (InputEvent event, float x, float y, int pointer) {
            }
        });
    }
    public void addChip(ChipActor chip){
        this.addActor(chip);
        this.invalidateHierarchy();
    }

}
