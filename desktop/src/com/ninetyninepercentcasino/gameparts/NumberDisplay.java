package com.ninetyninepercentcasino.gameparts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class NumberDisplay extends Actor {
    private BitmapFont font;
    private Label label;
    private String text;

    public NumberDisplay(){
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        Texture texture = new Texture(Gdx.files.internal("fonts.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font = new BitmapFont(Gdx.files.classpath("fonts.fnt"), new TextureRegion(texture), false);
        labelStyle.font = font;
        text = "";
        label = new Label(text, labelStyle);
        label.setFontScale(10);
        label.setPosition(100, 10);
    }
    public NumberDisplay(int number){
        this();
        setText(number);
    }
    @Override
    public void draw(Batch batch, float parentAlpha){
        label.draw(batch, parentAlpha);
    }
    public void setText(int number){
        label.setText((int)number);
    }


}
