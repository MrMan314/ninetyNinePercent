package com.ninetyninepercentcasino.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class PlayButton extends Actor {
    private final Sprite sprite;
    public boolean clicked = false;
    public PlayButton(final Game game){
        sprite = new Sprite(new Texture("DesktopWindowIcon.png"));
        sprite.setOriginCenter();
        sprite.setPosition(1f, 1f);
        sprite.setSize(1f, 1f);
        setTouchable(Touchable.enabled);
        this.setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ((PlayButton)event.getTarget()).clicked = true;
                return true;
            }
        });
    }
    @Override
    public void draw(Batch batch, float parentAlpha){
        System.out.println(clicked);
        sprite.draw(batch);
    }
    public void dispose(){

    }
}
