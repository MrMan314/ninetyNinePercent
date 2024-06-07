package com.ninetyninepercentcasino.bj;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ninetyninepercentcasino.bj.bjbuttons.*;
import com.ninetyninepercentcasino.game.Text;
import com.ninetyninepercentcasino.game.SFXManager;
import com.ninetyninepercentcasino.game.gameparts.Card;
import com.ninetyninepercentcasino.gameparts.*;
import com.ninetyninepercentcasino.net.*;

import java.io.IOException;
import java.util.HashMap;

/**
 * this class contains all the actors in a BJGame stage
 * also includes methods for changing the game state
 */
public class BJStage extends Stage {
	private CardGroup playerHand;
	private CardGroup dealerHand;
	private CardGroup splits;
	private DeckActor deckActor;
	private ChipGroup chips;
	private Table betDisplays;

	private BetButton betButton;
	private HitButton hitButton;
	private InsureButton insureButton;
	private SplitButton splitButton;
	private StandButton standButton;
	private DDButton doubleDownButton;
	private Label betDisplay;
	private BitmapFont font;

	private BJClient client;

	public BJStage(Viewport viewport){
		super(viewport);
	}
	public void handleDTO(DTO update){
		if(update instanceof BJBetRequest){
			startBetPhase();
		}
		else if(update instanceof BJInsuranceRequest){
			startInsurePhase();
		}
		else if(update instanceof BJCardUpdate){
			if(((BJCardUpdate)update).isPlayerCard()) addPlayerCard(((BJCardUpdate)update).getCard());
			else {
				addDealerCard(((BJCardUpdate)update).getCard());
				if(((BJCardUpdate)update).isVisible()) revealDealerHand();
			}
		}
		else if(update instanceof BJAvailActionUpdate){
			updateButtons(((BJAvailActionUpdate)update).getActions());
		}
		else if(update instanceof BJSplit){
			handleSplit((BJSplit)update);
		}
		else if(update instanceof BJHandEnd){
			revealDealerHand();
			endHand();
		}
	}
	private void startBetPhase(){
		final float WORLD_WIDTH = getViewport().getWorldWidth();
		final float WORLD_HEIGHT = getViewport().getWorldHeight();

		chips = new ChipGroup(1000, 5, 5, 5, 5, 400);
		addActor(chips);

		betButton = new BetButton();
		betButton.enable();
		betButton.setPosition(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f);

		betDisplay = new Label("", Text.getLeagueGothicLabelStyle(260));

		betDisplays = new Table();
		betDisplays.setFillParent(true);
		betDisplays.add(betButton).bottom();
		betDisplays.add(betDisplay).bottom().spaceLeft(WORLD_WIDTH/6);
		betDisplays.setZIndex(0);
		addActor(betDisplays);
	}
	public void updateBetDisplay(){
		if(betDisplay != null) betDisplay.setText(chips.calculate());
	}
	public void sendBet(){
		BJBetRequest betRequest = new BJBetRequest(chips.calculate());
		NetMessage message = new NetMessage(NetMessage.MessageType.INFO, betRequest);
		try {
			client.message(message);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		chips.disableChipsHeld();
		setupGame();
	}
	private void setupGame() {
		final float WORLD_WIDTH = getViewport().getWorldWidth();
		final float WORLD_HEIGHT = getViewport().getWorldHeight();

		betDisplays.setVisible(false);

		playerHand = new CardGroup(true, true);
		dealerHand = new CardGroup(false, false);
		splits = new CardGroup(true, false);
		deckActor = new DeckActor();

		Table bjButtons = new Table();
		hitButton = new HitButton();
		splitButton = new SplitButton();
		standButton = new StandButton();
		doubleDownButton = new DDButton();
		bjButtons.add(hitButton);
		bjButtons.add(standButton);
		bjButtons.add(splitButton);
		bjButtons.add(doubleDownButton);

		Table bottomUI = new Table();
		bottomUI.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
		bottomUI.add(bjButtons).bottom();

		Table upperTable = new Table();
		upperTable.add(deckActor).padRight(100);
		upperTable.add(dealerHand);
		upperTable.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 1.5f);

		Table root = new Table();
		root.setPosition(WORLD_WIDTH / 2, 0);
		root.add(playerHand).bottom();
		root.add(splits).bottom().padLeft(WORLD_WIDTH/16);

		addActor(upperTable);
		addActor(bottomUI);
		addActor(root);
	}
	private void startInsurePhase(){
		final float WORLD_WIDTH = getViewport().getWorldWidth();
		final float WORLD_HEIGHT = getViewport().getWorldHeight();

		chips.enableChipsHeld();
		betButton.enable();
		betButton.setPosition(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f);
		Label.LabelStyle labelStyle = new Label.LabelStyle();
		labelStyle.font = font;
		betDisplay = new Label("", labelStyle);

		betDisplays = new Table();
		betDisplays.setFillParent(true);
		betDisplays.add(betButton).bottom();
		betDisplays.add(betDisplay).bottom().spaceLeft(WORLD_WIDTH/6);
		betDisplays.setZIndex(0);
		betDisplays.setVisible(true);
		addActor(betDisplays);

	}
	public void sendInsure() {
		BJBetRequest betRequest = new BJBetRequest(chips.calculate());
		NetMessage message = new NetMessage(NetMessage.MessageType.INFO, betRequest);
		try {
			client.message(message);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		chips.disableChipsHeld();
	}
	private void addPlayerCard(Card card){
		SFXManager.playSlideSound();
		playerHand.addCard(card);
	}
	private void addDealerCard(Card card){
		SFXManager.playSlideSound();
		dealerHand.addCard(card);
	}
	private void revealDealerHand(){
		dealerHand.reveal();
	}
	@Override
	public Viewport getViewport(){
		return super.getViewport();
	}
	public CardGroup getPlayerHand(){
		return playerHand;
	}
	private void updateButtons(HashMap<BJAction, Boolean> actions){
		if(actions.get(BJAction.HIT)) hitButton.enable();
		else hitButton.disable();
		if(actions.get(BJAction.STAND)) standButton.enable();
		else standButton.disable();
		if(actions.get(BJAction.SPLIT)) splitButton.enable();
		else splitButton.disable();
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
		BJActionUpdate actionUpdate = new BJActionUpdate(BJAction.SPLIT);
		NetMessage message = new NetMessage(NetMessage.MessageType.INFO, actionUpdate);
		try {
			client.message(message);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		disableAllButtons();
	}
	private void handleSplit(BJSplit bjSplit){
		splits.addCard(bjSplit.getHand2().getCard(0));
		playerHand.removeCard(bjSplit.getHand1().getCard(0));
	}
	private void disableAllButtons(){
		hitButton.disable();
		standButton.disable();
		splitButton.disable();
		doubleDownButton.disable();
	}
	private void endHand(){
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
