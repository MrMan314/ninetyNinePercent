package com.ninetyninepercentcasino.game.gameparts;
import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ninetyninepercentcasino.game.SFXManager;

/**
 * Class to simulate a deck of cards
 * @author Grant Liang
 */
public class Deck extends Actor {
    private ArrayList<Card> deck;
    static Sprite sprite = new Sprite(new TextureRegion(new Texture("PokerAssets/Top-Down/Cards/Card_DeckA-88x140.png"), 88, 0, 88, 140));
    SFXManager sfxManager;

    public Deck(){
        deck = new ArrayList<>();
        setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight());

        for(int i = 0; i < 4; i++) {
            for (int j = 13; j >= 1; j--) {
                deck.add(new Card(j, i));
            }
        }
        sfxManager = new SFXManager();
    }
    public void shuffle(){
        Collections.shuffle(deck);
    }
    public Card drawCard(){
        sfxManager.playSlideSound();
        return deck.remove(0);
    }
    public void draw(Batch batch, float parentAlpha){
        batch.draw(sprite, getX(), getY(), sprite.getWidth(), sprite.getHeight());
    }
}
