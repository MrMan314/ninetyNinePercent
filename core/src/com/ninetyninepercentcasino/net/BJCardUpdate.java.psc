package com.ninetyninepercentcasino.net
public class BJCardUpdate extends DTO:
	private Card card
	private boolean visible
	private boolean isPlayerCard
	public BJCardUpdate(Card card, boolean visible, boolean isPlayerCard):
		this.card = card
		this.visible = visible
		this.isPlayerCard = isPlayerCard
	public Card getCard():
		return card
	public boolean isVisible():
		return visible
	public boolean isPlayerCard():
		return isPlayerCard
