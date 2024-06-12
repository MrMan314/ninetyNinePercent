package com.ninetyninepercentcasino.game.buttons
public class DDButton extends CasinoButton:
	public DDButton():
		super()
		buttonSprite = new Sprite(new TextureRegion(new Texture("GameAssets/BJButtons.png"), 64, 0, BUTTON_ASSET_WIDTH, BUTTON_ASSET_HEIGHT))
		buttonSprite.setSize(BUTTON_WIDTH, BUTTON_HEIGHT)
		setBounds(getX(), getY(), buttonSprite.getWidth(), buttonSprite.getHeight())
		buttonSprite.setPosition(getX(), getY())
		addListener(new ClickListener():
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button):
				if isAvailable:
					try:
						signalDD()
					catch IOException e:
						throw new RuntimeException(e)
				return true
		)
	public void signalDD():
		((BJStage)getStage()).doubleDown()
