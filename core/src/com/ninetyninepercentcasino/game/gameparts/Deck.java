package com.ninetyninepercentcasino.game.gameparts;
import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

public class Deck extends Actor {
    private ArrayList<Card> deck;
    TextureRegion deckTexture;

    public Deck(){
        deckTexture = new TextureRegion(new Texture("PokerAssets/Top-Down/Cards/Card_DeckA-88x140.png"), 0, 0, 88, 140);
        deck = new ArrayList<Card>();
        for(int i = 0; i < 4; i++) {
            for (int j = 12; j >= 0; j--) {
                deck.add(new Card(j, i));
            }
        }
    }
    public void shuffle(){
        Collections.shuffle(deck);
    }
    public Card drawCard(){
        return deck.remove(0);
    }
    public void draw(Batch batch, float parentAlpha){
        batch.draw(deckTexture, getX(), getY(), 88, 88 * ((float)140/(float)88));
    }
}
