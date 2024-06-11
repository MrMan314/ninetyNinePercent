package com.ninetyninepercentcasino.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * An Actor for a deck of cards, used to draw a deck onto the screen
 * @author Grant Liang
 */
public class DeckActor extends Actor {
	private Deck deck; //the deck that this Actor wraps
	private static Sprite sprite = new Sprite(new TextureRegion(new Texture("GameAssets/Top-Down/Cards/Card_DeckA-88x140.png"), 88, 0, 88, 140)); //the sprite that stores the visual of the deck

	/**
	 * initializes a new unshuffled deck
	 */
	public DeckActor() {
		deck = new Deck();
		setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight()); //set the hitbox of this Actor to match the sprite
	}

	/**
	 * initializes a new DeckActor from a preexisting Deck
	 * @param deck the deck this DeckActor will wrap
	 */
	public DeckActor(Deck deck) {
		this.deck = deck;
		setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight()); //set the hitbox of this Actor to match the sprite
	}
	/**
	 * draws the actor using the sprite
	 * @param batch the batch that will draw the actor
	 * @param parentAlpha The parent alpha, to be multiplied with this actor's alpha, allowing the parent's alpha to affect all
	 *		   children.
	 */
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(sprite, getX(), getY(), sprite.getWidth(), sprite.getHeight());
	}
}
