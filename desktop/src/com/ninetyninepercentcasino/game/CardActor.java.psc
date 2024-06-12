package com.ninetyninepercentcasino.game
public class CardActor extends Actor:
	private Card card
	private boolean faceUp
	private boolean isLocalCard
	boolean popped
	final static float POP_DISTANCE = 20
	final static TextureRegion faceDownTex = new TextureRegion(new Texture("GameAssets/Top-Down/Cards/Card_Back-88x124.png"), 0, 0, 88, 124)
	final TextureRegion faceUpTex
	private Sprite sprite
	public CardActor(Card card, boolean faceUp, boolean isLocalCard):
		this.card = card
		faceUpTex = new TextureRegion(findTexture(card))
		if (faceUp) sprite = new Sprite(faceUpTex)
		else sprite = new Sprite(faceDownTex)
		if isLocalCard:
			sprite.setSize(sprite.getWidth()*3, sprite.getHeight()*3)
		else setTouchable(Touchable.disabled)
		this.isLocalCard = isLocalCard
		popped = false
		setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight())
		addListener(new ClickListener():
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor):
				if (pointer == -1) popped = true
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor):
				if (pointer == -1) popped = false
		)
	public void reveal():
		if !faceUp:
			faceUp = true
			sprite = new Sprite(faceUpTex)
	public void hide():
		if faceUp:
			faceUp = false
			sprite = new Sprite(faceDownTex)
	public void draw(Batch batch, float parentAlpha):
		batch.setColor(Color.WHITE)
		if popped:
			batch.draw(sprite, getX(), getY()+POP_DISTANCE, sprite.getWidth(), sprite.getHeight())
		else batch.draw(sprite, getX(), getY(), sprite.getWidth(), sprite.getHeight())
	public void makeActive():
		if !isLocalCard:
			sprite.setSize(sprite.getWidth()*3, sprite.getHeight()*3)
			isLocalCard = true
			setTouchable(Touchable.enabled)
	public Card getCard():
		return card
	private TextureRegion findTexture(Card card):
		int cardNum = card.getNum()
		String suit = card.getSuitName()
		int textureRegionX = 0
		int textureRegionY = 0
		for int i = 1; i < cardNum; i++:
			textureRegionX += 88
			if textureRegionX > 88*4:
				textureRegionX = 0
				textureRegionY += 124
		return new TextureRegion(new Texture("GameAssets/Top-Down/Cards/" + suit + ".png"), textureRegionX, textureRegionY, 88, 124)
