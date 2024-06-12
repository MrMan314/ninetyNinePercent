package com.ninetyninepercentcasino.game
public class ChipSpawner extends ChipActor {
	private MainCasino game
	private ChipGroup chipGroup
	private int chipValue
	private Sprite sprite
	public ChipSpawner(MainCasino game, ChipGroup chipGroup, int chipValue) {
		this.game = game
		this.chipGroup = chipGroup
		this.chipValue = chipValue
		sprite = new Sprite(findTexture(chipValue))
		sprite.setSize(CHIP_WIDTH, CHIP_HEIGHT)
		setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight())
		addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.balance -= chipValue
				chipGroup.spawnChip(chipValue, getX(), getY()+CHIP_DISTANCE)
				return true
			}
		})
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(sprite, getX(), getY(), sprite.getWidth(), sprite.getHeight())
	}
}
