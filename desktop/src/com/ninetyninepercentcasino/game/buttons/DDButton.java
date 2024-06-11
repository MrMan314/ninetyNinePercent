package com.ninetyninepercentcasino.game.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ninetyninepercentcasino.bj.BJStage;


import java.io.IOException;

/**
 * Double down button for a blackjack game
 * Updates the stage when it is clicked
 * @author Grant Liang
 */
public class DDButton extends CasinoButton {
	/**
	 * initializes a new double down button
	 */
	public DDButton() {
		super();
		buttonSprite = new Sprite(new TextureRegion(new Texture("GameAssets/BJButtons.png"), 64, 0, BUTTON_ASSET_WIDTH, BUTTON_ASSET_HEIGHT));
		buttonSprite.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		setBounds(getX(), getY(), buttonSprite.getWidth(), buttonSprite.getHeight());
		buttonSprite.setPosition(getX(), getY());
		addListener(new ClickListener() {
			/**
			 * called when the button is clicked, will signal a double down
			 */
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (isAvailable) {
					try {
						signalDD();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
				return true;
			}
		});
	}
	/**
	 * called when the button is clicked
	 */
	public void signalDD() throws IOException {
		((BJStage)getStage()).doubleDown();
	}

}
