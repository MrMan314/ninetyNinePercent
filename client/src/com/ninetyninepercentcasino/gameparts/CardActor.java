package com.ninetyninepercentcasino.gameparts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ninetyninepercentcasino.game.gameparts.Card;

/**
 * Models an interactive Card with a texture and event listeners
 * @author Grant Liang
 */
public class CardActor extends Actor {
    private Card card;
    private boolean faceUp; //stores the current state of the card

    boolean popped; //card is popped when the cursor is touching it
    final static float POP_DISTANCE = 20; //the distance the card will pop up when hovered over

    final static TextureRegion faceDownTex = new TextureRegion(new Texture("GameAssets/Top-Down/Cards/Card_Back-88x124.png"), 0, 0, 88, 124);
    final TextureRegion faceUpTex;
    private Sprite sprite;

    public CardActor(Card card, boolean faceUp, boolean isUICard){
        this.card = card;
        faceUpTex = new TextureRegion(findTexture(card));
        if(faceUp) sprite = new Sprite(faceUpTex);
        else sprite = new Sprite(faceDownTex);
        if(isUICard){
            sprite.setSize(sprite.getWidth()*3, sprite.getHeight()*3);
        }
        else setTouchable(Touchable.disabled);
        setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight());
        popped = false;
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
    public void hide(){
        if(faceUp){
            faceUp = false;
            sprite = new Sprite(faceDownTex);
        }
    }
    public void draw(Batch batch, float parentAlpha){
        if(popped) batch.draw(sprite, getX(), getY()+POP_DISTANCE, sprite.getWidth(), sprite.getHeight());
        else batch.draw(sprite, getX(), getY(), sprite.getWidth(), sprite.getHeight());
    }
    public Card getCard(){
        return card;
    }
    /**
     * Finds and returns the TextureRegion that represents the correct card
     */
    private TextureRegion findTexture(Card card){
        int cardNum = card.getNum();
        String suit = card.getSuitName();
        //each card is 88 wide and 124 tall
        int textureRegionX = 0;
        int textureRegionY = 0;
        for(int i = 1; i < cardNum; i++){
            textureRegionX += 88; //increment by 88 in the row to get to the next number's texture
            if(textureRegionX > 88*4){ //the end of the row has been reached, so travel to the next row
                textureRegionX = 0;
                textureRegionY += 124;
            }
        }
        return new TextureRegion(new Texture("GameAssets/Top-Down/Cards/" + suit + ".png"), textureRegionX, textureRegionY, 88, 124);
    }
}
