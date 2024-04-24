package com.ninetyninepercentcasino.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PlayButton extends Actor {
    private Texture playButtonTexture = new Texture("DesktopWindowIcon.png");
    public PlayButton(){
        setBounds(1f, 1f, 1f, 1f);
    }
    public void draw(SpriteBatch batch, float x, float y){
        batch.draw(playButtonTexture, 0.5f, 0.5f, x, y);
    }
    public void dispose(){
        playButtonTexture.dispose();
    }
}
