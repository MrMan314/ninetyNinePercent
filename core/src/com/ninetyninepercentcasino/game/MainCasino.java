package com.ninetyninepercentcasino.game;

import com.badlogic.gdx.Game;

public class MainCasino extends Game {
//	final float VIRTUAL_HEIGHT = 2f; //camera height in meters
//	SpriteBatch batch;
//	Texture allSpades;
//	private TextureRegion region;
	@Override
	public void create () {
		setScreen(new MainMenu(this));
		//deck = new Deck();
		//batch = new SpriteBatch();
		//allSpades = new Texture("PokerAssets/Top-Down/Cards/SpadesRemovedBackground.png");
		//region = new TextureRegion(allSpades, 0, 0, 88, 124);
		/*float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();*/
	}
//	public void render () {
//		super.render();
//		handleInput();
//		camera.update();
//		batch.setProjectionMatrix(camera.combined);
//		ScreenUtils.clear(1, 150, 150, 150);
//		batch.begin();
//		batch.draw(region, 0, 0);
//		batch.draw(allSpades, 0.5f, 0.5f, 1, 1);
//		deck.drawDeck(batch);
//		batch.end();
//	}
}
