package com.ninetyninepercentcasino.bj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ninetyninepercentcasino.MainCasino;
import com.ninetyninepercentcasino.audio.SFXManager;
import com.ninetyninepercentcasino.game.*;
import com.ninetyninepercentcasino.game.buttons.*;
import com.ninetyninepercentcasino.net.*;
import com.ninetyninepercentcasino.screens.BJScreen;
import com.ninetyninepercentcasino.screens.CasinoScreen;
import com.ninetyninepercentcasino.text.LabelStyleGenerator;

import java.io.IOException;
import java.util.HashMap;

/**
 * This class contains all the actors required for a blackjack game
 * Also includes methods for changing the game state and handles DTOs coming in from the server
 * @author Grant Liang
 */
public class BJStage extends Stage {
	private CasinoScreen screen; //the screen that displays this stage
	private MainCasino game;
	private CardGroup playerHand; //holds the current hand the player is managing
	private CardGroup dealerHand;
	private CardGroup splitHands; //holds the hands that have been split and are waiting to be resolved
	private CardGroup resolvedHands; //holds all resolved hands
	private ChipGroup chips; //the chips displayed on screen
	private ChipGroupBet betChips;
	private ChipGroupBet insuredChips;

	private BetButton betButton;
	private HitButton hitButton;
	private SplitButton splitButton;
	private StandButton standButton;
	private DDButton doubleDownButton;
	private Table bjButtons;

	private Table betDisplays;
	private Label betDisplay; //will display to the user the value of all chips on the chip holders

	private Table chipSpawners;

	private BJClient client;

	/**
	 * initializes a new BJStage
	 * @param viewport the viewport to be used
	 */
	public BJStage(Viewport viewport) {
		super(viewport);
	}

	/**
	 * called by a BJScreen to update the game state
	 * @param update the DTO containing information about the game update
	 */
	public void handleDTO(DTO update) {
		//check the type of message and call the appropriate method
		if(update instanceof BJBetMessage){
			startBetPhase();
		}
		else if (update instanceof BJInsuranceMessage) {
			startInsurePhase();
		}
		else if (update instanceof BJCardUpdate) {
			if (((BJCardUpdate)update).isPlayerCard()) addPlayerCard(((BJCardUpdate)update).getCard());
			else {
				addDealerCard(((BJCardUpdate)update).getCard());
				if (((BJCardUpdate)update).isVisible()) revealDealerHand(); //only the first card for the dealer will be sent as visible, so revealing the hand will just reveal that first card
			}
		}
		else if (update instanceof BJAvailActionUpdate) {
			updateButtons(((BJAvailActionUpdate)update).getActions());
		}
		else if (update instanceof BJSplit) {
			handleSplit((BJSplit)update);
		}
		else if (update instanceof BJHandEnd) {
			revealDealerHand();
			endHand((BJHandEnd)update);
		}
		else if(update instanceof BJPayout){
			game.balance += ((BJPayout)(update)).getWinnings();
			showPlayAgainButton();
		}
		Gdx.graphics.requestRendering();
	}

	/**
	 * begins the betting phase before cards are drawn
	 */
	private void startBetPhase() {
		final float WORLD_WIDTH = getViewport().getWorldWidth();
		final float WORLD_HEIGHT = getViewport().getWorldHeight();

		//spawn the chips in
		chips = new ChipGroup(game.balance, 5, WORLD_WIDTH/2, WORLD_HEIGHT/1.8f, WORLD_WIDTH/2f, WORLD_HEIGHT/2.8f);
		addActor(chips);

		//addActor(chipSpawners);

		betButton = new BetButton();
		betButton.enable();

		LabelStyleGenerator labelStyleGenerator = new LabelStyleGenerator();
		betDisplay = new Label("", labelStyleGenerator.getLeagueGothicLabelStyle(260));

		betDisplays = new Table();
		betDisplays.add(betButton).padRight(WORLD_WIDTH/80);
		betDisplays.add(betDisplay).width(WORLD_HEIGHT/4);
		betDisplays.setPosition(WORLD_WIDTH/2, WORLD_HEIGHT/5.8f);
		addActor(betDisplays);
		betDisplays.toBack(); //send bet displays to the back, so they don't cover chips and make them irretrievable

	}

