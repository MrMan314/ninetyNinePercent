package com.ninetyninepercentcasino.bj;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ninetyninepercentcasino.bj.bjbuttons.*;
import com.ninetyninepercentcasino.game.SFXManager;
import com.ninetyninepercentcasino.game.gameparts.Card;
import com.ninetyninepercentcasino.game.gameparts.Chip;
import com.ninetyninepercentcasino.gameparts.CardGroup;
import com.ninetyninepercentcasino.gameparts.ChipActor;
import com.ninetyninepercentcasino.gameparts.ChipStack;
import com.ninetyninepercentcasino.gameparts.DeckActor;
import com.ninetyninepercentcasino.net.BJAction;
import com.ninetyninepercentcasino.net.BJActionUpdate;
import com.ninetyninepercentcasino.net.BJAvailActionUpdate;
import com.ninetyninepercentcasino.net.NetMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * this class contains all the actors in a BJGame stage
 * also includes methods for changing the game state
 */
public class BJGameStage extends Stage {
    private CardGroup playerHand;
    private CardGroup dealerHand;
    private CardGroup splits;
    private DeckActor deckActor;
    private HitButton hitButton;
    private InsureButton insureButton;
    private SplitButton splitButton;
    private StandButton standButton;
    private DDButton doubleDownButton;
    private BJClient client;
    private ChipStack whiteChips, redChips, blueChips, greenChips, blackChips;

    public BJGameStage(Viewport viewport){
        super(viewport);
        final float WORLD_WIDTH = getViewport().getWorldWidth();
        final float WORLD_HEIGHT = getViewport().getWorldHeight();

        playerHand = new CardGroup(true, true);
        dealerHand = new CardGroup(false, false);
        splits = new CardGroup(true, false);
        deckActor = new DeckActor();

        playerHand.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 24);
        dealerHand.setPosition(WORLD_WIDTH / 2, 4 * WORLD_HEIGHT / 6);

        Table bjButtons = new Table();
        hitButton = new HitButton();
        insureButton = new InsureButton();
        splitButton = new SplitButton();
        standButton = new StandButton();
        doubleDownButton = new DDButton();
        bjButtons.add(hitButton);
        bjButtons.add(standButton);
        bjButtons.add(insureButton);
        bjButtons.add(splitButton);
        bjButtons.add(doubleDownButton);

        Table bottomUI = new Table();
        bottomUI.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT/2);
        bottomUI.add(bjButtons).bottom();

        ChipStack chips = new ChipStack();
        chips.addChip(new ChipActor(new Chip(1)));
        chips.addChip(new ChipActor(new Chip(1)));
        chips.addChip(new ChipActor(new Chip(1)));
        chips.debug();

        Table upperTable = new Table();
        upperTable.add(deckActor).padRight(100);
        upperTable.add(dealerHand);
        upperTable.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 1.2f);

        Table root = new Table();
        root.setPosition(WORLD_WIDTH / 2, 0);
        root.add(playerHand).bottom();
        root.add(splits).bottom();
        root.debug();

        addActor(upperTable);
        addActor(bottomUI);
        addActor(chips);
        addActor(root);
    }

    public double placeBet(){
        Scanner input = new Scanner(System.in);
        System.out.println("Place bet:");
        return input.nextDouble();
    }
    public void addPlayerCard(Card card){
        SFXManager.playSlideSound();
        playerHand.addCard(card);
    }
    public void addDealerCard(Card card){
        SFXManager.playSlideSound();
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
    public void updateButtons(HashMap<BJAction, Boolean> actions){
        if(actions.get(BJAction.HIT)) hitButton.enable();
        else hitButton.disable();
        if(actions.get(BJAction.STAND)) standButton.enable();
        else standButton.disable();
        if(actions.get(BJAction.SPLIT)) splitButton.enable();
        else splitButton.disable();
        if(actions.get(BJAction.INSURANCE)) insureButton.enable();
        else insureButton.disable();
        if(actions.get(BJAction.DOUBLE_DOWN)) doubleDownButton.enable();
        else doubleDownButton.disable();
    }
    public void hit() {
        BJActionUpdate actionUpdate = new BJActionUpdate(BJAction.HIT);
        NetMessage message = new NetMessage(NetMessage.MessageType.INFO, actionUpdate);
        try {
            client.message(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        disableAllButtons();
    }
    public void stand() {
        BJActionUpdate actionUpdate = new BJActionUpdate(BJAction.STAND);
        NetMessage message = new NetMessage(NetMessage.MessageType.INFO, actionUpdate);
        try {
            client.message(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        disableAllButtons();
    }
    public void doubleDown(){
        BJActionUpdate actionUpdate = new BJActionUpdate(BJAction.DOUBLE_DOWN);
        NetMessage message = new NetMessage(NetMessage.MessageType.INFO, actionUpdate);
        try {
            client.message(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        disableAllButtons();
    }
    public void split(){
        splits.addCard(playerHand.getHand().getCard(0));
        playerHand.removeCard(playerHand.getHand().getCard(0));
        BJActionUpdate actionUpdate = new BJActionUpdate(BJAction.SPLIT);
        NetMessage message = new NetMessage(NetMessage.MessageType.INFO, actionUpdate);
        try {
            client.message(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        disableAllButtons();
    }
    private void disableAllButtons(){
        hitButton.disable();
        standButton.disable();
        splitButton.disable();
        insureButton.disable();
        doubleDownButton.disable();
    }
    public void endHand(){
        playerHand.hide();
    }
    /**
     * this method NEEDS TO BE CALLED to set the client of a stage if the stage is to communicate with server
     * @param client the client of the stage that communicates with the server
     */
    public void setClient(BJClient client){
        this.client = client;
    }
}
