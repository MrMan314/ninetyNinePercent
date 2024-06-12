package com.ninetyninepercentcasino.game.buttons
public abstract class CasinoButton extends Actor:
	protected static final int BUTTON_ASSET_WIDTH = 64
	protected static final int BUTTON_ASSET_HEIGHT = 72
	protected static final float BUTTON_WIDTH = 150
	protected static final float BUTTON_HEIGHT = BUTTON_WIDTH * ((float) BUTTON_ASSET_HEIGHT /BUTTON_ASSET_WIDTH)
	protected Sprite buttonSprite
	protected static final Sprite buttonOutlineSprite = new Sprite(new TextureRegion(new Texture(Gdx.files.internal("GameAssets/ButtonOutline.png"))))
	protected boolean isAvailable
	public CasinoButton():
		isAvailable = false
		setTouchable(Touchable.enabled)
		buttonOutlineSprite.setSize(BUTTON_WIDTH, BUTTON_HEIGHT)
		addListener(new ClickListener():
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor):
				if (isAvailable) buttonSprite.setColor(65, 65, 65, 0.8f)
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor):
				if (isAvailable) buttonSprite.setColor(1, 1,1 ,1f)
		)
	public void draw(Batch batch, float parentAlpha):
		setColor(Color.WHITE)
		buttonSprite.setPosition(getX(), getY())
		buttonOutlineSprite.setPosition(getX(), getY())
		if (!isAvailable) buttonOutlineSprite.draw(batch)
		else:
			buttonSprite.draw(batch)
	public void disable():
		isAvailable = false
	public void enable():
		isAvailable = true
