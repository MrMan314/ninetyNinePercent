package com.ninetyninepercentcasino.game.bj;

import com.ninetyninepercentcasino.game.Card;
import com.ninetyninepercentcasino.game.Deck;
import com.ninetyninepercentcasino.game.Hand;
import com.ninetyninepercentcasino.net.*;

import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Stack;

/**
 * Runs logic for a blackjack game and sends messages to client as needed
 * @author Grant Liang
 */
public class BJGame extends Thread {
	private static final int PLAYER_WON = 0;
	private static final int DEALER_WON = 1;
	private static final int TIE = 3;
	private static final int PLAYER_BLACKJACK = 4;
	private static final long PAUSE_TIME = 999; //pause time in between card draws, in milliseconds

	private final BJPlayer player;
	private BJDealer dealer;
	private Stack<BJHand> hands; //keeps track of the hands remaining to play
	private Stack<BJHand> resolved; //keeps track of all the resolved hands

	private final BJSynchronizer bjSynchronizer; //syc
	private int firstBet;
	private int insuranceBet;
	private BJAction action;

	/**
	 * initializes a new blackjack game
	 * @param player the player of the game, who will play against the dealer
	 */
	public BJGame(BJPlayer player) {
		this.player = player;
		hands = new Stack<>();
		resolved = new Stack<>();
		bjSynchronizer = new BJSynchronizer();
	}
	/**
	 * main driver for a blackjack game
	 */
	public void run() {
		getInitialBet();
		Deck deck = new Deck();
		deck.shuffle();

		dealer = new BJDealer(deck);
		//drawCardUpdate(new Card(1, 1), true, false);
		//dealer.addCard(new Card(1, 1)); //TODO remove
		drawCardUpdate(dealer.drawCard(), true, false);
		drawCardUpdate(dealer.drawCard(), false, false);

		BJHand firstHand = new BJHand(player);
		firstHand.setBet(firstBet);
		hands.push(firstHand);

		if (dealer.hasVisibleAce()) getInsurance(); //get insurance bet if the dealer has a visible ace

		drawCardUpdate(firstHand.drawCard(deck), true, true);
		//drawCardUpdate(firstHand.addCard(firstHand.getCard(0)), true, true);
		drawCardUpdate(firstHand.drawCard(deck), true, true);

		while (!hands.isEmpty()) {
			BJHand currentHand = hands.peek();
			HashMap<BJAction, Boolean> availableActions = currentHand.getOptions();
			boolean handOver = true;
			for (Boolean available : availableActions.values()) {
				if (available) {
					handOver = false;
					break;
				}
			}
			sendOptions(availableActions, handOver);
			if (handOver) {
				resolved.push(hands.pop());
			}
			else {
				switch(action) { //simulate the action that the user selected
					case HIT:
						drawCardUpdate(currentHand.drawCard(deck), true, true); //draw a card for the hit
						break;
					case STAND:
						resolved.push(hands.pop()); //resolve the current hand, transferring it to the resolved stack
						break;
					case SPLIT:
						//split the hand into two hands
						BJHand hand1 = new BJHand(player, currentHand.getCard(0));
						BJHand hand2 = new BJHand(player, currentHand.getCard(1));
						hand1.setBet(currentHand.getAmountBet());
						hand2.setBet(currentHand.getAmountBet());
						//remove the current hand from the stack and add the two new ones generated from the split
						hands.pop();
						hands.push(hand2);
						hands.push(hand1);
						signalSplit(new Hand(hand1.getCard(0)), new Hand(hand2.getCard(0)));
						break;
					case DOUBLE_DOWN:
						drawCardUpdate(currentHand.drawCard(deck), true, true);
						currentHand.doubleBet();
						resolved.push(hands.pop()); //resolve the hand because the last card for that hand has been drawn
						break;
				}
			}
		}
		actDealer();
		while (!resolved.isEmpty()) { //go through each finished hand
			BJHand currentHand = resolved.pop();
			int outcome = findWinner(currentHand, dealer); //calculate the outcome of the hand
			int winnings = 0; //total amount the player wins + the amount initially bet
			switch(outcome) {
				case PLAYER_BLACKJACK:
					player.addBalance((int) (currentHand.getAmountBet()*2.5));
					winnings = (int) (currentHand.getAmountBet()*2.5);
					break;
				case PLAYER_WON:
					player.addBalance(currentHand.getAmountBet()*2);
					winnings = currentHand.getAmountBet()*2;
					break;
				case TIE:
					player.addBalance(currentHand.getAmountBet());
					winnings = currentHand.getAmountBet();
					break;
				case DEALER_WON:
					break;
			}
			sendHandEnd(outcome, winnings); //inform the client of the result of the hand
		}
		if (dealer.getNumCards() == 2 && dealer.hasVisibleAce()) {
			player.addBalance(insuranceBet*3);
		}
		payoutPlayer(insuranceBet*3);
	}
	/**
	 * simulates the action of the dealer
	 */
	private void actDealer() {
		while (dealer.getScore() < 17) {
			drawCardUpdate(dealer.drawCard(), true, false);
		}
	}

