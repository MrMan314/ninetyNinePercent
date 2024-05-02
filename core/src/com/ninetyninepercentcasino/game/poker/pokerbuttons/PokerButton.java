package com.ninetyninepercentcasino.game.poker.pokerbuttons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Models an interactive poker button for normal player actions in a poker game
 * @author Grant Liang
 */
public class PokerButton extends Actor {
    protected Sprite buttonSprite;

    public PokerButton(){
        setTouchable(Touchable.enabled);
        addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                buttonSprite.setColor(65, 65, 65, 0.8f);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                buttonSprite.setColor(1, 1,1 ,1f);
            }
        });
    }
    public void draw(Batch batch, float parentAlpha){
        buttonSprite.draw(batch);
    }
}
