package com.ninetyninepercentcasino.net;

import com.ninetyninepercentcasino.game.bj.BJPlayer;
import com.ninetyninepercentcasino.game.gameparts.Card;

public class BJCardDrawn {
    private Card card;
    private BJPlayer player;

    public BJCardDrawn(Card card, BJPlayer player){
        this.card = card;
        this.player = player;
    }
    public BJPlayer getPlayer(){
        return player;
    }
    public Card getCard(){
        return card;
    }
}