	/**
	 * updates the number displayed by the chip value calculator
	 * called by the BJScreen every render
	 */
	public void updateBetDisplay() {
		if (betDisplay != null) betDisplay.setText(chips.calculate());
	}

	/**
	 * sends a bet to the server
	 */
	public void sendBet() {
		betChips = new ChipGroupBet(chips.getHolders());
		game.balance -= betChips.calculate();
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
	 * sets up the player hand, dealer hand, buttons, and everything else needed for the BJ game
	 */
	private void setupGame() {
		final float WORLD_WIDTH = getViewport().getWorldWidth();
		final float WORLD_HEIGHT = getViewport().getWorldHeight();

		betDisplays.setVisible(false);

		playerHand = new CardGroup(true, true);
		dealerHand = new CardGroup(false, false);
		splitHands = new CardGroup(true, false);
		resolvedHands = new CardGroup(true, false);
		DeckActor deckActor = new DeckActor();

		bjButtons = new Table(); //will hold all the buttons
		hitButton = new HitButton();
		splitButton = new SplitButton();
		standButton = new StandButton();
		doubleDownButton = new DDButton();
		bjButtons.add(hitButton);
		bjButtons.add(standButton);
		bjButtons.add(splitButton);
		bjButtons.add(doubleDownButton);

		bjButtons.setPosition(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2.5f);

		Table upperTable = new Table(); //will hold the deck and dealer hand
		upperTable.add(deckActor).width(deckActor.getWidth()*2);
		upperTable.add(dealerHand).width(deckActor.getWidth()*2);
		upperTable.setPosition(WORLD_WIDTH / 2.4f, WORLD_HEIGHT / 1.55f);

		Table lowerTable = new Table(); //will hold all the player's cards
		lowerTable.setPosition(WORLD_WIDTH / 2, 0);
		lowerTable.add(resolvedHands).top().padRight(WORLD_WIDTH/16);
		lowerTable.add(playerHand).bottom();
		lowerTable.add(splitHands).top().padLeft(WORLD_WIDTH/16);

		addActor(upperTable);
		addActor(bjButtons);
		addActor(lowerTable);
	}

	/**
	 * begins the insurance phase of betting
	 */
	private void startInsurePhase() {
		final float WORLD_WIDTH = getViewport().getWorldWidth();
		final float WORLD_HEIGHT = getViewport().getWorldHeight();

		bjButtons.setVisible(false);
		InsureButton insureButton = new InsureButton();
		chips.addInsuranceHolders(5, WORLD_WIDTH/2, WORLD_HEIGHT/5f);
		insureButton.enable();

		betDisplays = new Table(); //reset the bet display
		betDisplays.add(insureButton).padRight(WORLD_WIDTH/80); //add on the insurance button
		betDisplays.add(betDisplay).width(WORLD_WIDTH/4);
		betDisplays.setPosition(WORLD_WIDTH/2, WORLD_HEIGHT/2.3f);
		betDisplays.setVisible(true);
		addActor(betDisplays);
		chips.toFront();

	}

	/**
	 * sends an insurance bet to the server
	 */
	public void sendInsure() {
		insuredChips = new ChipGroupBet(chips.getInsuranceHolders());
		game.balance -= insuredChips.calculate(); //subtract the amount bet from the balance
		BJInsuranceMessage insuranceBet = new BJInsuranceMessage(insuredChips.calculate());
		addActor(insuredChips);
		insuredChips.stowHolders();
		NetMessage message = new NetMessage(NetMessage.MessageType.INFO, insuranceBet);
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
	private void addPlayerCard(Card card) {
		SFXManager.playSlideSound();
		playerHand.addCard(card);
	}
	/**
	 * adds a Card to the dealer's hand
	 * the card is immediately shown on screen either face up or face down
	 * @param card the card to be added
	 */
	private void addDealerCard(Card card) {
		if (dealerHand.getCards().size() == 2) dealerHand.reveal();
		SFXManager.playSlideSound();
		dealerHand.addCard(card);
	}

	/**
	 * reveals all cards in the dealer's hand
	 */
	private void revealDealerHand() {
		dealerHand.reveal();
	}

	/**
	 * getter for the stage's viewport
	 * @return this stage's viewport
	 */
	@Override
	public Viewport getViewport() {
		return super.getViewport();
	}

	/**
	 * updates the BJ buttons given the available actions
	 * @param actions describes the available and unavailable actions
	 */
	private void updateButtons(HashMap<BJAction, Boolean> actions) {
		boolean handOver = true;
		if (actions.get(BJAction.HIT)) {
			hitButton.enable();
			handOver = false;
		}
		else hitButton.disable();
		if (actions.get(BJAction.STAND)) {
			standButton.enable();
			handOver = false;
		}
		else standButton.disable();
		if (actions.get(BJAction.SPLIT) && game.balance >= betChips.calculate()) { //only activates the split button if there is enough money remaining to split
			splitButton.enable();
			handOver = false;
		}
		else splitButton.disable();
		if (actions.get(BJAction.DOUBLE_DOWN) && game.balance >= betChips.calculate()) { //only activates the double down button if there is enough money remaining to split
			doubleDownButton.enable();
			handOver = false;
		}
		else doubleDownButton.disable();
		if (handOver) {
			if (!splitHands.getCards().isEmpty()) {
				for (Card card : playerHand.getCards()) {
					resolvedHands.addCard(card);
				}
				playerHand.clearCards();
				playerHand.addCard(splitHands.removeCard(0));
			}
		}
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
		if(!splitHands.getCards().isEmpty()) { //there are still hands waiting to be resolved
			for (Card card : playerHand.getCards()) {
				resolvedHands.addCard(card);
			}
			playerHand.clearCards();
			playerHand.addCard(splitHands.removeCard(0));
		}
		disableAllButtons();
	}
	/**
	 * performs the double down action in blackjack
	 * sends the action to the server
	 */
	public void doubleDown() {
		BJActionUpdate actionUpdate = new BJActionUpdate(BJAction.DOUBLE_DOWN);
		game.balance -= betChips.calculate();
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
	public void split() {
		BJActionUpdate actionUpdate = new BJActionUpdate(BJAction.SPLIT);
		game.balance -= betChips.calculate();
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
	private void handleSplit(BJSplit bjSplit) {
		splitHands.addCard(bjSplit.getHand2().getCard(0));
		playerHand.removeCard(bjSplit.getHand2().getCard(0));
	}
	/**
	 * disables each button
	 */
	private void disableAllButtons() {
		hitButton.disable();
		standButton.disable();
		splitButton.disable();
		doubleDownButton.disable();
	}

	private void endHand(BJHandEnd handEnd) {
		playerHand.hide();
		if (handEnd.getOutcome() == BJHandEnd.DEALER_WON) {
			betChips.floatAway(); //floats away all the chips that have been bet
		} else if (handEnd.getWinnings() > 0) {
			game.balance += handEnd.getWinnings();
		}
	}

	/**
	 * shows the play again button that can be clicked to start a new round
	 */
	private void showPlayAgainButton(){
		Button playAgainButton = new Button(new TextureRegionDrawable(new Texture("GameAssets/PlayAgainButton.png")));
		playAgainButton.setPosition(getViewport().getWorldWidth()/2f - playAgainButton.getWidth()/2, getViewport().getWorldHeight()/1.2f - playAgainButton.getHeight()/2);
		playAgainButton.addListener(new ChangeListener(){
			public void changed (ChangeEvent event, Actor actor) {
				game.setScreen(new BJScreen(game));
				screen.dispose();
			}
		});
		playAgainButton.addListener(new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				playAgainButton.setColor(65, 65, 65, 0.7f);; //fades the button slightly when the cursor enters
			}
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				playAgainButton.setColor(1, 1, 1, 1f); //resets the fade when the cursor exits the actor
			}
		});
		addActor(playAgainButton);
	}
	/**
	 * this method NEEDS TO BE CALLED to set the client of a BJStage if the stage is to communicate with server
	 * @param client the client of the stage that communicates with the server
	 */
	public void setClient(BJClient client) {
		this.client = client;
	}

	/**
	 * @param screen the screen that this stage is being drawn on
	 */
	public void setScreen(CasinoScreen screen) {
		this.screen = screen;
		game = (MainCasino)screen.getGame();
	}
}
