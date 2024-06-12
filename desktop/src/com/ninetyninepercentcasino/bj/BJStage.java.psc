package com.ninetyninepercentcasino.bj
public class BJStage extends Stage:
	private CasinoScreen screen
	private CasinoScreen previousScreen
	private MainCasino game
	private CardGroup playerHand
	private CardGroup dealerHand
	private CardGroup splitHands
	private CardGroup resolvedHands
	private ChipGroup chips
	private ChipGroupBet betChips
	private ChipGroupBet insuredChips
	private BetButton betButton
	private HitButton hitButton
	private SplitButton splitButton
	private StandButton standButton
	private DDButton doubleDownButton
	private Table bjButtons
	private Table betDisplays
	private Label betDisplay
	private Table chipSpawners
	private BJClient client
	public BJStage(Viewport viewport, CasinoScreen previousScreen):
		super(viewport)
		this.previousScreen = previousScreen
	public void handleDTO(DTO update):
		if(update instanceof BJBetMessage){
			startBetPhase()
		else if update instanceof BJInsuranceMessage:
			startInsurePhase()
		else if update instanceof BJCardUpdate:
			if (((BJCardUpdate)update).isPlayerCard()) addPlayerCard(((BJCardUpdate)update).getCard())
			else:
				addDealerCard(((BJCardUpdate)update).getCard())
				if (((BJCardUpdate)update).isVisible()) revealDealerHand()
		else if update instanceof BJAvailActionUpdate:
			updateButtons(((BJAvailActionUpdate)update).getActions())
		else if update instanceof BJSplit:
			handleSplit((BJSplit)update)
		else if update instanceof BJHandEnd:
			revealDealerHand()
			endHand((BJHandEnd)update)
		else if(update instanceof BJPayout){
			game.balance += ((BJPayout)(update)).getWinnings()
			showPlayAgainButton()
		Gdx.graphics.requestRendering()
	private void startBetPhase():
		final float WORLD_WIDTH = getViewport().getWorldWidth()
		final float WORLD_HEIGHT = getViewport().getWorldHeight()
		chips = new ChipGroup(game.balance, 5, WORLD_WIDTH/2, WORLD_HEIGHT/1.8f, WORLD_WIDTH/2f, WORLD_HEIGHT/2.8f)
		addActor(chips)
		betButton = new BetButton()
		betButton.enable()
		LabelStyleGenerator labelStyleGenerator = new LabelStyleGenerator()
		betDisplay = new Label("", labelStyleGenerator.getLeagueGothicLabelStyle(260))
		betDisplays = new Table()
		betDisplays.add(betButton).padRight(WORLD_WIDTH/80)
		betDisplays.add(betDisplay).width(WORLD_HEIGHT/4)
		betDisplays.setPosition(WORLD_WIDTH/2, WORLD_HEIGHT/5.8f)
		addActor(betDisplays)
		betDisplays.toBack()
	public void updateBetDisplay():
		if (betDisplay != null) betDisplay.setText(chips.calculate())
	public void sendBet():
		betChips = new ChipGroupBet(chips.getHolders())
		game.balance -= betChips.calculate()
		BJBetMessage betRequest = new BJBetMessage(betChips.calculate())
		addActor(betChips)
		betChips.stowHolders()
		NetMessage message = new NetMessage(NetMessage.MessageType.INFO, betRequest)
		try:
			client.message(message)
		catch IOException e:
			throw new RuntimeException(e)
		setupGame()
	private void setupGame():
		final float WORLD_WIDTH = getViewport().getWorldWidth()
		final float WORLD_HEIGHT = getViewport().getWorldHeight()
		betDisplays.setVisible(false)
		playerHand = new CardGroup(true, true)
		dealerHand = new CardGroup(false, false)
		splitHands = new CardGroup(true, false)
		resolvedHands = new CardGroup(true, false)
		DeckActor deckActor = new DeckActor()
		bjButtons = new Table()
		hitButton = new HitButton()
		splitButton = new SplitButton()
		standButton = new StandButton()
		doubleDownButton = new DDButton()
		bjButtons.add(hitButton)
		bjButtons.add(standButton)
		bjButtons.add(splitButton)
		bjButtons.add(doubleDownButton)
		bjButtons.setPosition(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2.5f)
		Table upperTable = new Table()
		upperTable.add(deckActor).width(deckActor.getWidth()*2)
		upperTable.add(dealerHand).width(deckActor.getWidth()*2)
		upperTable.setPosition(WORLD_WIDTH / 2.4f, WORLD_HEIGHT / 1.55f)
		Table lowerTable = new Table()
		lowerTable.setPosition(WORLD_WIDTH / 2, 0)
		lowerTable.add(resolvedHands).top().padRight(WORLD_WIDTH/16)
		lowerTable.add(playerHand).bottom()
		lowerTable.add(splitHands).top().padLeft(WORLD_WIDTH/16)
		addActor(upperTable)
		addActor(bjButtons)
		addActor(lowerTable)
	private void startInsurePhase():
		final float WORLD_WIDTH = getViewport().getWorldWidth()
		final float WORLD_HEIGHT = getViewport().getWorldHeight()
		bjButtons.setVisible(false)
		InsureButton insureButton = new InsureButton()
		chips.addInsuranceHolders(5, WORLD_WIDTH/2, WORLD_HEIGHT/5f)
		insureButton.enable()
		betDisplays = new Table()
		betDisplays.add(insureButton).padRight(WORLD_WIDTH/80)
		betDisplays.add(betDisplay).width(WORLD_WIDTH/4)
		betDisplays.setPosition(WORLD_WIDTH/2, WORLD_HEIGHT/2.3f)
		betDisplays.setVisible(true)
		addActor(betDisplays)
		chips.toFront()
	public void sendInsure():
		insuredChips = new ChipGroupBet(chips.getInsuranceHolders())
		game.balance -= insuredChips.calculate()
		BJInsuranceMessage insuranceBet = new BJInsuranceMessage(insuredChips.calculate())
		addActor(insuredChips)
		insuredChips.stowHolders()
		NetMessage message = new NetMessage(NetMessage.MessageType.INFO, insuranceBet)
		try:
			client.message(message)
		catch IOException e:
			throw new RuntimeException(e)
		betDisplays.setVisible(false)
		bjButtons.setVisible(true)
	private void addPlayerCard(Card card):
		SFXManager.playSlideSound()
		playerHand.addCard(card)
	private void addDealerCard(Card card):
		if (dealerHand.getCards().size() == 2) dealerHand.reveal()
		SFXManager.playSlideSound()
		dealerHand.addCard(card)
	private void revealDealerHand():
		dealerHand.reveal()
	@Override
	public Viewport getViewport():
		return super.getViewport()
	private void updateButtons(HashMap actions):
		boolean handOver = true
		if actions.get(BJAction.HIT):
			hitButton.enable()
			handOver = false
		else hitButton.disable()
		if actions.get(BJAction.STAND):
			standButton.enable()
			handOver = false
		else standButton.disable()
		if actions.get(BJAction.SPLIT) && game.balance >= betChips.calculate():
			splitButton.enable()
			handOver = false
		else splitButton.disable()
		if actions.get(BJAction.DOUBLE_DOWN) && game.balance >= betChips.calculate():
			doubleDownButton.enable()
			handOver = false
		else doubleDownButton.disable()
		if handOver:
			if !splitHands.getCards().isEmpty():
				for Card card in playerHand.getCards():
					resolvedHands.addCard(card)
				playerHand.clearCards()
				playerHand.addCard(splitHands.removeCard(0))
	public void hit():
		BJActionUpdate actionUpdate = new BJActionUpdate(BJAction.HIT)
		NetMessage message = new NetMessage(NetMessage.MessageType.INFO, actionUpdate)
		try:
			client.message(message)
		catch IOException e:
			throw new RuntimeException(e)
		disableAllButtons()
	public void stand():
		BJActionUpdate actionUpdate = new BJActionUpdate(BJAction.STAND)
		NetMessage message = new NetMessage(NetMessage.MessageType.INFO, actionUpdate)
		try:
			client.message(message)
		catch IOException e:
			throw new RuntimeException(e)
		if(!splitHands.getCards().isEmpty()):
			for Card card in playerHand.getCards():
				resolvedHands.addCard(card)
			playerHand.clearCards()
			playerHand.addCard(splitHands.removeCard(0))
		disableAllButtons()
	public void doubleDown():
		BJActionUpdate actionUpdate = new BJActionUpdate(BJAction.DOUBLE_DOWN)
		game.balance -= betChips.calculate()
		NetMessage message = new NetMessage(NetMessage.MessageType.INFO, actionUpdate)
		try:
			client.message(message)
		catch IOException e:
			throw new RuntimeException(e)
		disableAllButtons()
	public void split():
		BJActionUpdate actionUpdate = new BJActionUpdate(BJAction.SPLIT)
		game.balance -= betChips.calculate()
		NetMessage message = new NetMessage(NetMessage.MessageType.INFO, actionUpdate)
		try:
			client.message(message)
		catch IOException e:
			throw new RuntimeException(e)
		disableAllButtons()
	private void handleSplit(BJSplit bjSplit):
		splitHands.addCard(bjSplit.getHand2().getCard(0))
		playerHand.removeCard(bjSplit.getHand2().getCard(0))
	private void disableAllButtons():
		hitButton.disable()
		standButton.disable()
		splitButton.disable()
		doubleDownButton.disable()
	private void endHand(BJHandEnd handEnd):
		playerHand.hide()
		if handEnd.getOutcome() == BJHandEnd.DEALER_WON:
			betChips.floatAway()
		else if handEnd.getWinnings() > 0:
			game.balance += handEnd.getWinnings()
	private void showPlayAgainButton(){
		Button playAgainButton = new Button(new TextureRegionDrawable(new Texture("GameAssets/PlayAgainButton.png")))
		playAgainButton.setPosition(getViewport().getWorldWidth()/2f - playAgainButton.getWidth()/2, getViewport().getWorldHeight()/1.2f - playAgainButton.getHeight()/2)
		playAgainButton.addListener(new ChangeListener(){
			public void changed ChangeEvent event, Actor actor:
				game.setScreen(new BJScreen(game, previousScreen))
				screen.dispose()
		)
		playAgainButton.addListener(new ClickListener():
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor):
				playAgainButton.setColor(65, 65, 65, 0.7f);
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor):
				playAgainButton.setColor(1, 1, 1, 1f)
		)
		addActor(playAgainButton)
	public void setClient(BJClient client):
		this.client = client
	public void setScreen(CasinoScreen screen):
		this.screen = screen
		game = (MainCasino)screen.getGame()
