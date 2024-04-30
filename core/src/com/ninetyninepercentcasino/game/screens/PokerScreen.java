package com.ninetyninepercentcasino.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ninetyninepercentcasino.game.MainCasino;
import com.ninetyninepercentcasino.game.gameparts.CardActor;
import com.ninetyninepercentcasino.game.gameparts.Deck;
import com.ninetyninepercentcasino.game.gameparts.Hand;

/**
 * Screen that renders poker screen
 * @author Grant Liang
 */
public class PokerScreen extends CasinoScreen {
    private Texture pokerTable;

    public PokerScreen(MainCasino game) {
        super(game);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        pokerTable = new Texture("PokerAssets/PokerTable.png");
        Hand playerHand = new Hand();
        Hand computerHand = new Hand();
        Deck deck = new Deck();
        deck.shuffle();
        deck.setX(screenWidth/2);
        deck.setY(screenHeight/2);
        playerHand.addCard(new CardActor(deck.drawCard(), true));
        playerHand.addCard(new CardActor(deck.drawCard(), true));
        computerHand.addCard(new CardActor(deck.drawCard(), true));
        computerHand.addCard(new CardActor(deck.drawCard(), true));

        computerHand.setX(440);
        computerHand.setY(400);
        computerHand.debug();
        computerHand.center();
        playerHand.debug();
        playerHand.setX(440);
        playerHand.setY(200);
        playerHand.center();
        playerHand.top();

        stage.addActor(deck);
        stage.addActor(playerHand);
        stage.addActor(computerHand);

        stage.addCaptureListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == 111) { //key pressed was the escape key (111 is keycode for ESCAPE)
                    game.setScreen(new MainMenu(game));
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1f);
        stage.getBatch().begin();
        stage.getBatch().draw(pokerTable, 0, 0);
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

    }
}
