package com.ninetyninepercentcasino.game.buttons
public class BetButton extends CasinoButton:
	public BetButton():
		super()
		buttonSprite = new Sprite(new TextureRegion(new Texture("GameAssets/BetButton.png"), 0, 0, 64, 72))
		buttonSprite.setSize(192, 192 * ((float) 72/64))
		setBounds(getX(), getY(), buttonSprite.getWidth(), buttonSprite.getHeight())
		buttonSprite.setPosition(getX(), getY())
		addListener(new ClickListener():
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button):
				if isAvailable:
					bet()
				return true
		)
	public void draw(Batch batch, float parentAlpha):
		buttonSprite.setPosition(getX(), getY())
		buttonSprite.draw(batch)
	public void bet():
		((BJStage)getStage()).sendBet()
		setVisible(false)
