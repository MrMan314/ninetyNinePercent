package com.ninetyninepercentcasino.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.ninetyninepercentcasino.MainCasino;
import com.ninetyninepercentcasino.bj.BJGameStage;
import com.ninetyninepercentcasino.bj.bjbuttons.HitButton;
import com.ninetyninepercentcasino.bj.bjbuttons.StandButton;
import com.ninetyninepercentcasino.game.gameparts.Chip;
import com.ninetyninepercentcasino.gameparts.CardGroup;
import com.ninetyninepercentcasino.bj.BJClient;
import com.ninetyninepercentcasino.gameparts.ChipActor;
import com.ninetyninepercentcasino.gameparts.ChipStack;
import com.ninetyninepercentcasino.net.BJCardUpdate;
import com.ninetyninepercentcasino.net.Connection;
import com.ninetyninepercentcasino.net.DTO;
import com.ninetyninepercentcasino.net.NetMessage;

import java.io.IOException;
import java.net.Socket;

/**
 * Screen that renders a BJ game
 * @author Grant Liang
 */
public class BJScreen extends CasinoScreen {
    private Texture background;
    private boolean firstRender;
    private boolean updateNeeded;
    private BJClient client;
    private BJGameStage stage;
    private DTO latestUpdate;

    public BJScreen(MainCasino game) {
        super(game);
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

    @Override
    public void show() {
        updateNeeded = false;
        firstRender = true;
        stage = new BJGameStage(new ExtendViewport(1312, 738, 1312, 738));
        Gdx.input.setInputProcessor(stage);

        background = new Texture("GameAssets/PokerTable.png");

        stage.addCaptureListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    game.setScreen(new MainMenu(game));
                    return true;
                }
                return false;
            }
        });
        stage.addCaptureListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.ESCAPE) {
                    game.setScreen(new MainMenu(game));
                    return true;
                }
                float distance = 100f;
                if(keycode == Input.Keys.W){
                    stage.getCamera().translate(0, distance, 0);
                    return true;
                }
                if(keycode == Input.Keys.A){
                    stage.getCamera().translate(-distance, 0, 0);
                    return true;
                }
                if(keycode == Input.Keys.S){
                    stage.getCamera().translate(0, -distance, 0);
                    return true;
                }
                if(keycode == Input.Keys.D){
                    stage.getCamera().translate(distance, 0, 0);
                    return true;
                }
//                if(keycode == Input.Keys.E){
//                    ((OrthographicCamera)stage.getCamera()).zoom += 5;
//                    return true;
//                }
//                if(keycode == Input.Keys.F){
//                    ((OrthographicCamera)stage.getCamera()).zoom -= 5;
//                    return true;
//                }
                return false;
            }
        });

        try {
            client = new BJClient(new Socket("127.0.0.1", 9925), this);
        } catch (IOException e) {
            System.out.println();
        }
        client.start();
        try {
            client.message(new NetMessage(NetMessage.MessageType.INFO, "begin game."));
        } catch (IOException e) {
            System.out.println();
        }


    }

    @Override
    public void render(float delta) {
        if(firstRender) {
            Gdx.graphics.requestRendering();
            firstRender = false;
        }
        if(updateNeeded){
            if(latestUpdate instanceof BJCardUpdate){
                stage.addPlayerCard(((BJCardUpdate)latestUpdate).getCard());
                updateNeeded = false;
            }
        }
        ScreenUtils.clear(0, 0, 0, 1f);
        stage.getBatch().begin();
        stage.getBatch().draw(background, -((1920-stage.getViewport().getWorldWidth())/2), -((1080-stage.getViewport().getWorldHeight())/2));
        stage.getBatch().end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
    }
    public void requestUpdate(DTO latestUpdate){
        updateNeeded = true;
        this.latestUpdate = latestUpdate;
    }
}
