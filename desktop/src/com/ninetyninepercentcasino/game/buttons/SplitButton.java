package com.ninetyninepercentcasino.game.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ninetyninepercentcasino.bj.BJStage;

/**
 * Split button for a blackjack game
 * Updates the stage when it is clicked
 * @author Grant Liang
 */
public class SplitButton extends CasinoButton {
	/**
	 * initializes a new split button
	 */
	public SplitButton() {
		super();
		buttonSprite = new Sprite(new TextureRegion(new Texture("GameAssets/BJButtons.png"), 0, 0, BUTTON_ASSET_WIDTH, BUTTON_ASSET_HEIGHT));
		buttonSprite.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		setBounds(getX(), getY(), buttonSprite.getWidth(), buttonSprite.getHeight());
		buttonSprite.setPosition(getX(), getY());
		addListener(new ClickListener() {
			/**
			 * called when the button is clicked, will signal a split
			 */
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (isAvailable) signalSplit();
				return true;
			}
		});
	}
	/**
	 * called when the button is clicked
	 */
	public void signalSplit() {
		((BJStage)getStage()).split();
	}
}
