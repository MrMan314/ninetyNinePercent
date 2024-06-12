package com.ninetyninepercentcasino.game.buttons
public class InsureButton extends CasinoButton:
	public InsureButton():
		super()
		buttonSprite = new Sprite(new TextureRegion(new Texture("GameAssets/BJButtons.png"), 256, 0, BUTTON_ASSET_WIDTH, BUTTON_ASSET_HEIGHT))
		buttonSprite.setSize(BUTTON_WIDTH, BUTTON_HEIGHT)
		setBounds(getX(), getY(), buttonSprite.getWidth(), buttonSprite.getHeight())
		buttonSprite.setPosition(getX(), getY())
		addListener(new ClickListener():
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button):
				if (isAvailable) signalInsure()
				return true
		)
	public void signalInsure():
		((BJStage)getStage()).sendInsure()
