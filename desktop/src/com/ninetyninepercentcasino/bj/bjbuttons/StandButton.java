package com.ninetyninepercentcasino.bj.bjbuttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ninetyninepercentcasino.bj.BJStage;
import com.ninetyninepercentcasino.gameparts.CasinoButton;

/**
 * Models a calling button in a poker game
 * @author Grant Liang
 */
public class StandButton extends CasinoButton {
	public StandButton(){
		super();
		buttonSprite = new Sprite(new TextureRegion(new Texture("GameAssets/BJButtons.png"), 128, 0, BUTTON_ASSET_WIDTH, BUTTON_ASSET_HEIGHT));
		buttonSprite.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		setBounds(getX(), getY(), buttonSprite.getWidth(), buttonSprite.getHeight());
		buttonSprite.setPosition(getX(), getY());
		addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if(isAvailable) {
					signalStand();
				}
				return true;
			}
		});
	}
	public void draw(Batch batch, float parentAlpha){
		super.draw(batch, parentAlpha);
	}
	/**
	 * called when the button is clicked
	 */
	public void signalStand() {
		((BJStage)getStage()).stand();
	}
}
