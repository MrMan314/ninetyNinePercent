package com.ninetyninepercentcasino.game
public class DeckActor extends Actor:
	private Deck deck
	private static Sprite sprite = new Sprite(new TextureRegion(new Texture("GameAssets/Top-Down/Cards/Card_DeckA-88x140.png"), 88, 0, 88, 140))
	public DeckActor():
		deck = new Deck()
		setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight())
	public DeckActor(Deck deck):
		this.deck = deck
		setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight())
	public void draw(Batch batch, float parentAlpha):
		setColor(Color.WHITE)
		batch.draw(sprite, getX(), getY(), sprite.getWidth(), sprite.getHeight())
