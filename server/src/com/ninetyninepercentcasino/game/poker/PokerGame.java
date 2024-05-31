package com.ninetyninepercentcasino.game.poker;

import com.ninetyninepercentcasino.game.gameparts.Card;
import com.ninetyninepercentcasino.game.gameparts.Deck;

import java.util.*;

/**
 * Models a poker game on server side
 * @author Grant Liang
 */
public class PokerGame {
	private final double SMALL_BLIND = 4;
	private final double BIG_BLIND = 8;
	private Deck deck;
	final int numPlayers;
	int numPlayersIn;
	int round;
	int dealerIndex;
	ArrayList<PokerPlayer> players;
	Queue<PokerPlayer> turnOrder;
	public ArrayList<Card> communityCards = new ArrayList<>();
	PokerPlayer dealer;
	PokerPlayer smallBlind;
	PokerPlayer bigBlind;
	double pot;

	public PokerGame(ArrayList<PokerPlayer> players){
		round = 0;
		this.players = players;
		numPlayers = players.size();
		dealerIndex = (int)(Math.random()*numPlayers);
		numPlayersIn = numPlayers;
		turnOrder = new LinkedList<>();
	}
	private void setupRound(){
		numPlayersIn = numPlayers;
		turnOrder.clear();
		turnOrder.addAll(players);
		round++;
		deck = new Deck();
		deck.shuffle();
		pot = 0;

		Stack<PokerPlayer> tempPlayerStack = new Stack<>();
		for(int i = dealerIndex; i < numPlayers; i++){
			tempPlayerStack.push(players.get(i));
		}
		tempPlayerStack.addAll(players);
		if(numPlayers >= 3){
			dealer = tempPlayerStack.pop();
			smallBlind = tempPlayerStack.pop();
			bigBlind = tempPlayerStack.pop();
		}
		//TODO less than 3 player game
	}
	public void playRound(){
		setupRound();
		preFlop();
		betPhase();
		System.out.println(numPlayersIn);
		if(numPlayersIn >= 2) {
			flop();
			betPhase();
		}
		if(numPlayersIn >= 2) {
			turn();
			betPhase();
		}
		if(numPlayersIn >= 2) {
			river();
			betPhase();
		}
		if(numPlayersIn >= 2){
			findWinner();
		}
		//else turnOrder.poll().addToBalance(pot);
		endRound();
	}
	public void preFlop(){
		for(int i = 0; i < numPlayers; i++){
			players.get(i).drawCard(deck);
			players.get(i).drawCard(deck);
		}
		smallBlind.bet(SMALL_BLIND);
		bigBlind.bet(BIG_BLIND);
	}
	public void flop(){
		communityCards.add(deck.drawCard());
		communityCards.add(deck.drawCard());
		communityCards.add(deck.drawCard());
	}
	public void turn(){
		communityCards.add(deck.drawCard());
	}
	public void river(){
		communityCards.add(deck.drawCard());
	}
	public void betPhase(){
		double tempPot = 0;
		double highestBet = 0;
		int numConsecutiveChecks = 0;
		int currentPlayerIndex = dealerIndex;

		//finds the first person to act, who is the person to the left of the dealer
		while(turnOrder.peek() != dealer){
			nextPlayer();
		}
		nextPlayer();

		while(numConsecutiveChecks < numPlayersIn){
			numConsecutiveChecks = 500;
			//if the player has folded, skip their turn
			//this may be redundant code
//			while(turnOrder.peek().folded){
//				turnOrder.poll();
//			}
			PokerPlayer currentPlayer = turnOrder.poll();
			PokerPlayer previousPlayer = turnOrder.peek();

			ArrayList<PokerPlayer.Actions> availActions = new ArrayList<>();
			availActions.add(PokerPlayer.Actions.FOLD);
			if(!turnOrder.isEmpty()){
				assert currentPlayer != null;
				if(currentPlayer.getBalanceInPot() < highestBet){
					availActions.add(PokerPlayer.Actions.RAISE);
					availActions.add(PokerPlayer.Actions.CALL);
				}
			}
			numPlayersIn--;
//			PokerPlayer.Actions action = currentPlayer.getAction();
//			//player folds
//			if(action == PokerPlayer.Actions.FOLD){
//				currentPlayer.fold();
//				turnOrder.remove(currentPlayer);
//				numPlayersIn--;
//			}
//			else if(action == 1){
//
//			}
//			//player calls
//			else if(action == 2){
//				if(currentPlayer.getBalanceInPot() < highestBet) {
//					pot += currentPlayer.bet(highestBet-currentPlayer.getBalanceInPot());
//				}
//			}
//			//player raises
//			else if(action == 3){
//				numConsecutiveChecks = 0;
//			}
			//nextPlayer();

			for(PokerPlayer player : players){
				player.clearBalanceInPot();
			}
		}
	}
	private void findWinner(){

	}
	public void endRound(){
		dealerIndex++;
		if(dealerIndex >= numPlayers) dealerIndex = 0;
	}
	private void nextPlayer(){
		turnOrder.add(turnOrder.poll());
	}
	public Deck getDeck(){
		return deck;
	}
}
