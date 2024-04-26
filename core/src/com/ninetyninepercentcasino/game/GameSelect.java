package com.ninetyninepercentcasino.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class GameSelect extends GameScreen {

    public GameSelect(final Game game) {
        super(game);
        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
    }
    @Override
    public void render(float delta){
        ScreenUtils.clear(0, 0, 0.2f, 1);
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

    }
}
