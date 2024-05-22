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
import com.ninetyninepercentcasino.bj.bjbuttons.HitButton;
import com.ninetyninepercentcasino.bj.bjbuttons.StandButton;
import com.ninetyninepercentcasino.game.gameparts.Chip;
import com.ninetyninepercentcasino.gameparts.CardGroup;
import com.ninetyninepercentcasino.bj.BJClient;
import com.ninetyninepercentcasino.gameparts.ChipActor;
import com.ninetyninepercentcasino.gameparts.ChipStack;
import com.ninetyninepercentcasino.net.Connection;
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
    private BJClient client;

    public BJScreen(MainCasino game) {
        super(game);
    }

    @Override
    public void show() {
        firstRender = true;
        stage = new Stage(new ExtendViewport(1312, 738, 1312, 738));
        Gdx.input.setInputProcessor(stage);
        final float WORLD_WIDTH = stage.getViewport().getWorldWidth();
        final float WORLD_HEIGHT = stage.getViewport().getWorldHeight();

        background = new Texture("GameAssets/PokerTable.png");

        CardGroup playerHand = new CardGroup(true, true);
        CardGroup dealerHand = new CardGroup(false, true);

        playerHand.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 24);
        dealerHand.setPosition(WORLD_WIDTH / 2, 4 * WORLD_HEIGHT / 6);

        Table blackjackButtons = new Table();
        blackjackButtons.add(new HitButton());
        blackjackButtons.add(new StandButton());

        Table bottomUI = new Table();
        bottomUI.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 3.5f);
        bottomUI.add(blackjackButtons).padRight(WORLD_WIDTH / 16).padLeft(WORLD_WIDTH / 16).top().padBottom(230);

        ChipStack chips = new ChipStack();
        chips.addChip(new ChipActor(new Chip(1)));
        chips.addChip(new ChipActor(new Chip(5)));
        chips.addChip(new ChipActor(new Chip(10)));
        chips.debug();

        stage.addActor(chips);
        stage.addActor(bottomUI);
        stage.addActor(playerHand);
        stage.addActor(dealerHand);

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

//        try {
//            client = new BJClient(new Socket("127.0.0.1", 9925));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        client.start();
//        try {
//            client.message(new NetMessage(NetMessage.MessageType.INFO, "begin game."));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }


    }

    @Override
    public void render(float delta) {
        if(firstRender) {
            Gdx.graphics.requestRendering();
            firstRender = false;
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
}
