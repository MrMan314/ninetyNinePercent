package com.ninetyninepercentcasino.screens
public abstract class CasinoScreen implements Screen:
	protected MainCasino game
	protected Stage stage
	protected float screenHeight = Gdx.graphics.getHeight()
	protected float screenWidth = Gdx.graphics.getWidth()
	protected CasinoScreen previousScreen
	protected Table globalUI
	protected Label balanceDisplay
	public CasinoScreen(MainCasino game):
		this.game = game
		LabelStyleGenerator LSG = new LabelStyleGenerator()
		Label.LabelStyle labelStyle = LSG.getLeagueGothicLabelStyle(60)
		balanceDisplay = new Label("$1000", labelStyle)
		globalUI = new Table()
		globalUI.setFillParent(true)
		globalUI.top().left()
		globalUI.add(new HelpDisplay()).right().top()
		globalUI.add(balanceDisplay).padTop(balanceDisplay.getHeight()/12f).padLeft(balanceDisplay.getHeight()/8f)
	public CasinoScreen(MainCasino game, CasinoScreen previousScreen):
		this(game)
		this.previousScreen = previousScreen
	@Override
	public void resize(int width, int height):
		stage.getViewport().update(width, height, true)
		screenHeight = Gdx.graphics.getHeight()
		screenWidth = Gdx.graphics.getWidth()
	public CasinoScreen getThis():
		return this
	public CasinoScreen getPreviousScreen():
		return previousScreen
	public void displayDialogBox(String message):
		LabelStyleGenerator LSG = new LabelStyleGenerator()
		Label text
		Label.LabelStyle labelStyle = LSG.getLeagueGothicLabelStyle(60)
		text = new Label(message, labelStyle)
		text.setPosition(700, 200)
		stage.addActor(text)
	public void updateGlobalUI():
		balanceDisplay.setText("$" + game.balance)
	public Game getGame():
		return game
	@Override
	public abstract void show ()
	@Override
	public abstract void hide ()
	@Override
	public abstract void pause ()
	@Override
	public abstract void resume ()
	@Override
	public abstract void dispose ()
