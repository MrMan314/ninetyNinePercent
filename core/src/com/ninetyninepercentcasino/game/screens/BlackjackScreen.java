package com.ninetyninepercentcasino.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.ninetyninepercentcasino.game.MainCasino;
import com.ninetyninepercentcasino.game.blackjack.BlackjackGame;
import com.ninetyninepercentcasino.game.blackjack.BlackjackPlayer;
import com.ninetyninepercentcasino.game.blackjack.blackjackbuttons.HitButton;
import com.ninetyninepercentcasino.game.blackjack.blackjackbuttons.StandButton;
import com.ninetyninepercentcasino.game.gameparts.CardGroup;

/**
 * Screen that renders a Blackjack game
 * @author Grant Liang
 */
public class BlackjackScreen extends CasinoScreen {
    private Texture background;
    private boolean firstRender;

    public BlackjackScreen(MainCasino game) {
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

        BlackjackPlayer player = new BlackjackPlayer(true);
        BlackjackGame blackjackGame = new BlackjackGame(player);
        CardGroup playerHand = player.getCardGroup();
        CardGroup dealerHand = blackjackGame.getDealer().getCardGroup();

        dealerHand.setPosition(WORLD_WIDTH/2, 4 * WORLD_HEIGHT/6);
        playerHand.setPosition(WORLD_WIDTH/2, WORLD_HEIGHT/24);
        Table blackjackButtons = new Table();
        blackjackButtons.add(new HitButton(blackjackGame));
        blackjackButtons.add(new StandButton(blackjackGame));

        Table bottomUI = new Table();
        bottomUI.setPosition(WORLD_WIDTH/2, WORLD_HEIGHT/3.5f);
        bottomUI.add(blackjackButtons).padRight(WORLD_WIDTH/16).padLeft(WORLD_WIDTH/16).top().padBottom(230);

        blackjackGame.playRound();

        stage.addActor(bottomUI);
        stage.addActor(playerHand);
        stage.addActor(dealerHand);

        stage.addCaptureListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.ESCAPE) {
                    game.setScreen(new MainMenu(game));
                    return true;
                }
                else if(keycode == Input.Keys.SPACE){
                    blackjackGame.playRound();
                }
                return false;
            }
        });
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
