package com.ninetyninepercentcasino.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.ninetyninepercentcasino.game.MainCasino;
import com.ninetyninepercentcasino.game.gameparts.CardActor;
import com.ninetyninepercentcasino.game.gameparts.Deck;
import com.ninetyninepercentcasino.game.gameparts.Hand;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.Input;
import com.ninetyninepercentcasino.game.poker.PokerGame;
import com.ninetyninepercentcasino.game.poker.PokerPlayer;
import com.ninetyninepercentcasino.game.poker.pokerbuttons.*;

import java.util.ArrayList;

/**
 * Screen that renders poker screen
 * @author Grant Liang
 */
public class PokerScreen extends CasinoScreen {
    PokerPlayer localPlayer = new PokerPlayer();
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
        ArrayList<PokerPlayer> players = new ArrayList<>();
        players.add(localPlayer);
        players.add(new PokerPlayer());
        players.add(new PokerPlayer());

        PokerGame pokerGame = new PokerGame(players);
        pokerGame.setupRound();

        Deck deck = pokerGame.getDeck();

        background = new Texture("PokerAssets/PokerTable.png");
        Hand localHand = localPlayer.getHand();

        Table pokerTable = new Table();
        pokerTable.setX(WORLD_WIDTH / 2);
        pokerTable.setY(WORLD_HEIGHT / 2);
        for(int i = 0; i < pokerGame.communityCards.size(); i++){
            pokerTable.add(new CardActor(pokerGame.communityCards.get(i), false, true)).pad(6);
        }
        pokerTable.add(deck).padLeft(200);

        Table pokerButtons = new Table();
        pokerButtons.add(new RaiseButton(localPlayer));
        pokerButtons.add(new CallButton(localPlayer));
        pokerButtons.add(new FoldButton(localPlayer));

        Table bottomUI = new Table();
        bottomUI.setPosition(WORLD_WIDTH/2, 0);
        bottomUI.debug();
        bottomUI.add(pokerButtons).padRight(WORLD_WIDTH/16).padLeft(WORLD_WIDTH/16).top().padBottom(230);
        bottomUI.add(localHand).padRight(WORLD_WIDTH/16);

        stage.addActor(pokerTable);
        stage.addActor(bottomUI);

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
        Gdx.graphics.requestRendering();
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
