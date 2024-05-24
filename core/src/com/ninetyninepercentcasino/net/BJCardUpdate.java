package com.ninetyninepercentcasino.net;

import com.ninetyninepercentcasino.game.gameparts.Card;

public class BJCardUpdate extends DTO {
    private Card card;
    private boolean visible;

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
