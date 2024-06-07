package com.ninetyninepercentcasino.gameparts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Models an interactive game button for normal player actions in a casino game
 * @author Grant Liang
 */
public abstract class CasinoButton extends Actor {
	protected static final int BUTTON_ASSET_WIDTH = 64;
	protected static final int BUTTON_ASSET_HEIGHT = 72;
	protected static final float BUTTON_WIDTH = 135;
	protected static final float BUTTON_HEIGHT = BUTTON_WIDTH * ((float) BUTTON_ASSET_HEIGHT /BUTTON_ASSET_WIDTH);

	protected Sprite buttonSprite; //stores the sprite that will model the texture of the button
	protected static final Sprite buttonOutlineSprite = new Sprite(new TextureRegion(new Texture(Gdx.files.internal("GameAssets/ButtonOutline.png"))));
	protected boolean isAvailable; //stores whether the button is available or not

	/**
	 * initializes a button
	 */
	public CasinoButton(){
		isAvailable = false;
		setTouchable(Touchable.enabled); //let the button be touchable
		buttonOutlineSprite.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		addListener(new ClickListener(){ //listens for cursor entering and exiting actor events
			/**
			 * //TODO
			 * @param event
			 * @param x
			 * @param y
			 * @param pointer
			 * @param fromActor May be null.
			 */
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
				if(isAvailable) buttonSprite.setColor(65, 65, 65, 0.8f);
			}
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
				if(isAvailable) buttonSprite.setColor(1, 1,1 ,1f);
			}
		});
	}

	/**
	 * draws the actor using the sprite
	 * @param batch the batch that will draw the actor
	 * @param parentAlpha The parent alpha, to be multiplied with this actor's alpha, allowing the parent's alpha to affect all
	 *		   children.
	 */
	public void draw(Batch batch, float parentAlpha){
		buttonSprite.setPosition(getX(), getY());
		buttonOutlineSprite.setPosition(getX(), getY());
		if(!isAvailable) buttonOutlineSprite.draw(batch);
		else {
			buttonSprite.draw(batch);
		}
	}

	/**
	 * disables the button, and the entering/exiting animations
	 */
	public void disable(){
		isAvailable = false;
	}

	/**
	 * enables the button and the entering/exiting animations
	 */
	public void enable(){
		isAvailable = true;
	}
}
