package com.ninetyninepercentcasino.game.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


/**
 * Models a raising button in a poker game
 * @author Grant Liang
 */
public class RaiseButton extends CasinoButton {
	public RaiseButton() {
		super();
		buttonSprite = new Sprite(new TextureRegion(new Texture("GameAssets/PokerButtons.png"), 64, 0, 64, 72));
		buttonSprite.setSize(192, 192 * ((float) 72/64));
		setBounds(getX(), getY(), buttonSprite.getWidth(), buttonSprite.getHeight());
		buttonSprite.setPosition(getX(), getY());
		addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				signalRaise();
				return true;
			}
		});
	}
	public void draw(Batch batch, float parentAlpha) {
		buttonSprite.setPosition(getX(), getY());
		buttonSprite.draw(batch);
	}
	/**
	 * called when the button is clicked
	 */
	public void signalRaise() {
	}
}
