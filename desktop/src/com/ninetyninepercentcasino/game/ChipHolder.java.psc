package com.ninetyninepercentcasino.game
public class ChipHolder extends ChipActor:
	private static final Sprite sprite = new Sprite(new TextureRegion(new Texture("GameAssets/Isometric/Chips/Template/Template_Outline_Flat_Small-87x54.png"), 0, 0, 88, 56))
	public ChipHolder():
		chipAbove = null
		sprite.setSize(CHIP_WIDTH, CHIP_HEIGHT)
		setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight())
	@Override
	public void pop():
	@Override
	public void unpop():
	@Override
	public void draw(Batch batch, float parentAlpha):
		setColor(Color.WHITE)
		batch.draw(sprite, getX(), getY(), sprite.getWidth(), sprite.getHeight())
	@Override
	public int calculate():
		if (chipAbove != null) return chipAbove.calculate()
		else return 0
	@Override
	public void floatAway():
		if (chipAbove != null) chipAbove.floatAway()
