package com.ninetyninepercentcasino.game.bj;

import com.ninetyninepercentcasino.game.Card;
import com.ninetyninepercentcasino.game.Deck;
import com.ninetyninepercentcasino.game.Hand;

public class BJDealer {
	private Hand hand;
	private Deck deck;

	/**
	 * initializes a new blackjack dealer
	 * @param deck the deck this dealer will draw from
	 */
	public BJDealer(Deck deck){
		this.deck = deck;
		hand = new Hand();
	}

	/**
	 * draws a card from the deck connected to this dealer
	 * @return the card drawn
	 */
	public Card drawCard(){
		return hand.drawCard(deck);
	}
	/**
	 * calculates the highest possible bj score of a hand that doesn't bust
	 * @return score of the hand
	 */
	public int getScore(){
		int score = 0;
		int numAces = 0;
		for(Card card : hand.getCards()){
			int cardValue = card.getNum();
			if(cardValue == 1) numAces++;
			else if(cardValue > 10) cardValue = 10;
			score += cardValue;
		}
		while(numAces > 0 && score + 10 <= 21){
			numAces--;
			score += 10;
		}
		return score;
	}

	/**
	 * @return whether the dealer has a visible ace or not
	 */
	public boolean hasVisibleAce(){
		return hand.getCard(0).getNum() == 1;
	}

	/**
	 * @return the number of cards in the dealer's hand
	 */
	public int getNumCards(){
		return hand.getCards().size();
	}
	//TODO remove
	public void addCard(Card card){
		hand.addCard(card);
	}
}
