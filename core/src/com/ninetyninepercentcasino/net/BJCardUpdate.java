package com.ninetyninepercentcasino.net;

import com.ninetyninepercentcasino.game.bj.BJPlayer;
import com.ninetyninepercentcasino.game.gameparts.Card;

public class BJCardUpdate {
    private Card card;
    private BJPlayer player;
    private boolean visible;

    public BJCardUpdate(Card card, BJPlayer player, boolean visible){
        this.card = card;
        this.player = player;
        this.visible = visible;
    }
    public BJPlayer getPlayer(){
        return player;
    }
    public Card getCard(){
        if(visible) return card;
        else return new Card(0, 0);
    }
    public boolean isVisible(){
        return visible;
    }
}
