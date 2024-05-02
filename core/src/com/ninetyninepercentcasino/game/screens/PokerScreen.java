package com.ninetyninepercentcasino.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.ninetyninepercentcasino.game.MainCasino;
import com.ninetyninepercentcasino.game.gameparts.CardActor;
import com.ninetyninepercentcasino.game.gameparts.Deck;
import com.ninetyninepercentcasino.game.gameparts.Hand;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.Input;

/**
 * Screen that renders poker screen
 * @author Grant Liang
 */
public class PokerScreen extends CasinoScreen {
    private Texture background;

    public PokerScreen(MainCasino game) {
        super(game);
    }

    @Override
    public void show() {
        stage = new Stage(new ExtendViewport(1312, 738, 1312, 738));
        Gdx.input.setInputProcessor(stage);
        final float WORLD_WIDTH = stage.getViewport().getWorldWidth();
        final float WORLD_HEIGHT = stage.getViewport().getWorldHeight();

        background = new Texture("PokerAssets/PokerTable.png");
        Hand playerHand = new Hand(true, true);
        Deck deck = new Deck();
        deck.shuffle();
        playerHand.drawCard(deck);
        playerHand.drawCard(deck);

        playerHand.setX(WORLD_WIDTH / 2);
        playerHand.setY(200);
        playerHand.center();
        playerHand.top();

        Table pokerTable = new Table();
        pokerTable.setX(WORLD_WIDTH / 2);
        pokerTable.setY(WORLD_HEIGHT / 2);
        for (int i = 0; i < 5; i++) {
            pokerTable.add(new CardActor(deck.drawCard(), false, true)).space(10);
        }
        pokerTable.add(deck).padLeft(200);

        Table pokerButtons = new Table();


        stage.addActor(pokerTable);
        stage.addActor(playerHand);

        stage.addCaptureListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.ESCAPE) {
                    game.setScreen(new MainMenu(game));
                    return true;
                }
//                float distance = 100f;
//                if(keycode == Input.Keys.W){
//                    stage.getCamera().translate(0, distance, 0);
//                    return true;
//                }
//                if(keycode == Input.Keys.A){
//                    stage.getCamera().translate(-distance, 0, 0);
//                    return true;
//                }
//                if(keycode == Input.Keys.S){
//                    stage.getCamera().translate(0, -distance, 0);
//                    return true;
//                }
//                if(keycode == Input.Keys.D){
//                    stage.getCamera().translate(distance, 0, 0);
//                    return true;
//                }
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
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1f);
        stage.getBatch().begin();
        stage.getBatch().draw(background, -((1920-stage.getViewport().getWorldWidth())/2), -((1080-stage.getViewport().getWorldHeight())/2));
        stage.getBatch().end();
        stage.draw();
        stage.act();
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
        stage.dispose();
        background.dispose();
    }
}
