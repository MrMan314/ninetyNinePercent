package com.ninetyninepercentcasino.gameparts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ninetyninepercentcasino.game.gameparts.Deck;

public class DeckActor extends Actor {
    private Deck deck;
    static Sprite sprite = new Sprite(new TextureRegion(new Texture("GameAssets/Top-Down/Cards/Card_DeckA-88x140.png"), 88, 0, 88, 140));
    

    public DeckActor(){
        deck = new Deck();
        setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight());
    }
    public DeckActor(Deck deck){
        this.deck = deck;
        setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight());
    }
    public void draw(Batch batch, float parentAlpha){
        batch.draw(sprite, getX(), getY(), sprite.getWidth(), sprite.getHeight());
    }
}
