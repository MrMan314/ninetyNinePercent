package com.ninetyninepercentcasino.bj;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ninetyninepercentcasino.game.ChipGroupBet;
import com.ninetyninepercentcasino.game.buttons.*;
import com.ninetyninepercentcasino.game.CardGroup;
import com.ninetyninepercentcasino.game.ChipGroup;
import com.ninetyninepercentcasino.game.DeckActor;
import com.ninetyninepercentcasino.text.LabelStyleGenerator;
import com.ninetyninepercentcasino.audio.SFXManager;
import com.ninetyninepercentcasino.game.Card;
import com.ninetyninepercentcasino.net.*;

import java.io.IOException;
import java.util.HashMap;

/**
 * this class contains all the actors in a BJGame stage
 * also includes methods for changing the game state and handles DTOs coming in from the server
 * @author Grant Liang
 */
public class BJStage extends Stage {
	private CardGroup playerHand;
	private CardGroup dealerHand;
	private CardGroup splits;
	private DeckActor deckActor;
	private ChipGroup chips; //the chips displayed on screen
	private ChipGroupBet betChips;
	private ChipGroupBet insuredChips;
	private Table betDisplays;

	private BetButton betButton;
	private HitButton hitButton;
	private SplitButton splitButton;
	private StandButton standButton;
	private DDButton doubleDownButton;
	private Table bjButtons;
	private Label betDisplay;

	private BJClient client;

	/**
	 * initializes a new BJStage
	 * @param viewport the viewport to be used
	 */
	public BJStage(Viewport viewport){
		super(viewport);
	}

