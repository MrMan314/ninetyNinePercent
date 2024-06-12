package com.ninetyninepercentcasino.game.buttons
public class SplitButton extends CasinoButton:
	public SplitButton():
		super()
		buttonSprite = new Sprite(new TextureRegion(new Texture("GameAssets/BJButtons.png"), 0, 0, BUTTON_ASSET_WIDTH, BUTTON_ASSET_HEIGHT))
		buttonSprite.setSize(BUTTON_WIDTH, BUTTON_HEIGHT)
		setBounds(getX(), getY(), buttonSprite.getWidth(), buttonSprite.getHeight())
		buttonSprite.setPosition(getX(), getY())
		addListener(new ClickListener():
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button):
				if (isAvailable) signalSplit()
				return true
		)
	public void signalSplit():
		((BJStage)getStage()).split()
