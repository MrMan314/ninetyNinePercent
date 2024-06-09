package com.ninetyninepercentcasino.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.ninetyninepercentcasino.MainCasino;
import com.ninetyninepercentcasino.bj.BJStage;
import com.ninetyninepercentcasino.bj.BJClient;
import com.ninetyninepercentcasino.net.*;

import java.io.IOException;
import java.net.Socket;
import java.net.ConnectException;
import java.util.ArrayList;

/**
 * Screen that renders a BJ game
 * @author Grant Liang
 */
public class BJScreen extends CasinoScreen {
	private Texture background;
	private boolean firstRender;
	private BJClient client;
	private BJStage stage;
	private ArrayList<DTO> updates = new ArrayList<>();

	public BJScreen(MainCasino game, CasinoScreen previousScreen) {
		super(game, previousScreen);
	}

	public BJScreen(MainCasino game) {
		super(game);
	}

	/**
	 * called whenever the application is resized
	 * @param width the width of the new window
	 * @param height the height of the new window
	 */
	@Override
	public void resize(int width, int height){
		stage.getViewport().update(width, height, true); //update the viewport of the screen's stage to accurately represent the screen size change
		screenHeight = Gdx.graphics.getHeight(); //update screenHeight and screenWidth variables as needed
		screenWidth = Gdx.graphics.getWidth();
	}

	@Override
	public void show() {
		firstRender = true;
		stage = new BJStage(new ExtendViewport(1312, 738, 1312, 738));
		Gdx.input.setInputProcessor(stage);

		background = new Texture("GameAssets/PokerTable.png");

		stage.addCaptureListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if (keycode == Input.Keys.ESCAPE) {
					game.setScreen(previousScreen);
					try {
						client.finish();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return true;
				}
				return false;
			}
		});

		try {
			client = new BJClient(new Socket("127.0.0.1", 9925), this);
		} catch (ConnectException e) {
			game.setScreen(previousScreen);
			previousScreen.displayDialogBox(e.getMessage());
			getThis().dispose();
			return;
		} catch (IOException ignored) {
		}
		client.start();
		try {
			client.message(new NetMessage(NetMessage.MessageType.INFO, new BJBeginGame()));
		} catch (IOException ignored) {
		}
		stage.setClient(client);

	}

	@Override
	public void render(float delta) {
		if(firstRender) {
			Gdx.graphics.requestRendering();
			firstRender = false;
		}
		if(!updates.isEmpty()){
			stage.handleDTO(updates.remove(0));
		}
		ScreenUtils.clear(0, 0, 0, 1f);
		stage.updateBetDisplay();
		stage.getBatch().begin();
		stage.getBatch().draw(background, -((1920-stage.getViewport().getWorldWidth())/2), -((1080-stage.getViewport().getWorldHeight())/2));
		stage.getBatch().end();
		stage.act(delta);
		stage.draw();

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
		try {
			client.finish();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {

		}
		background.dispose();
		stage.dispose();
	}

	/**
	 * called by the client to request an update to the game state
	 * since multithreading and scene2d don't go well together, this must be done to move the update onto the main Application thread
	 * @param latestUpdate the DTO transferred by the server holding the information for the update
	 */
	public void requestUpdate(DTO latestUpdate){
		updates.add(latestUpdate);
		Gdx.graphics.requestRendering();
	}
}
