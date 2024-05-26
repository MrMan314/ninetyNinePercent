package com.ninetyninepercentcasino.gameparts;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.ninetyninepercentcasino.game.gameparts.Card;
import com.ninetyninepercentcasino.game.gameparts.Hand;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Models a hand's visuals that manages CardActors and a Hand
 * @author Grant Liang
 */
public class CardGroup extends Table {
    private Hand hand;
    private final boolean faceUp; //any new CardActors added will be faceUp or faceDown according to this variable
    private boolean isUIHand;
    /**
     * Constructor that initializes a new empty player hand
     * pre: none
     * post: initializes a new empty player hand
     */
    public CardGroup(boolean faceUp, boolean isUIHand){
        this.faceUp = faceUp;
        this.isUIHand = isUIHand;
        if(isUIHand) setTouchable(Touchable.enabled);
        else setTouchable(Touchable.disabled);
        hand = new Hand();
    }
    public CardGroup(Hand hand, boolean faceUp, boolean isUIHand){
        this(faceUp, isUIHand);
        for(Card card : hand.getCards()){
            add(new CardActor(card, faceUp, isUIHand));
        }
        this.hand = hand;
    }
    /**
     * Method that adds a CardActor to the hand
     * pre: none
     * post: adds the CardActor to the hand
     */
    public void addCard(CardActor card){
        hand.addCard(card.getCard());
        add(card);
    }
    /**
     * Method that adds a Card to the hand
     * pre: none
     * post: adds the CardActor to the hand
     */
    public void addCard(Card card){
        hand.addCard(card);
        CardActor newCard = new CardActor(card, faceUp, isUIHand);
        add(newCard);
    }
    /**
     * Method that removes a CardActor from the hand
     * pre: none
     * post: removes the CardActor from the hand
     */
    public void removeCard(CardActor card){
        hand.removeCard(card.getCard());
        removeActor(card);
    }
    /**
     * Hides the hand by hiding all CardActors
     */
    public void hide(){
        for(Actor cardActor: getChildren()){
            ((CardActor)cardActor).hide();
        }
    }
    /**
     * Reveals the hand by revealing all CardActors
     */
    public void reveal(){
        for(Actor cardActor: getChildren()){
            ((CardActor)cardActor).reveal();
        }
    }
    public Hand getHand(){
        return hand;
    }
}
