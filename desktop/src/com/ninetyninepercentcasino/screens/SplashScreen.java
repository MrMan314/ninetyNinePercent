package com.ninetyninepercentcasino.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.ninetyninepercentcasino.MainCasino;

/**
 * Class for a splash screen displayed for 2 seconds before the main menu is displayed
 */
public class SplashScreen implements Screen {
	private float time;
	private float alpha; //will store the alpha (fade effect) to draw the screen at
	private final MainCasino game;
	private Stage stage;
	private Texture splashScreen;

	/**
	 * initializes a new splash screen
	 * @param game the game that this screen belongs to
	 */
	public SplashScreen(MainCasino game){
		this.game = game;
	}
	/**
	 * Called when this screen becomes the current screen for the game.
	 */
	@Override
	public void show() {
		time = 0;
		alpha = 0;
		splashScreen = new Texture("GameAssets/SplashScreen.png");
		stage = new Stage(new ExtendViewport(1920, 1080, 1920, 1080));
	}

	/**
	 * Called when the screen should render itself.
	 * @param delta The time in seconds since the last render.
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clear the screen to remove previously drawn frames
		time += delta;
		if(time < 1){ //
			alpha += delta; //fade in effect
		}
		else if(time > 3){ //begin fading out once 3 seconds have passed
			alpha -= delta; //fade out effect
		}
		if(time > 4f){ //once the screen has been displayed for more than 2 seconds, switch to the main menu screen
			dispose(); //dispose of this screen
			Gdx.graphics.setContinuousRendering(false); //the rest of this project is a turn-based game that doesn't require continuous rendering
			game.music.playMusic(); //begin the music
			game.setScreen(new MainMenu(game)); //set the screen to be the main menu screen, passing it an instance of the game
			Gdx.graphics.requestRendering();
		}
		else {
			stage.getBatch().begin();
			stage.getBatch().setColor(255, 255, 255, alpha); //set the batch to the appropriate alpha
			stage.getBatch().draw(splashScreen, 0, 0); //draw the splashScreen
			stage.getBatch().end();
		}
	}

	/**
	 * @param width the width of the new window
	 * @param height the height of the new window
	 */
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true); //update the viewport of the screen's stage to accurately represent the screen size change
	}

	/**
	 * Called when the user unfocuses from the window
	 */
	@Override
	public void pause() {

	}

	/**
	 * called when the user refocuses onto the window
	 */
	@Override
	public void resume() {

	}

	/**
	 * Called when this screen is no longer the current screen for the game.
	 */
	@Override
	public void hide() {

	}

	/**
	 * Called when this screen should release all resources.
	 */
	@Override
	public void dispose() {
		splashScreen.dispose();
		stage.dispose();
	}
}