	/**
	 * called by a BJScreen to update the game state
	 * @param update the DTO containing information about the game update
	 */
	public void handleDTO(DTO update){
		if(update instanceof BJBetMessage){
			startBetPhase();
		}
		else if(update instanceof BJInsuranceRequest){
			startInsurePhase();
		}
		else if(update instanceof BJCardUpdate){
			if(((BJCardUpdate)update).isPlayerCard()) addPlayerCard(((BJCardUpdate)update).getCard());
			else {
				addDealerCard(((BJCardUpdate)update).getCard());
				if(((BJCardUpdate)update).isVisible()) revealDealerHand(); //only the first card for the dealer will be sent as visible, so revealing the hand will just reveal that first card
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
			endHand((BJHandEnd)update);
		}
	}

	/**
	 * begins the betting phase before cards are drawn
	 */
	private void startBetPhase(){
		final float WORLD_WIDTH = getViewport().getWorldWidth();
		final float WORLD_HEIGHT = getViewport().getWorldHeight();

		chips = new ChipGroup(1000, 5, WORLD_WIDTH/2, 0, WORLD_WIDTH/2f, WORLD_HEIGHT/3f);
		addActor(chips);

		betButton = new BetButton();
		betButton.enable();
		betButton.setPosition(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f);

		LabelStyleGenerator labelStyleGenerator = new LabelStyleGenerator();

		betDisplay = new Label("", labelStyleGenerator.getLeagueGothicLabelStyle(260));
		Pixmap labelColor = new Pixmap((int) betDisplay.getWidth(), (int) betDisplay.getHeight(), Pixmap.Format.RGB888);
		labelColor.setColor(Color.BLACK);
		labelColor.fill();
		betDisplay.getStyle().background = new Image(new Texture(labelColor)).getDrawable();

		betDisplays = new Table();
		betDisplays.add(betButton).padRight(WORLD_WIDTH/80);
		betDisplays.add(betDisplay).width(WORLD_HEIGHT/4);
		betDisplays.setPosition(WORLD_WIDTH/2, WORLD_HEIGHT/1.2f);
		addActor(betDisplays);
	}

	/**
	 * updates the number displayed by the chip value calculator
	 * called by the BJScreen every render
	 */
	public void updateBetDisplay(){
		if(betDisplay != null) betDisplay.setText(chips.calculate());
	}

	/**
	 * sends a bet to the server
	 */
	public void sendBet(){
		betChips = new ChipGroupBet(chips.getHolders());
		BJBetMessage betRequest = new BJBetMessage(betChips.calculate());
		addActor(betChips);
		betChips.stowHolders();
		NetMessage message = new NetMessage(NetMessage.MessageType.INFO, betRequest);
		try {
			client.message(message);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		setupGame();
	}

	/**
	 * sets up the player hand, dealer hand, buttons, and everything else needed for the BJ gaem
	 */
	private void setupGame() {
		final float WORLD_WIDTH = getViewport().getWorldWidth();
		final float WORLD_HEIGHT = getViewport().getWorldHeight();

		betDisplays.setVisible(false);

		playerHand = new CardGroup(true, true);
		dealerHand = new CardGroup(false, false);
		splits = new CardGroup(true, false);
		deckActor = new DeckActor();

		bjButtons = new Table();
		hitButton = new HitButton();
		splitButton = new SplitButton();
		standButton = new StandButton();
		doubleDownButton = new DDButton();
		bjButtons.add(hitButton);
		bjButtons.add(standButton);
		bjButtons.add(splitButton);
		bjButtons.add(doubleDownButton);

		bjButtons.setPosition(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2.3f);

		Table upperTable = new Table();
		upperTable.add(deckActor).spaceRight(100);
		upperTable.add(dealerHand);
		upperTable.setPosition(WORLD_WIDTH / 2f, WORLD_HEIGHT / 1.55f);

		Table lowerTable = new Table();
		lowerTable.setPosition(WORLD_WIDTH / 2, 0);
		lowerTable.add(playerHand).bottom();
		lowerTable.add(splits).bottom().padLeft(WORLD_WIDTH/16);

		addActor(upperTable);
		addActor(bjButtons);
		addActor(lowerTable);
	}

	/**
	 * begins the insurance phase of betting
	 */
	private void startInsurePhase(){
		final float WORLD_WIDTH = getViewport().getWorldWidth();
		final float WORLD_HEIGHT = getViewport().getWorldHeight();

		bjButtons.setVisible(false);
		InsureButton insureButton = new InsureButton();
		chips.addInsuranceHolders(5, WORLD_WIDTH/2, WORLD_HEIGHT/2);
		insureButton.enable();
		insureButton.setPosition(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2.3f);

		betDisplays = new Table();
		betDisplays.add(insureButton);
		betDisplays.add(insureButton).spaceRight(WORLD_WIDTH/4);
		betDisplays.add(betDisplay).spaceLeft(WORLD_WIDTH/4);
		betDisplays.setPosition(WORLD_WIDTH/2, WORLD_HEIGHT/1.5f);
		betDisplays.setVisible(true);

		addActor(betDisplays);
	}

	/**
	 * sends an insurance bet to the server
	 */
	public void sendInsure() {
		insuredChips = new ChipGroupBet(chips.getHolders());
		BJBetMessage betRequest = new BJBetMessage(insuredChips.calculate());
		addActor(insuredChips);
		insuredChips.stowHolders();
		NetMessage message = new NetMessage(NetMessage.MessageType.INFO, betRequest);
		try {
			client.message(message);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		betDisplays.setVisible(false);
		bjButtons.setVisible(true);
	}

	/**
	 * adds a Card to the player's hand
	 * the card is immediately displayed
	 * @param card the card to be added
	 */
	private void addPlayerCard(Card card){
		SFXManager.playSlideSound();
		playerHand.addCard(card);
	}
	/**
	 * adds a Card to the dealer's hand
	 * the card is immediately shown on screen either face up or face down
	 * @param card the card to be added
	 */
	private void addDealerCard(Card card){
		if(dealerHand.getHand().getCards().size() == 2) dealerHand.reveal();
		SFXManager.playSlideSound();
		dealerHand.addCard(card);
	}

	/**
	 * reveals all cards in the dealer's hand
	 */
	private void revealDealerHand(){
		dealerHand.reveal();
	}

	/**
	 * getter for the stage's viewport
	 * @return this stage's viewport
	 */
	@Override
	public Viewport getViewport(){
		return super.getViewport();
	}

	/**
	 * updates the BJ buttons given the available actions
	 * @param actions describes the available and unavailable actions
	 */
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

	/**
	 * performs the hit action in blackjack
	 * sends the action to the server
	 */
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
	/**
	 * performs the stand action in blackjack
	 * sends the action to the server
	 */
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
	/**
	 * performs the double down action in blackjack
	 * sends the action to the server
	 */
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
	/**
	 * performs the splitting action in blackjack
	 * sends the action to the server
	 */
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
	/**
	 * handles the server sending over a successful split
	 */
	private void handleSplit(BJSplit bjSplit){
		splits.addCard(bjSplit.getHand2().getCard(0));
		playerHand.removeCard(bjSplit.getHand1().getCard(0));
	}
	/**
	 * disables each button
	 */
	private void disableAllButtons(){
		hitButton.disable();
		standButton.disable();
		splitButton.disable();
		doubleDownButton.disable();
	}

	private void endHand(BJHandEnd handEnd){
		playerHand.hide();
		if(handEnd.getOutcome() != BJHandEnd.PLAYER_WON) betChips.floatAway();
	}
	/**
	 * this method NEEDS TO BE CALLED to set the client of a BJStage if the stage is to communicate with server
	 * @param client the client of the stage that communicates with the server
	 */
	public void setClient(BJClient client){
		this.client = client;
	}
}
