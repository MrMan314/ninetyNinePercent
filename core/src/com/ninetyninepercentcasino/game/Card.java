package com.ninetyninepercentcasino.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Grant Liang
 * Mr. Gonzalez
 * ICS4Ud
 * This class models a playing card
 */

public class Card extends Actor {
    private Sprite sprite;
    private final Suit suit;
    private final CardValue cardValue;
    /**
     * Constructor that initializes a new card with a given suit and number
     * pre: suit is "diamonds", "clubs", "hearts", or "spades"
     * post: initializes a new card object assigned to the given numberValue and suit
     */
    public Card(CardValue cardValue, Suit suit){
        this.suit = suit;
        this.cardValue = cardValue;
        sprite = new Sprite();
    }
    /**
     * Accessor method that returns the number value of a card
     * pre: none
     * post: returns the number value of the card
     */
    public CardValue getValue(){
        return cardValue;
    }
    /**
     * Accessor method that returns the suit of a card
     * pre: none
     * post: returns the suit of the card as a string
     */
    public Suit getSuit(){
        return suit;
    }
    public void draw(Batch batch, float parentAlpha){
        sprite.draw(batch);
    }
}