package com.ninetyninepercentcasino.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.ninetyninepercentcasino.MainCasino;
import com.ninetyninepercentcasino.text.HelpDisplay;
import com.ninetyninepercentcasino.text.LabelStyleGenerator;

/**
 * Describes general characteristics of screens used in the project
 * @author Grant Liang
 */
public abstract class CasinoScreen implements Screen {
	protected MainCasino game; //screen has a game instance, so it can create a new screen for the same game
	protected Stage stage; //stage to manage the actors on the screen
	protected float screenHeight = Gdx.graphics.getHeight(); //height of client area in pixels
	protected float screenWidth = Gdx.graphics.getWidth(); //width of client area in pixels
	protected CasinoScreen previousScreen;
	protected Table globalUI;
	protected Label balanceDisplay; //displays the user's current balance

	/**
	 * general constructor for a screen in the game
	 * @param game the game that the screen is a part of
	 */
	public CasinoScreen(MainCasino game) {
		this.game = game; //record the game as the one passed to the constructor
		LabelStyleGenerator LSG = new LabelStyleGenerator();
		balanceDisplay = new Label("$1000", LSG.getLeagueGothicLabelStyle(60));
		globalUI = new Table();
		globalUI.setFillParent(true);
		globalUI.top().left();
		globalUI.add(new HelpDisplay()).right().top().padTop(balanceDisplay.getHeight()/12f).padLeft(balanceDisplay.getHeight()/8f);
		globalUI.add(balanceDisplay).padTop(balanceDisplay.getHeight()/12f).padLeft(balanceDisplay.getHeight()/8f);
	}

	/**
	 * general constructor for a screen in the game
	 * @param game the game that the screen is a part of
	 */
	public CasinoScreen(MainCasino game, CasinoScreen previousScreen) {
		this(game);
		this.previousScreen = previousScreen;
	}

	/**
	 * called whenever the application is resized
	 * @param width the width of the new window
	 * @param height the height of the new window
	 */
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true); //update the viewport of the screen's stage to accurately represent the screen size change
		screenHeight = Gdx.graphics.getHeight(); //update screenHeight and screenWidth variables as needed
		screenWidth = Gdx.graphics.getWidth();
	}

	/**
	 * @return this screen
	 */
	public CasinoScreen getThis() {
		return this;
	}

	/**
	 * @return the previous screen
	 */
	public CasinoScreen getPreviousScreen() {
		return previousScreen;
	}

	/**
	 * displays a dialogue box
	 * @param message the message to be displayed
	 */
	public void displayDialogBox(String message) {
		LabelStyleGenerator LSG = new LabelStyleGenerator();
		Label text;
		Label.LabelStyle labelStyle = LSG.getLeagueGothicLabelStyle(60);
		text = new Label(message, labelStyle);
		text.setPosition(700, 200);

		stage.addActor(text);
	}

	/**
	 * updates any global UI modules
	 */
	public void updateGlobalUI() {
		balanceDisplay.setText("$" + game.balance); //update the bet display to the player's balance
	}
	public Game getGame() {
		return game;
	}

	/**
	 * called when the screen is shown i.e. it is the one the user is currently looking at
	 */
	@Override
	public abstract void show ();
	/**
	 * called when the screen is hidden from view
	 */
	@Override
	public abstract void hide ();

	/**
	 * called when the application is paused from the user unfocusing the window
	 */
	@Override
	public abstract void pause ();

	/**
	 * called when the user focuses back onto the window
	 */
	@Override
	public abstract void resume ();

	/**
	 * called when the screen is disposed
	 */
	@Override
	public abstract void dispose ();
}
