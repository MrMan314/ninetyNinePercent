package com.ninetyninepercentcasino.bj.bjbuttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ninetyninepercentcasino.bj.BJGameStage;
import com.ninetyninepercentcasino.gameparts.CasinoButton;


import java.io.IOException;

public class DDButton extends CasinoButton {
	public DDButton(){
		super();
		buttonSprite = new Sprite(new TextureRegion(new Texture("GameAssets/BJButtons.png"), 64, 0, 64, 72));
		buttonSprite.setSize(192, 192 * ((float) 72/64));
		setBounds(getX(), getY(), buttonSprite.getWidth(), buttonSprite.getHeight());
		buttonSprite.setPosition(getX(), getY());
		addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if(isAvailable) {
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
	public void draw(Batch batch, float parentAlpha){
		buttonSprite.setPosition(getX(), getY());
		buttonSprite.draw(batch);
	}
	/**
	 * called when the button is clicked
	 */
	public void signalDD() throws IOException {
		((BJGameStage)getStage()).doubleDown();
	}

}
