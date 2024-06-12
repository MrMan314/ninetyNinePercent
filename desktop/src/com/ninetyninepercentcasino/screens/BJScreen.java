package com.ninetyninepercentcasino.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.ninetyninepercentcasino.MainCasino;
import com.ninetyninepercentcasino.bj.BJClient;
import com.ninetyninepercentcasino.bj.BJStage;
import com.ninetyninepercentcasino.net.BJBeginGame;
import com.ninetyninepercentcasino.net.DTO;
import com.ninetyninepercentcasino.net.NetMessage;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Screen that renders a BJ game
 * @author Grant Liang
 */
public class BJScreen extends CasinoScreen {
	private Texture background;
	private BJClient client;
	private BJStage stage;
	private ArrayList<DTO> updates; //stores all pending updates from the server
	private boolean firstRender;

	/**
	 * initializes a new BJScreen
	 * @param game the game this screen belongs to
	 * @param previousScreen the screen previously displayed
	 */
	public BJScreen(MainCasino game, CasinoScreen previousScreen) {
		super(game, previousScreen);
	}

	/**
	 * initializes a new BJScreen
	 * @param game the game this screen belongs to
	 */
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

	/**
	 * called when the screen is shown
	 */
	@Override
	public void show() {
		firstRender = true;
		stage = new BJStage(new ExtendViewport(1312, 738, 1312, 738));
		Gdx.input.setInputProcessor(stage);

		updates = new ArrayList<>();

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
			client.message(new NetMessage(NetMessage.MessageType.INFO, new BJBeginGame())); //begin the game once connected
		} catch (IOException ignored) {
		}
		stage.setClient(client);
		stage.setScreen(this);

	}

	/**
	 * called when the screen should render itself
	 * @param delta The time in seconds since the last render.
	 */
	@Override
	public void render(float delta) {
		if(firstRender) {
			Gdx.graphics.requestRendering();
			firstRender = false;
		}
		if(!updates.isEmpty()){
			stage.handleDTO(updates.remove(0)); //update the stage with a DTO if there are still DTOs in the queue
		}
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.updateBetDisplay(); //update the chip calculator number display
		stage.getBatch().begin();
		stage.getBatch().setColor(Color.WHITE);
		stage.getBatch().draw(background, -((1920-stage.getViewport().getWorldWidth())/2), -((1080-stage.getViewport().getWorldHeight())/2)); //draw the background
		stage.getBatch().end();
		updateGlobalUI();
		stage.act(delta); //act all actors in the stage
		stage.draw(); //draw all actors in the stage
	}

	/**
	 * called when the screen is hidden from view
	 */
	@Override
	public void hide() {

	}

	/**
	 * called when the application is paused from the user unfocusing the window
	 */
	@Override
	public void pause() {

	}
	/**
	 * called when the user focuses back onto the window
	 */
	@Override
	public void resume() {

	}
	/**
	 * called when the screen is disposed
	 */
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
	 * called by the BJClient to request an update to the game state
	 * since multithreading and scene2d don't go well together, this must be done to move the update onto the main Application thread
	 * @param latestUpdate the DTO transferred by the server holding the information for the update
	 */
	public void requestUpdate(DTO latestUpdate){
		updates.add(latestUpdate);
		Gdx.graphics.requestRendering();
	}
}
