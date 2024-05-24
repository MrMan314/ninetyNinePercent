package com.ninetyninepercentcasino.gameparts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ninetyninepercentcasino.game.gameparts.Chip;

public class ChipActor extends Actor {
    private Chip chip;
    private Sprite sprite;
    final static float POPDISTANCE = 5;
    boolean popped = false;

    public ChipActor(Chip chip){
        this.chip = chip;
        sprite = new Sprite(findTexture());
        sprite.setSize(192, 192 * ((float) 72/128));
        setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight());
        sprite.setPosition(getX(), getY());
        addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer == -1) popped = true;
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer == -1) popped = false;
            }
        });
    }
    public void draw(Batch batch, float parentAlpha){
        if(popped) batch.draw(sprite, getX(), getY()+POPDISTANCE, sprite.getWidth(), sprite.getHeight());
        else batch.draw(sprite, getX(), getY(), sprite.getWidth(), sprite.getHeight());
    }
    private TextureRegion findTexture(){
        Texture chips = new Texture("GameAssets/Isometric/Chips/ChipsA_Outline_Flat_Small-128x72.png");
        int x = 0;
        int y = 0;
        double chipValue = chip.getValue();
        if(chipValue <= 1) {
            x = 0;
        }
        else if(chipValue <= 5){
            x = 128;
        }
        else if(chipValue <= 10){
            x = 128*2;
        }
        else if(chipValue <= 25){
            x = 128*3;
        }
        else if(chipValue <= 100){
            x = 128*4;
        }
        return new TextureRegion(chips, x+20, y+8, 88, 56);
    }
    public void unpop(){
        popped = false;
    }
}
