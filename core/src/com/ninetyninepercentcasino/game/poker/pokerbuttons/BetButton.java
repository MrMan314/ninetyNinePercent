package com.ninetyninepercentcasino.game.poker.pokerbuttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Models a betting button in a poker game
 * @author Grant Liang
 */
public class BetButton extends PokerButton {

    public BetButton(){
        super();
        buttonSprite = new Sprite(new TextureRegion(new Texture("PokerAssets/PokerButtons.png"), 256, 0, 64, 72));
        setBounds(getX(), getY(), buttonSprite.getWidth(), buttonSprite.getHeight());
        addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                signalBet();
            }
        });
    }
    public void draw(Batch batch, float parentAlpha){
        batch.draw(buttonSprite, getX(), getY(), buttonSprite.getWidth(), buttonSprite.getHeight());
    }
    public void signalBet(){
    }
}
