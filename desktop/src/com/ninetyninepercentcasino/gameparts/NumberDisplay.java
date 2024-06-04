package com.ninetyninepercentcasino.gameparts;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class NumberDisplay extends Actor {
    private BitmapFont font;
    private String text;

    public NumberDisplay(){
        font = new BitmapFont();
        text = "";
    }
    public NumberDisplay(double number){
        font = new BitmapFont();
        setText(number);
    }
    @Override
    public void draw(Batch batch, float parentAlpha){
        font.draw(batch, text, getX(), getY());
    }
    public void setText(double number){
        text = Double.toString(number);
    }


}
