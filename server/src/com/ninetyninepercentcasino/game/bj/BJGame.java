package com.ninetyninepercentcasino.game.bj;

import com.ninetyninepercentcasino.game.gameparts.Card;
import com.ninetyninepercentcasino.game.gameparts.Deck;
import com.ninetyninepercentcasino.game.gameparts.Hand;
import com.ninetyninepercentcasino.net.*;
import com.ninetyninepercentcasino.net.BJAvailActionUpdate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;
import java.net.SocketException;

/**
 * Runs logic for a blackjack game and sends messages to client as needed
 * @author Grant Liang
 */
public class BJGame extends Thread {
	private static final int PLAYER_WON = 0;
	private static final int DEALER_WON = 1;
	private static final int TIE = 3;
	private static final int PLAYER_BLACKJACK = 4;
	private static final long PAUSE_TIME = 1000;

	private final BJPlayer player;
	private BJDealer dealer;
	private Stack<BJHand> hands;
	private Stack<BJHand> resolved;

	private final BJSynchronizer bjSynchronizer;
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
		drawCardUpdate(dealer.drawCard(), true, false);
		drawCardUpdate(dealer.drawCard(), false, false);

		BJHand firstHand = new BJHand(player);
		firstHand.setBet(firstBet);
		hands.push(firstHand);

		if(dealer.hasVisibleAce()) getInsurance();

		drawCardUpdate(firstHand.drawCard(deck), true, true);
		drawCardUpdate(firstHand.drawCard(deck), true, true);

		while(!hands.isEmpty()){
			BJHand currentHand = hands.peek();
			HashMap<BJAction, Boolean> availableActions = currentHand.getOptions();
			boolean handOver = true;
			for(Boolean available : availableActions.values()){
				if (available) {
					handOver = false;
					break;
				}
			}
			if(handOver) {
				resolved.push(hands.pop());
				System.out.println("HAND OVER.");
			}
			else {
				sendOptions(availableActions);
				switch(action){
					case HIT:
						drawCardUpdate(currentHand.drawCard(deck), true, true);
						break;
					case STAND:
						resolved.push(hands.pop());
						break;
					case SPLIT:
						BJHand hand1 = new BJHand(player, currentHand.getCard(0));
						BJHand hand2 = new BJHand(player, currentHand.getCard(1));
						hands.push(hand1);
						hands.push(hand2);
						signalSplit(hand1, hand2);
						break;
					case DOUBLE_DOWN:
						drawCardUpdate(currentHand.drawCard(deck), true, true);
						currentHand.doubleBet();
						resolved.push(hands.pop());
						break;
				}
			}
		}
		actDealer();
		while (!resolved.isEmpty()) {
			BJHand currentHand = resolved.pop();
			int outcome = findWinner(currentHand, dealer);
			int winnings = 0; //net earnings for the player
			switch(outcome){
				case PLAYER_BLACKJACK:
					player.addBalance((int) (currentHand.getAmountBet()*2.5));
					winnings = (int) (currentHand.getAmountBet()*1.5);
					break;
				case PLAYER_WON:
					player.addBalance(currentHand.getAmountBet()*2);
					winnings = currentHand.getAmountBet();
					break;
				case TIE:
					player.addBalance(currentHand.getAmountBet());
					winnings = 0;
					break;
				case DEALER_WON:
					if(dealer.getNumCards() == 2) player.addBalance(insuranceBet*3);
					winnings = dealer.getInsuranceBet()*2;
					break;
			}
			sendHandEnd(outcome, winnings);
		}

	}
	public BJDealer getDealer(){
		return dealer;
	}
	/**
	 * simulates the action of the dealer
	 */
	private void actDealer(){
		while(dealer.getScore() < 17){
			drawCardUpdate(dealer.drawCard(), false, false);
		}
	}

	private void getInitialBet(){
		NetMessage getBet = new NetMessage(NetMessage.MessageType.INFO, new BJBetRequest());
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
		synchronized(bjSynchronizer) {
			try {
				bjSynchronizer.wait(); //waits until the client returns the amount bet
			} catch (InterruptedException e) {
			}
		}
	}
	private void getInsurance(){
		NetMessage insuranceMessage = new NetMessage(NetMessage.MessageType.INFO, new BJInsuranceRequest());
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
		synchronized(bjSynchronizer) {
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
	private int findWinner(BJHand playerHand, BJDealer dealer){
		int playerScore = playerHand.getScore();
		int dealerScore = dealer.getScore();
		if(playerScore == 21 && dealerScore == 21){
			if(playerHand.getCards().size() == 2 && dealer.getNumCards() == 2) return TIE;
			else if(playerHand.getCards().size() == 2) return PLAYER_BLACKJACK;
			else return DEALER_WON;
		}
		else if((playerScore > 21 && dealerScore > 21)) return DEALER_WON;
		else if(playerScore == dealerScore) return TIE;
		else if(playerScore > 21) return DEALER_WON;
		else if(dealerScore > 21) return PLAYER_WON;
		else if(playerScore > dealerScore) return PLAYER_WON;
		else return DEALER_WON;
	}

	/**
	 * called when the client needs to be updated about a card that was drawn
	 */
	private void drawCardUpdate(Card card, boolean visible, boolean isPlayerCard){
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
	 * @param availableActions the
	 */
	private void sendOptions(HashMap<BJAction, Boolean> availableActions){
		NetMessage actionUpdate = new NetMessage(NetMessage.MessageType.INFO, new BJAvailActionUpdate(availableActions));
		try {
			player.getConnection().message(actionUpdate);
			synchronized(bjSynchronizer) {
				bjSynchronizer.wait(); //waits until the client returns the action
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
	private void signalSplit(Hand hand1, Hand hand2){
		NetMessage splitUpdate = new NetMessage(NetMessage.MessageType.INFO, new BJSplit(hand1, hand2));
		try {
			player.getConnection().message(splitUpdate);
			synchronized(bjSynchronizer) {
				bjSynchronizer.wait(); //waits until the client returns the action
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
	private void sendHandEnd(int winner, int winnings){
		NetMessage handEndUpdate = new NetMessage(NetMessage.MessageType.INFO, new BJHandEnd(winner, winnings));
		try {
			player.getConnection().message(handEndUpdate);
			synchronized(bjSynchronizer) {
				bjSynchronizer.wait(); //waits until the client returns the action
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
	public BJSynchronizer getBjSynchronizer() {
		return bjSynchronizer;
	}
	/**
	 * called by the ServerConnection managing the game when it receives the client's first bet
	 * @param firstBet the amount the client has chosen to bet
	 */
	public void setFirstBet(int firstBet){
		this.firstBet = firstBet;
	}
	/**
	 * called by the ServerConnection managing the game when it receives the client's action
	 * @param action the action the client has chosen to perform
	 */
	public void setAction(BJAction action){
		this.action = action;
	}
	/**
	 * sleeps the thread to prevent multiple DTOs being sent to client at the same time
	 */
	private void pause(){
		try {
			Thread.sleep(PAUSE_TIME);
		} catch (InterruptedException e) {
		}
	}
}
