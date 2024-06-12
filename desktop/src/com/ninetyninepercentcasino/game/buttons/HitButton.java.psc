package com.ninetyninepercentcasino.game.buttons
public class HitButton extends CasinoButton:
	public HitButton():
		super()
		buttonSprite = new Sprite(new TextureRegion(new Texture("GameAssets/BJButtons.png"), 192, 0, BUTTON_ASSET_WIDTH, BUTTON_ASSET_HEIGHT))
		buttonSprite.setSize(BUTTON_WIDTH, BUTTON_HEIGHT)
		setBounds(getX(), getY(), buttonSprite.getWidth(), buttonSprite.getHeight())
		buttonSprite.setPosition(getX(), getY())
		addListener(new ClickListener():
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button):
				if isAvailable:
					signalHit()
				return true
		)
	public void signalHit():
		((BJStage)getStage()).hit()
