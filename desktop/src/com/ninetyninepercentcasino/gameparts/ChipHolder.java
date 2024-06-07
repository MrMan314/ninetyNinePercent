package com.ninetyninepercentcasino.gameparts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

/**
 * models an immovable chip holder that will hold chips that are ready to be bet
 * @author Grant Liang
 */
public class ChipHolder extends ChipActor {
	private static final Sprite sprite = new Sprite(new TextureRegion(new Texture("GameAssets/Isometric/Chips/Template/Template_Outline_Flat_Small-87x54.png"), 0, 0, 88, 56));

	public ChipHolder(){
		chipAbove = null;
		sprite.setSize(CHIP_WIDTH, CHIP_HEIGHT);
		setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight());
	}
	public void pop(){
	}
	public void unpop(){
	}
	public void draw(Batch batch, float parentAlpha){
		batch.draw(sprite, getX(), getY(), sprite.getWidth(), sprite.getHeight());
	}
	public int calculate(){
		if(chipAbove != null) return chipAbove.calculate();
		else return 0;
	}
	public void floatAway(){
		if(chipAbove != null) chipAbove.floatAway();
	}
}
