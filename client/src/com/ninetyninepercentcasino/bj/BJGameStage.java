package com.ninetyninepercentcasino.bj;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ninetyninepercentcasino.bj.bjbuttons.HitButton;
import com.ninetyninepercentcasino.bj.bjbuttons.InsureButton;
import com.ninetyninepercentcasino.bj.bjbuttons.SplitButton;
import com.ninetyninepercentcasino.bj.bjbuttons.StandButton;
import com.ninetyninepercentcasino.game.gameparts.Card;
import com.ninetyninepercentcasino.game.gameparts.Chip;
import com.ninetyninepercentcasino.gameparts.CardActor;
import com.ninetyninepercentcasino.gameparts.CardGroup;
import com.ninetyninepercentcasino.gameparts.ChipActor;
import com.ninetyninepercentcasino.gameparts.ChipStack;

import java.util.Scanner;

/**
 * this class contains all the actors in a BJGame stage
 * also includes methods for changing the game state
 */
public class BJGameStage extends Stage {
    private CardGroup playerHand;
    private CardGroup dealerHand;
    private ChipStack whiteChips, redChips, blueChips, greenChips, blackChips;

    public BJGameStage(Viewport viewport){
        super(viewport);
        final float WORLD_WIDTH = getViewport().getWorldWidth();
        final float WORLD_HEIGHT = getViewport().getWorldHeight();

        playerHand = new CardGroup(true, true);
        dealerHand = new CardGroup(false, true);

        playerHand.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 24);
        dealerHand.setPosition(WORLD_WIDTH / 2, 4 * WORLD_HEIGHT / 6);

        Table bjButtons = new Table();
        bjButtons.add(new HitButton());
        bjButtons.add(new StandButton());
        bjButtons.add(new InsureButton());
        bjButtons.add(new SplitButton());

        Table bottomUI = new Table();
        bottomUI.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT/7);
        bottomUI.add(bjButtons).padRight(WORLD_WIDTH / 16).padLeft(WORLD_WIDTH / 16).bottom();

        ChipStack chips = new ChipStack();
        chips.addChip(new ChipActor(new Chip(1)));
        chips.addChip(new ChipActor(new Chip(1)));
        chips.addChip(new ChipActor(new Chip(1)));
        chips.debug();
        addActor(chips);
        addActor(bottomUI);
        addActor(playerHand);
        addActor(dealerHand);
    }

    public double placeBet(){
        Scanner input = new Scanner(System.in);
        System.out.println("Place bet:");
        return input.nextDouble();
    }
    public void addPlayerCard(Card card){
        playerHand.addCard(card);
    }
    public void addDealerCard(Card card){
        dealerHand.addCard(card);
    }
    public void revealDealerHand(){
        dealerHand.reveal();
    }
    @Override
    public Viewport getViewport(){
        return super.getViewport();
    }
    public CardGroup getPlayerHand(){
        return playerHand;
    }
    public void update(){

    }
}
