package com.ninetyninepercentcasino.game.gameparts;
import java.util.ArrayList;
import java.util.Collections;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class Deck {
    private ArrayList<Card> deck;
    Texture deckTexture;
    public Deck(){
        deckTexture = new Texture("PokerAssets/Top-Down/Cards/Card_DeckA-88x140.png");
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
    public void drawDeck(SpriteBatch batch){
        batch.draw(deckTexture, -1, -1, 0, 0, 1, 1 * ((float)140/(float)88), 0, 0, 0, 0, 0, 88, 140, false, false);
    }
}
