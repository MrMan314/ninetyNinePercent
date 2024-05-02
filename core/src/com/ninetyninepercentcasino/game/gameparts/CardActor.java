package com.ninetyninepercentcasino.game.gameparts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Models an interactive CardActor with a texture and event listeners
 * @author Grant Liang
 */
public class CardActor extends Actor {
    private boolean faceUp;
    private Card card;

    boolean popped = false;
    final static float POPDISTANCE = 20;

    final static TextureRegion faceDownTex = new TextureRegion(new Texture("PokerAssets/Top-Down/Cards/Card_Back-88x124.png"), 0, 0, 88, 124);
    final TextureRegion faceUpTex;
    private Sprite sprite;

    public CardActor(Card card, boolean isUserHand, boolean faceUp){
        this.card = card;
        faceUpTex = new TextureRegion(card.findTexture());
        if(faceUp) sprite = new Sprite(faceUpTex);
        else sprite = new Sprite(faceDownTex);
        if(isUserHand) {
            setTouchable(Touchable.enabled);
            sprite.setSize(sprite.getWidth()*3, sprite.getHeight()*3);
        }
        else setTouchable(Touchable.disabled);
        setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight());

        addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer == -1) popped = true;
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer == -1) popped = false;
            }
        });
    }
    public void reveal(){
        if(!faceUp) {
            faceUp = true;
            sprite = new Sprite(faceUpTex);
        }
    }
    public void draw(Batch batch, float parentAlpha){
        if(popped) batch.draw(sprite, getX(), getY()+POPDISTANCE, sprite.getWidth(), sprite.getHeight());
        else batch.draw(sprite, getX(), getY(), sprite.getWidth(), sprite.getHeight());
    }
    public Card getCard(){
        return card;
    }
}
