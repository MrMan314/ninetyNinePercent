package com.ninetyninepercentcasino.game.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ninetyninepercentcasino.game.MainCasino;

/**
 * Game screen for casino game selection
 * @author Grant Liang
 */
public class GameSelect extends CasinoScreen {

    public GameSelect(MainCasino game) {
        super(game);
        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {

    }
    @Override
    public void render(float delta){
        ScreenUtils.clear(0, 0, 0f, 1);
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
