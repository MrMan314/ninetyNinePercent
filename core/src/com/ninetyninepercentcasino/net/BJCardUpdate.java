package com.ninetyninepercentcasino.net;

import com.ninetyninepercentcasino.game.gameparts.Card;

/**
 * DTO for whenever a new card is introduced to the BJ game
 * @author Grant Liang
 */
public class BJCardUpdate extends DTO {
    private Card card;
    private boolean visible; //this variable currently also acts as an identifier for whether the player or dealer drew the card

    public BJCardUpdate(Card card, boolean visible){
        this.card = card;
        this.visible = visible;
    }
    public Card getCard(){
        if(visible) return card;
        else return new Card(0, 0);
    }
    public boolean isVisible(){
        return visible;
    }
}
