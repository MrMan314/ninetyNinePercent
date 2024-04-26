package com.ninetyninepercentcasino.game.screens;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ninetyninepercentcasino.game.PlayButton;

public class MainMenu extends GameScreen {
    SpriteBatch batch = new SpriteBatch();
    private Stage stage;
    private Table table;
    //private Button playButton = new Button(new Texture("MainMenu/WoodenBanner.png"));
    Texture background;
    PlayButton playButton;
    public MainMenu(Game game) {
        super(game);
    }
    @Override
    public void show() {
        background = new Texture("MainMenu/Background.jpg");
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        playButton = new PlayButton(game);
        stage.addActor(table);
        stage.addActor(playButton);
    }
    @Override
    public void render(float delta){
        ScreenUtils.clear(150, 0, 0.2f, 1);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.end();
        stage.draw();
        stage.act();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
    }
}
