package com.ninetyninepercentcasino.game.blackjack;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.ninetyninepercentcasino.game.gameparts.Deck;

/**
 * Runs logic for a blackjack game
 * @author Grant Liang
 */
public class BlackjackGame {
    private Deck deck;
    private double pot;
    BlackjackPlayer player;
    BlackjackPlayer dealer;

    public BlackjackGame(BlackjackPlayer player){
        this.player = player;
        dealer = new BlackjackPlayer(false);
    }
    public void playRound(){
        deck = new Deck();
        deck.shuffle();
        player.drawCard(deck);
        float delay = 0.7f;
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                dealer.drawCard(deck);
                Timer.schedule(new Timer.Task(){
                    @Override
                    public void run() {
                        player.drawCard(deck);
                        Timer.schedule(new Timer.Task(){
                            @Override
                            public void run() {
                                dealer.drawCard(deck);
                            }
                        }, delay);
                    }
                }, delay);
            }
        }, delay);

    }
    public BlackjackPlayer getDealer(){
        return dealer;
    }
}
