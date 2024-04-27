package com.ninetyninepercentcasino.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Grant Liang
 * Mr. Gonzalez
 * ICS4Ud
 * This class models a playing card
 */

public class Card extends Actor {
    private Sprite sprite;
    private final int suit; //0 spades, 1 diamonds, 2 clubs, 3 hearts
    private final int cardNum;
    private final String[] suitNames = {"SPADES", "DIAMONDS", "CLUBS", "HEARTS"};
    private final String[] numberNames = {"ZERO_ERROR", "ACE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN", "JACK", "QUEEN", "KING"};
    /**
     * Constructor that initializes a new card with a given suit and number
     * pre: suit is number from 0-3
     * post: initializes a new card object assigned to the given numberValue and suit
     */
    public Card(int cardNum, int suit){
        this.suit = suit;
        this.cardNum = cardNum;
        sprite = new Sprite(findTexture());
    }
    /**
     * Accessor method that returns the number of a card
     * pre: none
     * post: returns the number of the card
     */
    public int getNum(){
        return cardNum;
    }
    /**
     * Accessor method that returns the suit of a card
     * pre: none
     * post: returns the suit of the card as a string
     */
    public int getSuit(){
        return suit;
    }
    public String getSuitName(){
        return suitNames[suit];
    }
    public void draw(Batch batch, float parentAlpha){
        sprite.draw(batch);
    }
    /**
     * Finds and returns the TextureRegion that represents the correct card
     */
    private TextureRegion findTexture(){
        //each card is 88 wide and 124 tall
        int textureRegionX = 0;
        int textureRegionY = 0;
        for(int i = 1; i < cardNum; i++){
            textureRegionX += 88;
            if(textureRegionX > 88*4){
                textureRegionX = 0;
                textureRegionY += 124;
            }
        }
        return new TextureRegion(new Texture("PokerAssets/Top-Down/Cards/" + suitNames[suit] + ".png"), textureRegionX, textureRegionY, 88, 124);
    }
}