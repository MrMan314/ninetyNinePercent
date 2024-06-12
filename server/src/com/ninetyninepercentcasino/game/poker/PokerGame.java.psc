package com.ninetyninepercentcasino.game.poker
public class PokerGame:
	private final int SMALL_BLIND = 4
	private final int BIG_BLIND = 8
	private Deck deck
	final int numPlayers
	int numPlayersIn
	int round
	int dealerIndex
	ArrayList players
	Queue turnOrder
	public ArrayList()
	PokerPlayer dealer
	PokerPlayer smallBlind
	PokerPlayer bigBlind
	int pot
	public PokerGame(ArrayList players):
		round = 0
		this.players = players
		numPlayers = players.size()
		dealerIndex = (int)(Math.random()*numPlayers)
		numPlayersIn = numPlayers
		turnOrder = new LinkedList()
	private void setupRound():
		numPlayersIn = numPlayers
		turnOrder.clear()
		turnOrder.addAll(players)
		round++
		deck = new Deck()
		deck.shuffle()
		pot = 0
		Stack()
		for int i = dealerIndex; i < numPlayers; i++:
			tempPlayerStack.push(players.get(i))
		tempPlayerStack.addAll(players)
		if numPlayers >= 3:
			dealer = tempPlayerStack.pop()
			smallBlind = tempPlayerStack.pop()
			bigBlind = tempPlayerStack.pop()
	public void playRound():
		setupRound()
		preFlop()
		betPhase()
		println(numPlayersIn)
		if numPlayersIn >= 2:
			flop()
			betPhase()
		if numPlayersIn >= 2:
			turn()
			betPhase()
		if numPlayersIn >= 2:
			river()
			betPhase()
		if numPlayersIn >= 2:
			findWinner()
		endRound()
	public void preFlop():
		for int i = 0; i < numPlayers; i++:
			players.get(i).drawCard(deck)
			players.get(i).drawCard(deck)
		smallBlind.bet(SMALL_BLIND)
		bigBlind.bet(BIG_BLIND)
	public void flop():
		communityCards.add(deck.drawCard())
		communityCards.add(deck.drawCard())
		communityCards.add(deck.drawCard())
	public void turn():
		communityCards.add(deck.drawCard())
	public void river():
		communityCards.add(deck.drawCard())
	public void betPhase():
		int tempPot = 0
		int highestBet = 0
		int numConsecutiveChecks = 0
		int currentPlayerIndex = dealerIndex
		while turnOrder.peek() != dealer:
			nextPlayer()
		nextPlayer()
		while numConsecutiveChecks < numPlayersIn:
			numConsecutiveChecks = 500
			PokerPlayer currentPlayer = turnOrder.poll()
			PokerPlayer previousPlayer = turnOrder.peek()
			ArrayList()
			availActions.add(PokerPlayer.Actions.FOLD)
			if !turnOrder.isEmpty():
				assert currentPlayer != null
				if currentPlayer.getBalanceInPot() < highestBet:
					availActions.add(PokerPlayer.Actions.RAISE)
					availActions.add(PokerPlayer.Actions.CALL)
			numPlayersIn--
			for PokerPlayer player in players:
				player.clearBalanceInPot()
	private void findWinner():
	public void endRound():
		dealerIndex++
		if (dealerIndex >= numPlayers) dealerIndex = 0
	private void nextPlayer():
		turnOrder.add(turnOrder.poll())
	public Deck getDeck():
		return deck
