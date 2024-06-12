package com.ninetyninepercentcasino.game.buttons
public class FoldButton extends CasinoButton:
	public FoldButton():
		super()
		buttonSprite = new Sprite(new TextureRegion(new Texture("GameAssets/PokerButtons.png"), 128, 0, 64, 72))
		buttonSprite.setSize(192, 192 * ((float) 72/64))
		setBounds(getX(), getY(), buttonSprite.getWidth(), buttonSprite.getHeight())
		buttonSprite.setPosition(getX(), getY())
		addListener(new ClickListener():
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button):
				signalRaise()
				return true
		)
	public void draw(Batch batch, float parentAlpha):
		buttonSprite.setPosition(getX(), getY())
		buttonSprite.draw(batch)
	public void signalRaise():
