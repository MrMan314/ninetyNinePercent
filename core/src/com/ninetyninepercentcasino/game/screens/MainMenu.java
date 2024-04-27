package com.ninetyninepercentcasino.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ninetyninepercentcasino.game.Card;


public class MainMenu extends CasinoScreen {
    private Texture background;
    private SpriteBatch batch;
    private Card testCard;

    public MainMenu(Game game) {
        super(game);
    }
    @Override
    public void show() {
        testCard = new Card(13, 1);
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
        //root.setDebug(true);
        root.add(titleBanner).width(800).height(800*((float) 191/446)).fillX().align(Align.top).padBottom(100).top();
        root.row();
        root.add(middleMenu);

        stage.addActor(root);

        background = new Texture("MainMenu/Background.jpg");
        batch = new SpriteBatch();

        ClickListener buttonDown = new ClickListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (event.getTarget()).setColor(65, 65, 65, 0.7f);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (event.getTarget()).setColor(1, 1, 1, 1f);
            }
        }; //makes the button fade a little when hovering over it

        playButton.addListener(new ChangeListener(){
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new GameSelect(game));
            }
        });
        playButton.addListener(buttonDown);

        settingsButton.addListener(buttonDown);
        settingsButton.addListener(new ChangeListener(){
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new SettingsMenu(game));
            }
        });
    }
    @Override
    public void render(float delta){
        ScreenUtils.clear(0, 0, 0f, 1);
        batch.begin();
        batch.draw(background, 0, 0, 2000, 2000*((float) 2/3));
        testCard.draw(batch, 0);
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
        background.dispose();
    }
}
