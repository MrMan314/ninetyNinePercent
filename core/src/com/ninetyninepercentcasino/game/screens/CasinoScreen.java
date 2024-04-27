package com.ninetyninepercentcasino.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class CasinoScreen implements Screen {
    Game game;
    Stage stage;

    public CasinoScreen(Game game){
        this.game = game;
    }
    @Override
    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
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
