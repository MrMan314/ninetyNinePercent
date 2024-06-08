package com.ninetyninepercentcasino.net;

import com.ninetyninepercentcasino.game.gameparts.Card;

/**
 * DTO for whenever a new card is introduced to the BJ game
 * @author Grant Liang
 */
public class BJCardUpdate extends DTO {
	private Card card;
	private boolean visible;
	private boolean isPlayerCard; //acts as an identifier for whether the player or dealer drew the card

	public BJCardUpdate(Card card, boolean visible, boolean isPlayerCard){
		this.card = card;
		this.visible = visible;
		this.isPlayerCard = isPlayerCard;
	}
	public Card getCard(){
		return card;
	}
	public boolean isVisible(){
		return visible;
	}
	public boolean isPlayerCard(){
		return isPlayerCard;
	}
}
