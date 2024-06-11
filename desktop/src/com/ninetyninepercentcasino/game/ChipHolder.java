package com.ninetyninepercentcasino.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * models an immovable chip holder that will hold chips that are ready to be bet
 * @author Grant Liang
 */
public class ChipHolder extends ChipActor {
	private static final Sprite sprite = new Sprite(new TextureRegion(new Texture("GameAssets/Isometric/Chips/Template/Template_Outline_Flat_Small-87x54.png"), 0, 0, 88, 56));

	/**
	 * initializes a new ChipHolder
	 */
	public ChipHolder() {
		chipAbove = null;
		sprite.setSize(CHIP_WIDTH, CHIP_HEIGHT);
		setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight());
	}

	/**
	 * ChipHolders cannot be popped, so override with an empty method
	 */
	@Override
	public void pop() {
	}
	/**
	 * ChipHolders cannot be popped, so override with an empty method
	 */
	@Override
	public void unpop() {
	}
	/**
	 * draws the ChipHolder
	 * ChipHolders cannot attach onto other chips either, so override the ChipActor functionality there
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(sprite, getX(), getY(), sprite.getWidth(), sprite.getHeight());
	}

	/**
	 * calculates the value of all chips above this one. overrides since ChipHolders do not have a value
	 * @return the value of all chips above this one
	 */
	@Override
	public int calculate() {
		if (chipAbove != null) return chipAbove.calculate();
		else return 0;
	}

	/**
	 * ChipHolders cannot float away, so override the ChipActor functionality
	 * this will still float the stack above it away though
	 */
	@Override
	public void floatAway() {
		if (chipAbove != null) chipAbove.floatAway();
	}
}
