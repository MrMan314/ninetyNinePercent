package com.ninetyninepercentcasino.game.gameparts;
import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ninetyninepercentcasino.game.Player;
import com.ninetyninepercentcasino.game.SFXManager;

/**
 * Class to simulate a deck of cards
 * @author Grant Liang
 */
public class Deck {
    private ArrayList<Card> deck;
    SFXManager sfxManager;

    public Deck(){
        deck = new ArrayList<>();

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

    public void deal(Hand hand){
        hand.addCard(deck.remove(0));
    }
}
