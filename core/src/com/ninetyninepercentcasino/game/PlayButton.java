package com.ninetyninepercentcasino.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ninetyninepercentcasino.game.screens.GameSelect;

public class PlayButton extends Button {
    Game game;
    private final Sprite sprite;
    public boolean clicked = false;
    public boolean mouseOn = false;
    final float WIDTH = 482f;
    final float HEIGHT = 184f;
    public PlayButton(Game game){
        this.game = game;
        sprite = new Sprite(new Texture("MainMenu/WoodenBanner.png"));
        setTouchable(Touchable.enabled);
        sprite.setCenter(960f, 640f);
        sprite.setSize(500f, 500f*(HEIGHT/WIDTH));
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ((PlayButton)event.getTarget()).clicked = true;
                return true;
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                ((PlayButton)event.getTarget()).mouseOn = true;
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                ((PlayButton)event.getTarget()).mouseOn = false;
            }
        });
    }
    @Override
    public void draw(Batch batch, float parentAlpha){
        if(clicked) game.setScreen(new GameSelect(game));
        if(mouseOn) sprite.setColor(86, 86, 86, .65f);
        sprite.draw(batch);
        sprite.setColor(1, 1, 1, 1f);
    }
}
