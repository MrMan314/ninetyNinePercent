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
    MainCasino game; //screen has a game instance so it can create a new screen for the same game
    Stage stage; //stage to manage the actors on the screen
    float screenHeight = Gdx.graphics.getHeight(); //height of client area in pixels
    float screenWidth = Gdx.graphics.getWidth(); //width of client area in pixels

    /**
     * general constructor for a screen in the game
     * @param game the game that the screen is a part of
     */
    public CasinoScreen(MainCasino game){
        this.game = game; //record the game as the one passed to the constructor
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
     * called when the screen is shown i.e. it is the one the user is currently looking at
     */
    @Override
    public abstract void show ();

    /**
     * called when the screen is hidden from view
     */
    @Override
    public abstract void hide ();

    /**
     * called when the application is paused from the user unfocusing the window
     */
    @Override
    public abstract void pause ();

    /**
     * called when the user focuses back onto the window
     */
    @Override
    public abstract void resume ();

    /**
     * called when the screen is disposed
     */
    @Override
    public abstract void dispose ();
}
