package com.ninetyninepercentcasino.game.gameparts;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;

/**
 * Models a player hand that manages Cards
 * @author Grant Liang
 */
public class Hand extends HorizontalGroup {
    /**
     * Constructor that initializes a new empty player hand
     * pre: none
     * post: initializes a new empty player hand
     */
    public Hand(){
        this.debug();
        this.padBottom(200);
        this.padLeft(200);
        this.padRight(200);
        this.space(88);
    }
    /**
     * Method that adds a Card to the hand
     * pre: none
     * post: adds the Card to the hand
     */
    public void addCard(CardActor card){
        this.addActor(card);
    }
    /**
     * Method that removes a Card from the hand
     * pre: none
     * post: removes the Card from the hand
     */
    public void removeCard(CardActor card){
        this.removeActor(card);
    }


}
