package com.ninetyninepercentcasino.game.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Models a calling button in a poker game
 * @author Grant Liang
 */
public class CallButton extends CasinoButton {
	/**
	 * initializes a new call button
	 */
	public CallButton(){
		super();
		buttonSprite = new Sprite(new TextureRegion(new Texture("GameAssets/PokerButtons.png"), 192, 0, 64, 72));
		buttonSprite.setSize(192, 192 * ((float) 72/64));
		setBounds(getX(), getY(), buttonSprite.getWidth(), buttonSprite.getHeight()); //set the bounds of the actor so it can receive input events
		buttonSprite.setPosition(getX(), getY());
		addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				signalCall(); //perform the call action once the button has been clicked
				return true;
			}
		});
	}

	/**
	 * updates the position of the sprite and draws it
	 * @param batch the batch that will draw the actor
	 * @param parentAlpha The parent alpha, to be multiplied with this actor's alpha, allowing the parent's alpha to affect all
	 *		   children.
	 */
	public void draw(Batch batch, float parentAlpha){
		buttonSprite.setPosition(getX(), getY());
		buttonSprite.draw(batch);
	}
	/**
	 * called when the button is clicked
	 */
	public void signalCall(){
	}
}
