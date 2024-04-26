package com.ninetyninepercentcasino.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class GameScreen implements Screen {
    Game game;

    public GameScreen(Game game){
        this.game = game;
    }
    @Override
    public abstract void resize(int width, int height);

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
