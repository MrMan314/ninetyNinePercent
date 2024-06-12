package com.ninetyninepercentcasino.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Models an interactive Card with a texture and event listeners
 * @author Grant Liang
 */
public class CardActor extends Actor {
	private Card card; //the Card instance that this Actor wraps
	private boolean faceUp; //stores the current state of the card
	private boolean isLocalCard;

	boolean popped; //card is popped when the cursor is touching it
	final static float POP_DISTANCE = 20; //the distance the card will pop up when hovered over

	final static TextureRegion faceDownTex = new TextureRegion(new Texture("GameAssets/Top-Down/Cards/Card_Back-88x124.png"), 0, 0, 88, 124); //texture of the face down card
	final TextureRegion faceUpTex; //will store the texture of the face up card
	private Sprite sprite; //the sprite that will model the visuals of the actor

	/**
	 * initializes a new CardActor
	 * @param card the card that the CardActor is modeling
	 * @param faceUp whether the card is face up or face down, it will be drawn as such
	 * @param isLocalCard if true, the card will be drawn bigger than the other cards. this simulates the cards being in the hand
	 */
	public CardActor(Card card, boolean faceUp, boolean isLocalCard){
		this.card = card;
		faceUpTex = new TextureRegion(findTexture(card)); //call the method to find the appropriate texture for the Card
		if(faceUp) sprite = new Sprite(faceUpTex); //set the sprite to the appropriate texture to match its faceUp property
		else sprite = new Sprite(faceDownTex);
		if(isLocalCard){ //the card is local, so increase its size
			sprite.setSize(sprite.getWidth()*3, sprite.getHeight()*3);
		}
		else setTouchable(Touchable.disabled); //the card is not local, so make it not interactable
		this.isLocalCard = isLocalCard;
		popped = false;
		setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight()); //set the hit bounds of the actor to match the sprite's width and height, but the actor's x and y coordinates.
		addListener(new ClickListener(){ //listens for enter and exit events
			/**
			 * called when the cursor enters the actor, and will make the card pop up a little
			 * @param event the associated InputEvent
			 * @param x cursor x location
			 * @param y cursor y location
			 * @param pointer the status of the cursor
			 * @param fromActor May be null.
			 */
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
				if(pointer == -1) popped = true; //if the pointer isn't down, pop the card
			}
			/**
			 * called when the cursor exits the actor, and will make the card unpop
			 * @param event the associated InputEvent
			 * @param x cursor x location
			 * @param y cursor y location
			 * @param pointer the status of the cursor
			 * @param fromActor May be null.
			 */
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
				if(pointer == -1) popped = false; //if the pointer isn't down, unpop the card
			}
		});
	}

	/**
	 * reveals the side of the card with the number and suit on it if not already revealed
	 */
	public void reveal(){
		if(!faceUp) {
			faceUp = true;
			sprite = new Sprite(faceUpTex);
		}
	}

	/**
	 * flips the card over to the back side
	 */
	public void hide(){
		if(faceUp){
			faceUp = false;
			sprite = new Sprite(faceDownTex);
		}
	}

	/**
	 * draws the actor using the sprite, the visual will be POP_DISTANCE up from the hitbox if the card is popped
	 * @param batch the batch used to draw the actor
	 * @param parentAlpha The parent alpha, to be multiplied with this actor's alpha, allowing the parent's alpha to affect all
	 *		   children.
	 */
	public void draw(Batch batch, float parentAlpha){
		batch.setColor(Color.WHITE); //ensures this is drawn without any tint
		if(popped) batch.draw(sprite, getX(), getY()+POP_DISTANCE, sprite.getWidth(), sprite.getHeight()); //translate the visual up by POP_DISTANCE if the card is popped
		else batch.draw(sprite, getX(), getY(), sprite.getWidth(), sprite.getHeight());
	}

	/**
	 * enlarges the card, called when it is added to a currently active hand
	 */
	public void makeActive(){
		if(!isLocalCard) {
			sprite.setSize(sprite.getWidth()*3, sprite.getHeight()*3);
			isLocalCard = true;
			setTouchable(Touchable.enabled);
		}
	}
	/**
	 * @return the Card that this CardActor wraps
	 */
	public Card getCard(){
		return card;
	}
	/**
	 * finds and returns the TextureRegion that represents the correct card
	 */
	private TextureRegion findTexture(Card card){
		int cardNum = card.getNum();
		String suit = card.getSuitName();
		//each card is 88 wide and 124 tall
		int textureRegionX = 0;
		int textureRegionY = 0;
		for(int i = 1; i < cardNum; i++){
			textureRegionX += 88; //increment by 88 in the row to get to the next number's texture
			if(textureRegionX > 88*4){ //the end of the row has been reached, so travel to the next row
				textureRegionX = 0;
				textureRegionY += 124;
			}
		}
		return new TextureRegion(new Texture("GameAssets/Top-Down/Cards/" + suit + ".png"), textureRegionX, textureRegionY, 88, 124);
	}
}
