package com.ninetyninepercentcasino.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.ninetyninepercentcasino.MainCasino;
import com.ninetyninepercentcasino.game.gameparts.Chip;
import com.ninetyninepercentcasino.gameparts.ChipActor;
import com.ninetyninepercentcasino.gameparts.ChipGroup;
import com.ninetyninepercentcasino.gameparts.ChipHolder;
import com.ninetyninepercentcasino.gameparts.NumberDisplay;

/**
 * Main menu of the game
 * @author Grant Liang
 */
public class MainMenu extends CasinoScreen {
	private Texture background;
	private ChipGroup chipGroup;
	private Label totalBet;
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

		VerticalGroup middleMenu = new VerticalGroup();
		middleMenu.addActor(playButton);
		middleMenu.addActor(settingsButton);

		Table root = new Table();
		root.setFillParent(true);
		root.add(titleBanner).width(800).height(800*((float) 191/446)).fillX().top().padBottom(80);
		root.row();
		root.add(middleMenu).padBottom(160);

		stage.addActor(root);

		background = new Texture("Menus/Background.jpg");

		/*
		 * adding change listeners to the buttons on the main menu
		 * this gives them their functionality to switch the game over to another screen when clicked
		 */
		settingsButton.addListener(new ChangeListener(){
			public void changed (ChangeEvent event, Actor actor) {
				game.setScreen(new AccountMenu(game));
			}
		});
		settingsButton.addListener(new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				(event.getTarget()).setColor(65, 65, 65, 0.7f); //fades the button slightly when the cursor enters
			}
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				(event.getTarget()).setColor(1, 1, 1, 1f); //resets the fade when the cursor exits the actor
			}
		});
		playButton.addListener(new ChangeListener(){
			public void changed (ChangeEvent event, Actor actor) {
				game.setScreen(new GameSelect(game)); //set the screen to be a new game selection screen
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
	public void render(float delta){
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clear the screen to remove previously drawn frames
		stage.getBatch().begin(); //begin the stage batch to draw the background separately
		stage.getBatch().setColor(1, 1,1 ,1f);
		stage.getBatch().draw(background, 0, 0, 2000, 2000*((float) 2/3));
		stage.getBatch().end();
		stage.draw(); //draw all actors on stage
		stage.act(); //act all actors on stage
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		stage.dispose();
		background.dispose();
	}
}
