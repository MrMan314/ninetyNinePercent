package com.ninetyninepercentcasino.bj;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ninetyninepercentcasino.bj.bjbuttons.*;
import com.ninetyninepercentcasino.game.SFXManager;
import com.ninetyninepercentcasino.game.gameparts.Card;
import com.ninetyninepercentcasino.gameparts.*;
import com.ninetyninepercentcasino.net.BJAction;
import com.ninetyninepercentcasino.net.BJActionUpdate;
import com.ninetyninepercentcasino.net.BJBetRequest;
import com.ninetyninepercentcasino.net.NetMessage;

import java.io.IOException;
import java.util.HashMap;

/**
 * this class contains all the actors in a BJGame stage
 * also includes methods for changing the game state
 */
public class BJGameStage extends Stage {
	private CardGroup playerHand;
	private CardGroup dealerHand;
	private CardGroup splits;
	private DeckActor deckActor;
	private ChipGroup chips;

	private HitButton hitButton;
	private InsureButton insureButton;
	private SplitButton splitButton;
	private StandButton standButton;
	private DDButton doubleDownButton;

	private BJClient client;

	public BJGameStage(Viewport viewport){
		super(viewport);
		final float WORLD_WIDTH = getViewport().getWorldWidth();
		final float WORLD_HEIGHT = getViewport().getWorldHeight();
		chips = new ChipGroup(5, 5, 5, 5, 5, 5);
		addActor(chips);
	}
	public void startBetPhase(){
		final float WORLD_WIDTH = getViewport().getWorldWidth();
		final float WORLD_HEIGHT = getViewport().getWorldHeight();

		BetButton betButton = new BetButton();
		betButton.enable();
		betButton.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 2f);
		addActor(betButton);
	}
	public void sendBet(){
		BJBetRequest betRequest = new BJBetRequest(chips.calculate());
		NetMessage message = new NetMessage(NetMessage.MessageType.INFO, betRequest);
		try {
			client.message(message);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		setupGame();
	}
	public void setupGame(){
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

		Table upperTable = new Table();
		upperTable.add(deckActor).padRight(100);
		upperTable.add(dealerHand);
		upperTable.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 1.2f);

		Table root = new Table();
		root.setPosition(WORLD_WIDTH / 2, 0);
		root.add(playerHand).bottom();
		root.add(splits).bottom();

		addActor(upperTable);
		addActor(bottomUI);
		addActor(root);
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
	 * this method NEEDS TO BE CALLED to set the client of a BJStage if the stage is to communicate with server
	 * @param client the client of the stage that communicates with the server
	 */
	public void setClient(BJClient client){
		this.client = client;
	}
}
