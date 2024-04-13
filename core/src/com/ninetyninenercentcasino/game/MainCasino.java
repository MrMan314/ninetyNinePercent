package com.ninetyninenercentcasino.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class MainCasino extends ApplicationAdapter {
	
	SpriteBatch batch;
	Texture allSpades;
	private TextureRegion region;
	private OrthographicCamera camera;
	private float rotationSpeed;
	private float angle = 0f;
	private void handleInput() {
		if(angle >= 360) angle -= 360;
		Vector2 cameraUp = new Vector2(0, 1);
		cameraUp.clamp(3, 3);
		cameraUp.rotateDeg(-angle);
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			camera.zoom += 0.1f;
			//If the A Key is pressed, add 0.1 to the Camera's Zoom
		}
		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
			camera.zoom -= 0.1f;
			//If the Q Key is pressed, subtract 0.1 from the Camera's Zoom
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			Vector2 cameraLeft = new Vector2(cameraUp.x, cameraUp.y);
			cameraLeft.rotateDeg(90);
			camera.translate(cameraLeft.x, cameraLeft.y, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			Vector2 cameraRight = new Vector2(cameraUp.x, cameraUp.y);
			cameraRight.rotateDeg(270);
			camera.translate(cameraRight.x, cameraRight.y, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			Vector2 cameraDown = new Vector2(cameraUp.x, cameraUp.y);
			cameraDown.rotateDeg(180);
			camera.translate(cameraDown.x, cameraDown.y, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			camera.translate(cameraUp.x, cameraUp.y, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			camera.rotate(-rotationSpeed, 0, 0, 1);
			angle += rotationSpeed;
			//If the W Key is pressed, rotate the camera by -rotationSpeed around the Z-Axis
		}
		if (Gdx.input.isKeyPressed(Input.Keys.E)) {
			camera.rotate(rotationSpeed, 0, 0, 1);
			angle -= rotationSpeed;
			//If the E Key is pressed, rotate the camera by rotationSpeed around the Z-Axis
		}
	}
	
	@Override
	public void create () {
		rotationSpeed = 1f;

		batch = new SpriteBatch();
		allSpades = new Texture("PokerAssets/Top-Down/Cards/SpadesRemovedBackground.png");
		region = new TextureRegion(allSpades, 0, 0, 88, 124);
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(30, 30 * (h/w));
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();

	}

	@Override
	public void render () {
		handleInput();
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		ScreenUtils.clear(1, 150, 150, 150);
		batch.begin();
		batch.draw(region, 0, 0);
		batch.draw(allSpades, 100, 0, 1000, 1000);
		batch.end();
	}	
	@Override
	public void dispose () {
		batch.dispose();
		allSpades.dispose();
	}
}
