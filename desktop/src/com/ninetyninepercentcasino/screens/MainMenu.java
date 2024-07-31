package com.ninetyninepercentcasino.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.ninetyninepercentcasino.MainCasino;

/**
 * Main menu that leads to the blackjack game and settings menu
 * @author Grant Liang
 */
public class MainMenu extends CasinoScreen {
	private Texture background; //the background texture
	private CasinoScreen nextScreen;

	/**
	 * initializes a new main menu
	 * @param game the game that this screen belongs to
	 */
	public MainMenu(MainCasino game) {
		super(game);
	}

	/**
	 * called when the main menu is shown
	 * this method will load everything needed on the main menu
	 */
	@Override
	public void show() {
		stage = new Stage(new ExtendViewport(1920, 1080, 1920, 1080));
		Gdx.input.setInputProcessor(stage); //set the input processor to be the stage of the current screen so inputs are handled by the visible screen first


		Skin skins = new Skin();
		skins.add("titleBanner", new Texture("Menus/TitleBanner.png"));
		skins.add("playButton", new Texture("Menus/PlayButton.png"));
		skins.add("settingsButton", new Texture("Menus/SettingsButton.png"));

		Image titleBanner = new Image(skins.getDrawable("titleBanner"));
		Button playButton = new Button(skins.getDrawable("playButton"));
		Button settingsButton = new Button(skins.getDrawable("settingsButton"));

		VerticalGroup middleMenu = new VerticalGroup(); //the group that will hold the buttons and banner in the center of the screen
		middleMenu.addActor(playButton);
		//middleMenu.addActor(settingsButton);

		Table root = new Table();
		root.setFillParent(true);
		root.add(titleBanner).width(800).height(800*((float) 191/446)).fillX().top().padBottom(80);
		root.row();
		root.add(middleMenu).padBottom(160);

		stage.addActor(root);
		stage.addActor(globalUI);

		background = new Texture("Menus/Background.jpg");

//		/*
//		 * adding change listeners to the buttons on the main menu
//		 * this gives them their functionality to switch the game over to another screen when clicked
//		 */
//		settingsButton.addListener(new ChangeListener(){
//			public void changed (ChangeEvent event, Actor actor) {
//				nextScreen = new AccountMenu(game, getThis());
//				game.setScreen(nextScreen);
//			}
//		});
		settingsButton.addListener(new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				(event.getTarget()).setColor(65, 65, 65, 0.7f); //fades the button slightly when the cursor enters
			}
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				(event.getTarget()).setColor(1, 1, 1, 1f); //resets the fade when the cursor exits the actor
			}
		});
		playButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				nextScreen = new BJScreen(game, getThis());
				game.setScreen(nextScreen); //set the screen to be a new game selection screen
			}
		});
		playButton.addListener( new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				(event.getTarget()).setColor(65, 65, 65, 0.7f); //fades the button slightly when the cursor enters
			}
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				(event.getTarget()).setColor(1, 1, 1, 1f); //resets the fade when the cursor exits the actor
			}
		});

	}

	/**
	 * called when the screen should render itself
	 * @param delta The time in seconds since the last render.
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clear the screen to remove previously drawn frames
		stage.getBatch().begin(); //begin the stage batch to draw the background separately
		stage.getBatch().setColor(1, 1,1 ,1f);
		stage.getBatch().draw(background, 0, 0, 2000, 2000*((float) 2/3));
		stage.getBatch().end();
		updateGlobalUI();
		stage.draw(); //draw all actors on stage
		stage.act(); //act all actors on stage
	}

	/**
	 * called when the user focuses off the screen
	 */
	@Override
	public void pause() {

	}

	/**
	 * called when the user refocuses onto the screen
	 */
	@Override
	public void resume() {

	}

	/**
	 * called when the screen is hidden
	 */
	@Override
	public void hide() {
	}

	/**
	 * disposes of this screen
	 */
	@Override
	public void dispose() {
		stage.dispose();
		try {
			nextScreen.dispose();
		} catch (NullPointerException e) {
		}
		background.dispose();
	}
}
