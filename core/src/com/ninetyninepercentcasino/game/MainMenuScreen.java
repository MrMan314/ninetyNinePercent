package com.ninetyninepercentcasino.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class MainMenuScreen implements Screen {
    private final Stage stage;
    private final PlayButton playButton;
    private final BitmapFont font;
    final float VIRTUAL_HEIGHT = 2f;
    private float rotationSpeed;
    private float angle = 0f;
    float screenWidth;
    float screenHeight;
    final MainCasino game;
    OrthographicCamera camera;
    public MainMenuScreen(final MainCasino game) {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        this.game = game;
        camera = new OrthographicCamera(VIRTUAL_HEIGHT * screenWidth / screenHeight, VIRTUAL_HEIGHT);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        font.getData().setScale(.05F);
        playButton = new PlayButton();
    }
    private void handleInput() {
        if(angle >= 360) angle -= 360;
        Vector2 cameraUp = new Vector2(0, 1);
        cameraUp.clamp(0.2f, 0.2f);
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
    public void show() {

    }
    @Override
    public void render(float delta){
        ScreenUtils.clear(0, 0, 0.2f, 1);
        handleInput();
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        font.draw(game.batch, "99% Casino", -0.5f, 1.5f);
        playButton.draw(game.batch, 1, 1);
        //game.font.draw(game.batch, "Test line", 0, 1);
        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

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
        font.dispose();
    }
}
