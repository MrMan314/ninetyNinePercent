package com.ninetyninepercentcasino.game.gameparts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.ninetyninepercentcasino.game.SFXManager;

import java.util.ArrayList;

/**
 * Models a player hand that manages Cards
 * @author Grant Liang
 */
public class Hand extends Table {
    private boolean faceUp;
    /**
     * Constructor that initializes a new empty player hand
     * pre: none
     * post: initializes a new empty player hand
     */
    public Hand(boolean faceUp, boolean touchable){
        this.faceUp = faceUp;
        if(touchable) setTouchable(Touchable.enabled);
        else setTouchable(Touchable.disabled);
    }
    /**
     * Method that adds a Card to the hand
     * pre: none
     * post: adds the Card to the hand
     */
    public void addCard(CardActor card){
        add(card);
    }
    /**
     * Method that removes a Card from the hand
     * pre: none
     * post: removes the Card from the hand
     */
    public void removeCard(CardActor card){
        removeActor(card);
    }
    public void drawCard(Deck deck){
        addCard(new CardActor(deck.drawCard(), true, faceUp));
    }


}
