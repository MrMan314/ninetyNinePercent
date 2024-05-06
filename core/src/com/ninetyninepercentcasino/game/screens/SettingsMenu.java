package com.ninetyninepercentcasino.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ninetyninepercentcasino.game.MainCasino;

/**
 * Screen for settings menu
 * @author Grant Liang
 */
public class SettingsMenu extends CasinoScreen {
    private Texture background;

    public SettingsMenu(MainCasino game) {
        super(game);
        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        game.music.setVolume(0.1f);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skins = new Skin();
        skins.add("titleBanner", new Texture("MainMenu/TitleBanner.png"));
        skins.add("settingsButton", new Texture("MainMenu/SettingsButton.png"));

        Image titleBanner = new Image(skins.getDrawable("titleBanner"));
//        Slider masterVolumeSlider = new Slider(0f, 100f, 5f, false, skins);
        Button settingsButton = new Button(skins.getDrawable("settingsButton"));

        VerticalGroup middleMenu = new VerticalGroup();
//        middleMenu.addActor(masterVolumeSlider);
        middleMenu.addActor(settingsButton);

        Table root = new Table();
        root.setFillParent(true);
//        root.setDebug(true);
        root.add(titleBanner).width(800).height(800*((float) 191/446)).fillX().align(Align.top).padBottom(100).top();
        root.row();
        root.add(middleMenu);

        stage.addActor(root);

        background = new Texture("MainMenu/Background.jpg");

        stage.addCaptureListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                System.out.println("Capture listener.");
                if(keycode == Input.Keys.ESCAPE) {
                    game.setScreen(new MainMenu(game));
                    return true;
                }
                return false;
            }
        });

        ClickListener buttonDown = new ClickListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (event.getTarget()).setColor(65, 65, 65, 0.7f);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                (event.getTarget()).setColor(1, 1, 1, 1f);
            }
        }; //makes the button fade a little when hovering over it

        settingsButton.addListener(buttonDown);
        settingsButton.addListener(new ChangeListener(){
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
            }
        });
    }
    @Override
    public void render(float delta){
        ScreenUtils.clear(0, 0, 0f, 1);
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
        game.music.setVolume(1f);
    }

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
    }
}
