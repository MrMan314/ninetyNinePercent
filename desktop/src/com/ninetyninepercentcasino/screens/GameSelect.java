package com.ninetyninepercentcasino.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ninetyninepercentcasino.MainCasino;

/**
 * Game screen for casino game selection
 * @author Grant Liang
 */
public class GameSelect extends CasinoScreen {
	private Texture background;

	public GameSelect(MainCasino game, CasinoScreen previousScreen) {
		super(game, previousScreen);
	}

	public GameSelect(MainCasino game) {
		super(game);
	}

	@Override
	public void show() {
		stage = new Stage(new ExtendViewport(1920, 1080, 1920, 1080));
		Gdx.input.setInputProcessor(stage);

		Image titleBanner = new Image(new TextureRegionDrawable(new Texture("Menus/TitleBanner.png")));
		Button BJButton = new Button(new TextureRegionDrawable(new Texture("Menus/PlayButton.png")));
		Button pokerButton = new Button(new TextureRegionDrawable(new Texture("Menus/PlayButton.png")));

		Table top = new Table();
		top.add(titleBanner);

		Table gameOptions = new Table();
		gameOptions.add(BJButton);
		gameOptions.add(pokerButton);

		Table root = new Table();
		root.setFillParent(true);
		root.add(top);
		root.row();
		root.add(gameOptions);

		stage.addActor(root);

		ClickListener buttonDown = new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				(event.getTarget()).setColor(65, 65, 65, 0.7f);
			}
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				(event.getTarget()).setColor(1, 1, 1, 1f);
			}
		}; //makes the button fade a little when hovering over it

		BJButton.addListener(new ChangeListener(){
			public void changed (ChangeEvent event, Actor actor) {
				game.setScreen(new BJScreen(game, getThis().getPreviousScreen()));
			}
		});
		BJButton.addListener(buttonDown);

		pokerButton.addListener(new ChangeListener(){
			public void changed (ChangeEvent event, Actor actor) {
				game.setScreen(new PokerScreen(game, getThis().getPreviousScreen()));
			}
		});
		pokerButton.addListener(buttonDown);

		background = new Texture("Menus/Background.jpg");
	}
	@Override
	public void render(float delta){
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.getBatch().begin();
		stage.getBatch().setColor(1, 1,1 ,1f);
		stage.getBatch().draw(background, 0, 0, 2000, 2000*((float) 2/3));
		stage.getBatch().end();
		stage.draw();
		stage.act();
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

	}
}
