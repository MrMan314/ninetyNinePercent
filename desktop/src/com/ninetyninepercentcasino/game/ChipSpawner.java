package com.ninetyninepercentcasino.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ninetyninepercentcasino.MainCasino;


public class ChipSpawner extends ChipActor {
	private MainCasino game;
	private ChipGroup chipGroup;
	private int chipValue;
	private Sprite sprite;


	/**
	 * initializes a new ChipSpawner
	 * @param game the game this ChipSpawner will deduct balance from
	 * @param chipGroup the ChipGroup this ChipSpawner will spawn to
	 * @param chipValue the value of the chip it will spawn
	 */
	public ChipSpawner(MainCasino game, ChipGroup chipGroup, int chipValue) {
		this.game = game;
		this.chipGroup = chipGroup;
		this.chipValue = chipValue;
		sprite = new Sprite(findTexture(chipValue));
		sprite.setSize(CHIP_WIDTH, CHIP_HEIGHT);
		setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight());
		addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.balance -= chipValue;
				chipGroup.spawnChip(chipValue, getX(), getY()+CHIP_DISTANCE);
				return true;
			}
		});
	}
	/**
	 * draws the ChipSpawner
	 * ChipSpawners cannot attach onto other chips either, so override the ChipActor functionality there
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(sprite, getX(), getY(), sprite.getWidth(), sprite.getHeight());
	}
}
