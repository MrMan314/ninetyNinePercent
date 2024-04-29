package com.ninetyninepercentcasino.game.gameparts;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Models an interactive CardActor with a texture and event listeners
 * @author Grant Liang
 */
public class CardActor extends ImageButton {
    final float POPDISTANCE = 20;

    public CardActor(Card card){
        super(new TextureRegionDrawable(card.findTexture()));
        setTouchable(Touchable.enabled);
        this.setBounds(getX(), getY(), getWidth(), getHeight());
        addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                popOut();
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                popIn();
            }
        });
    }
    public void popOut(){
        moveBy(0, POPDISTANCE);
    }
    public void popIn(){
        moveBy(0,-POPDISTANCE);
    }
}
