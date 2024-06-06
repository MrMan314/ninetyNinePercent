package com.ninetyninepercentcasino.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ninetyninepercentcasino.MainCasino;

public class AccountMenu extends CasinoScreen {
	private Texture background;

	public AccountMenu(MainCasino game, CasinoScreen previousScreen) {
		super(game, previousScreen);
	}
	
	public AccountMenu(MainCasino game) {
		super(game);
	}

	@Override
	public void show() {
		stage = new Stage(new ExtendViewport(1920, 1080, 1920, 1080));
		Gdx.input.setInputProcessor(stage);

		Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

		Table infoInput = new Table();
		TextField usernameEntry = new TextField("", skin);
		TextField passwordEntry = new TextField("", skin);
		usernameEntry.setMessageText("Username");
		passwordEntry.setMessageText("Password");
		passwordEntry.setPasswordMode(true);
		infoInput.add(usernameEntry);
		infoInput.add(passwordEntry);

		Table root = new Table();
		root.setFillParent(true);
		root.add(infoInput);

		stage.addActor(root);

		background = new Texture("Menus/Background.jpg");
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.getBatch().begin();
		stage.getBatch().setColor(1, 1,1 ,1f);
		stage.getBatch().draw(background, 0, 0, 2000, 2000*((float) 2/3));
		stage.getBatch().end();
		stage.draw();
		stage.act();
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}
}