	/**
	 * sends bet message to client and waits until a bet has been placed
	 */
	private void getInitialBet() {
		NetMessage getBet = new NetMessage(NetMessage.MessageType.INFO, new BJBetMessage());
		try {
			player.getConnection().message(getBet);
		} catch (SocketException e) {
			try {
				player.getConnection().finish();
			} catch (IOException f) {
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		synchronized (bjSynchronizer) {
			try {
				bjSynchronizer.wait(); //waits until the client returns the amount bet
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * sends an insurance request to the player and waits until a bet has been received
	 */
	private void getInsurance() {
		NetMessage insuranceMessage = new NetMessage(NetMessage.MessageType.INFO, new BJInsuranceMessage());
		try {
			player.getConnection().message(insuranceMessage);
		} catch (SocketException e) {
			try {
				player.getConnection().finish();
			} catch (IOException f) {
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		synchronized (bjSynchronizer) {
			try {
				bjSynchronizer.wait(); //waits until the client returns the amount bet
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * determines the winner of a blackjack game between the dealer and a player hand
	 * @param playerHand the hand of the player to be compared with the dealer
	 * @param dealer the dealer's hand
	 * @return 0 if the player won, 1 if the dealer won, 2 for a tie
	 */
	private int findWinner(BJHand playerHand, BJDealer dealer) {
		int playerScore = playerHand.getScore();
		int dealerScore = dealer.getScore();
		if (playerScore == 21 && dealerScore == 21) {
			if (playerHand.getCards().size() == 2 && dealer.getNumCards() == 2) return TIE;
			else if (playerHand.getCards().size() == 2) return PLAYER_BLACKJACK;
			else return DEALER_WON;
		}
		else if ((playerScore > 21 && dealerScore > 21)) return DEALER_WON;
		else if (playerScore == dealerScore) return TIE;
		else if (playerScore > 21) return DEALER_WON;
		else if (dealerScore > 21) return PLAYER_WON;
		else if (playerScore > dealerScore) return PLAYER_WON;
		else return DEALER_WON;
	}

	/**
	 * called when the client needs to be updated about a card that was drawn
	 */
	private void drawCardUpdate(Card card, boolean visible, boolean isPlayerCard) {
		NetMessage cardUpdate = new NetMessage(NetMessage.MessageType.INFO, new BJCardUpdate(card, visible, isPlayerCard));
		try {
			player.getConnection().message(cardUpdate);
		} catch (SocketException e) {
			try {
				player.getConnection().finish();
			} catch (IOException f) {
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		pause();
	}

	/**
	 * sends the available BJActions to the player
	 * it is guaranteed that after this method is called setAction() will be called to resume the thread
	 * @param availableActions the available actions
	 */
	private void sendOptions(HashMap<BJAction, Boolean> availableActions, boolean handOver) {
		BJAvailActionUpdate update = new BJAvailActionUpdate(availableActions);
		NetMessage actionUpdate = new NetMessage(NetMessage.MessageType.INFO, update);
		try {
			player.getConnection().message(actionUpdate);
			if (!handOver) {
				synchronized (bjSynchronizer) {
					bjSynchronizer.wait(); //waits until the client returns the action
				}
			}
		} catch (SocketException e) {
			try {
				player.getConnection().finish();
			} catch (IOException f) {
			}
		} catch (InterruptedException e) {
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * called when the server initiates a split
	 * @param hand1 the hand that will be played out first
	 * @param hand2 the hand that will be played out second
	 */
	private void signalSplit(Hand hand1, Hand hand2) {
		NetMessage splitUpdate = new NetMessage(NetMessage.MessageType.INFO, new BJSplit(hand1, hand2));
		try {
			player.getConnection().message(splitUpdate); //message the player about the split information
		} catch (SocketException e) {
			try {
				player.getConnection().finish();
			} catch (IOException f) {
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * called when a hand is resolved
	 * @param outcome the outcome of the hand
	 * @param winnings
	 */
	private void sendHandEnd(int outcome, int winnings) {
		NetMessage handEndUpdate = new NetMessage(NetMessage.MessageType.INFO, new BJHandEnd(outcome, winnings));
		try {
			player.getConnection().message(handEndUpdate); //message the player about the hand end
		} catch (SocketException e) {
			try {
				player.getConnection().finish();
			} catch (IOException f) {
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * called to notify the player about any money they have won
	 * @param winnings the amount won
	 */
	private void payoutPlayer(int winnings){
		System.out.println("payout");
		NetMessage payoutMessage = new NetMessage(NetMessage.MessageType.INFO, new BJPayout(winnings));
		try {
			player.getConnection().message(payoutMessage); //message the player about the hand end
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return the synchronizer for this game
	 */
	public BJSynchronizer getBjSynchronizer() {
		return bjSynchronizer;
	}
	/**
	 * called by the ServerConnection managing the game when it receives the client's first bet
	 * @param firstBet the amount the client has chosen to bet
	 */
	public void setFirstBet(int firstBet) {
		this.firstBet = firstBet;
	}

	/**
	 * called by the ServerConnection managing the game when it receives the client's insurance bet
	 * @param insuranceBet the amount bet
	 */
	public void setInsuranceBet(int insuranceBet) {
		this.insuranceBet = insuranceBet;
	}
	/**
	 * called by the ServerConnection managing the game when it receives the client's action
	 * @param action the action the client has chosen to perform
	 */
	public void setAction(BJAction action) {
		this.action = action;
	}
	/**
	 * sleeps the thread to animate a pause between information sends, usually done in between card draws
	 */
	private void pause() {
		try {
			Thread.sleep(PAUSE_TIME);
		} catch (InterruptedException e) {
		}
	}
}
