package com.ninetyninepercentcasino.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.ninetyninepercentcasino.MainCasino;

/**
 * Describes general characteristics of screens used in the project
 * @author Grant Liang
 */
public abstract class CasinoScreen implements Screen {
    MainCasino game;
    Stage stage;
    float screenHeight = Gdx.graphics.getHeight();
    float screenWidth = Gdx.graphics.getWidth();
    public CasinoScreen(MainCasino game){
        this.game = game;
    }
    @Override
    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
    }

    @Override
    public abstract void show ();

    @Override
    public abstract void hide ();

    @Override
    public abstract void pause ();

    @Override
    public abstract void resume ();

    @Override
    public abstract void dispose ();
}
