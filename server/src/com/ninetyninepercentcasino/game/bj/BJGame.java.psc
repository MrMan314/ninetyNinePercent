package com.ninetyninepercentcasino.game.bj
public class BJGame extends Thread:
	private static final int PLAYER_WON = 0
	private static final int DEALER_WON = 1
	private static final int TIE = 3
	private static final int PLAYER_BLACKJACK = 4
	private static final long PAUSE_TIME = 999
	private final BJPlayer player
	private BJDealer dealer
	private Stack hands
	private Stack resolved
	private final BJSynchronizer bjSynchronizer
	private int firstBet
	private int insuranceBet
	private BJAction action
	public BJGame(BJPlayer player):
		this.player = player
		hands = new Stack()
		resolved = new Stack()
		bjSynchronizer = new BJSynchronizer()
	public void run():
		getInitialBet()
		Deck deck = new Deck()
		deck.shuffle()
		dealer = new BJDealer(deck)
		drawCardUpdate(dealer.drawCard(), true, false)
		drawCardUpdate(dealer.drawCard(), false, false)
		BJHand firstHand = new BJHand(player)
		firstHand.setBet(firstBet)
		hands.push(firstHand)
		if (dealer.hasVisibleAce()) getInsurance()
		drawCardUpdate(firstHand.drawCard(deck), true, true)
		drawCardUpdate(firstHand.drawCard(deck), true, true)
		while !hands.isEmpty():
			BJHand currentHand = hands.peek()
			HashMap availableActions = currentHand.getOptions()
			boolean handOver = true
			for Boolean available in availableActions.values():
				if available:
					handOver = false
					break
			sendOptions(availableActions, handOver)
			if handOver:
				resolved.push(hands.pop())
			else:
				switch(action):
					case HIT:
						drawCardUpdate(currentHand.drawCard(deck), true, true)
						break
					case STAND:
						resolved.push(hands.pop())
						break
					case SPLIT:
						BJHand hand1 = new BJHand(player, currentHand.getCard(0))
						BJHand hand2 = new BJHand(player, currentHand.getCard(1))
						hand1.setBet(currentHand.getAmountBet())
						hand2.setBet(currentHand.getAmountBet())
						hands.pop()
						hands.push(hand2)
						hands.push(hand1)
						signalSplit(new Hand(hand1.getCard(0)), new Hand(hand2.getCard(0)))
						break
					case DOUBLE_DOWN:
						drawCardUpdate(currentHand.drawCard(deck), true, true)
						currentHand.doubleBet()
						resolved.push(hands.pop())
						break
		actDealer()
		while !resolved.isEmpty():
			BJHand currentHand = resolved.pop()
			int outcome = findWinner(currentHand, dealer)
			int winnings = 0
			switch(outcome):
				case PLAYER_BLACKJACK:
					player.addBalance((int) (currentHand.getAmountBet()*2.5))
					winnings = (int) (currentHand.getAmountBet()*2.5)
					break
				case PLAYER_WON:
					player.addBalance(currentHand.getAmountBet()*2)
					winnings = currentHand.getAmountBet()*2
					break
				case TIE:
					player.addBalance(currentHand.getAmountBet())
					winnings = currentHand.getAmountBet()
					break
				case DEALER_WON:
					break
			sendHandEnd(outcome, winnings)
		if dealer.getNumCards() == 2 && dealer.hasVisibleAce():
			player.addBalance(insuranceBet*3)
		payoutPlayer(insuranceBet*3)
	private void actDealer():
		while dealer.getScore() < 17:
			drawCardUpdate(dealer.drawCard(), true, false)
	private void getInitialBet():
		NetMessage getBet = new NetMessage(NetMessage.MessageType.INFO, new BJBetMessage())
		try:
			player.getConnection().message(getBet)
		catch SocketException e:
			try:
				player.getConnection().finish()
			catch IOException f:
		catch IOException e:
			throw new RuntimeException(e)
		synchronized bjSynchronizer:
			try:
				bjSynchronizer.wait()
			catch InterruptedException e:
	private void getInsurance():
		NetMessage insuranceMessage = new NetMessage(NetMessage.MessageType.INFO, new BJInsuranceMessage())
		try:
			player.getConnection().message(insuranceMessage)
		catch SocketException e:
			try:
				player.getConnection().finish()
			catch IOException f:
		catch IOException e:
			throw new RuntimeException(e)
		synchronized bjSynchronizer:
			try:
				bjSynchronizer.wait()
			catch InterruptedException e:
	private int findWinner(BJHand playerHand, BJDealer dealer):
		int playerScore = playerHand.getScore()
		int dealerScore = dealer.getScore()
		if playerScore == 21 && dealerScore == 21:
			if (playerHand.getCards().size() == 2 && dealer.getNumCards() == 2) return TIE
			else if (playerHand.getCards().size() == 2) return PLAYER_BLACKJACK
			else return DEALER_WON
		else if ((playerScore > 21 && dealerScore > 21)) return DEALER_WON
		else if (playerScore == dealerScore) return TIE
		else if (playerScore > 21) return DEALER_WON
		else if (dealerScore > 21) return PLAYER_WON
		else if (playerScore > dealerScore) return PLAYER_WON
		else return DEALER_WON
	private void drawCardUpdate(Card card, boolean visible, boolean isPlayerCard):
		NetMessage cardUpdate = new NetMessage(NetMessage.MessageType.INFO, new BJCardUpdate(card, visible, isPlayerCard))
		try:
			player.getConnection().message(cardUpdate)
		catch SocketException e:
			try:
				player.getConnection().finish()
			catch IOException f:
		catch IOException e:
			throw new RuntimeException(e)
		pause()
	private void sendOptions(HashMap availableActions, boolean handOver):
		BJAvailActionUpdate update = new BJAvailActionUpdate(availableActions)
		NetMessage actionUpdate = new NetMessage(NetMessage.MessageType.INFO, update)
		try:
			player.getConnection().message(actionUpdate)
			if !handOver:
				synchronized bjSynchronizer:
					bjSynchronizer.wait()
		catch SocketException e:
			try:
				player.getConnection().finish()
			catch IOException f:
		catch InterruptedException e:
		catch IOException e:
			throw new RuntimeException(e)
	private void signalSplit(Hand hand1, Hand hand2):
		NetMessage splitUpdate = new NetMessage(NetMessage.MessageType.INFO, new BJSplit(hand1, hand2))
		try:
			player.getConnection().message(splitUpdate)
		catch SocketException e:
			try:
				player.getConnection().finish()
			catch IOException f:
		catch IOException e:
			e.printStackTrace()
	private void sendHandEnd(int outcome, int winnings):
		NetMessage handEndUpdate = new NetMessage(NetMessage.MessageType.INFO, new BJHandEnd(outcome, winnings))
		try:
			player.getConnection().message(handEndUpdate)
		catch SocketException e:
			try:
				player.getConnection().finish()
			catch IOException f:
		catch IOException e:
			throw new RuntimeException(e)
	private void payoutPlayer(int winnings){
		println("payout")
		NetMessage payoutMessage = new NetMessage(NetMessage.MessageType.INFO, new BJPayout(winnings))
		try:
			player.getConnection().message(payoutMessage)
		catch IOException e:
			throw new RuntimeException(e)
	public BJSynchronizer getBjSynchronizer():
		return bjSynchronizer
	public void setFirstBet(int firstBet):
		this.firstBet = firstBet
	public void setInsuranceBet(int insuranceBet):
		this.insuranceBet = insuranceBet
	public void setAction(BJAction action):
		this.action = action
	private void pause():
		try:
			Thread.sleep(PAUSE_TIME)
		catch InterruptedException e:
