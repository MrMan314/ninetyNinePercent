package com.ninetyninepercentcasino.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ninetyninepercentcasino.MainCasino;

/**
 * Main menu of the game
 * @author Grant Liang
 */
public class MainMenu extends CasinoScreen {
    private Texture background;

    public MainMenu(MainCasino game) {
        super(game);
    }
    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skins = new Skin();
        skins.add("titleBanner", new Texture("MainMenu/TitleBanner.png"));
        skins.add("playButton", new Texture("MainMenu/PlayButton.png"));
        skins.add("settingsButton", new Texture("MainMenu/SettingsButton.png"));

        Image titleBanner = new Image(skins.getDrawable("titleBanner"));
        Button playButton = new Button(skins.getDrawable("playButton"));
        Button settingsButton = new Button(skins.getDrawable("settingsButton"));

        VerticalGroup middleMenu = new VerticalGroup();
        middleMenu.addActor(playButton);
        middleMenu.addActor(settingsButton);

        Table root = new Table();
        root.setFillParent(true);
        root.add(titleBanner).width(800).height(800*((float) 191/446)).fillX().top().padBottom(80);
        root.row();
        root.add(middleMenu).padBottom(160);

        stage.addActor(root);

        background = new Texture("MainMenu/Background.jpg");

        ClickListener buttonDown = new ClickListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (event.getTarget()).setColor(65, 65, 65, 0.7f);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (event.getTarget()).setColor(1, 1, 1, 1f);
            }
        }; //makes the button fade a little when hovering over it

        settingsButton.addListener(new ChangeListener(){
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new PokerScreen(game));
            }
        });
        settingsButton.addListener(buttonDown);
        playButton.addListener(new ChangeListener(){
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new BlackjackScreen(game));
            }
        });
        playButton.addListener(buttonDown);


    }
    @Override
    public void render(float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().begin();
        stage.getBatch().setColor(1, 1,1 ,1f);
        stage.getBatch().draw(background, 0, 0, 2000, 2000*((float) 2/3));
        stage.getBatch().end();
        stage.draw();
        stage.act();
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
        background.dispose();
    }
}
