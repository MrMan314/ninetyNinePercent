package com.ninetyninepercentcasino.game;

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
	private boolean isLocalHand; //any new CardActors will be larger/smaller if isLocalHand is true/false respectively.
	/**
	 * Constructor that initializes a new empty player hand
	 * pre: none
	 * post: initializes a new empty player hand
	 */
	public CardGroup(boolean faceUp, boolean isLocalHand){
		this.faceUp = faceUp;
		this.isLocalHand = isLocalHand;
		if(isLocalHand) setTouchable(Touchable.enabled);
		else setTouchable(Touchable.disabled);
		hand = new Hand();
	}

	/**
	 * Constructor that initializes a new player hand with a given Hand
	 * @param hand the hand to create the CardGroup with
	 * @param faceUp the orientation that new cards will automatically have
	 * @param isLocalHand
	 */
	public CardGroup(Hand hand, boolean faceUp, boolean isLocalHand){
		this(faceUp, isLocalHand);
		for(Card card : hand.getCards()){
			add(new CardActor(card, faceUp, isLocalHand));
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
		CardActor newCard = new CardActor(card, faceUp, isLocalHand);
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
	public void removeCard(Card card){
		int index = 0;
		for(int i = 0; i < hand.getCards().size(); i++){
			Card cardInHand = hand.getCard(i);
			if(card.getNum() == cardInHand.getNum() && card.getSuit() == cardInHand.getSuit()){
				index = i;
				hand.removeCard(i);
			}
		}
		removeActorAt(index, true);
	}
	/**
	 * Hides the hand by hiding all CardActors
	 */
	public void hide(){
		for(Actor cardActor: getChildren()){
			((CardActor)cardActor).hide(); //hide each CardActor this manages
		}
	}
	/**
	 * Reveals the hand by revealing all CardActors
	 */
	public void reveal(){
		for(Actor cardActor: getChildren()){
			((CardActor)cardActor).reveal(); //reveal each CardActor this manages
		}
	}

	/**
	 * @return the Hand this CardGroup wraps
	 */
	public Hand getHand(){
		return hand;
	}
}
