package com.ninetyninepercentcasino.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainCasino extends Game {
	final float VIRTUAL_HEIGHT = 2f; //camera height in meters
	SpriteBatch batch;
	Texture allSpades;
	private TextureRegion region;
	private OrthographicCamera camera;
	Deck deck;
	public MainMenuScreen mainMenuScreen;
	@Override
	public void create () {
		mainMenuScreen = new MainMenuScreen(this);
		this.setScreen(mainMenuScreen);
		deck = new Deck();
		batch = new SpriteBatch();
		allSpades = new Texture("PokerAssets/Top-Down/Cards/SpadesRemovedBackground.png");
		region = new TextureRegion(allSpades, 0, 0, 88, 124);
		/*float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(VIRTUAL_HEIGHT * screenWidth / screenHeight, VIRTUAL_HEIGHT);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0); //set camera to center of the world
		camera.update();*/

	}

	@Override
	public void render () {
		super.render();
		/*handleInput();
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		ScreenUtils.clear(1, 150, 150, 150);
		batch.begin();
		batch.draw(region, 0, 0);
		batch.draw(allSpades, 0.5f, 0.5f, 1, 1);
		deck.drawDeck(batch);
		batch.end();*/
	}
	@Override
	public void dispose () {
		batch.dispose();
		allSpades.dispose();
		mainMenuScreen.dispose();
	}
}
