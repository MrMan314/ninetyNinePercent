package com.ninetyninepercentcasino.screens
public class BJScreen extends CasinoScreen:
	private Texture background
	private BJClient client
	private BJStage stage
	private ArrayList updates
	private boolean firstRender
	public BJScreen(MainCasino game, CasinoScreen previousScreen):
		super(game, previousScreen)
	public BJScreen(MainCasino game):
		super(game)
	@Override
	public void resize(int width, int height):
		stage.getViewport().update(width, height, true)
		screenHeight = Gdx.graphics.getHeight()
		screenWidth = Gdx.graphics.getWidth()
	@Override
	public void show():
		firstRender = true
		stage = new BJStage(new ExtendViewport(1312, 738, 1312, 738), super.previousScreen)
		Gdx.input.setInputProcessor(stage)
		updates = new ArrayList()
		background = new Texture("GameAssets/PokerTable.png")
		stage.addCaptureListener(new InputListener():
			@Override
			public boolean keyDown(InputEvent event, int keycode):
				if keycode == Input.Keys.ESCAPE:
					game.setScreen(previousScreen)
					try:
						client.finish()
						getThis().dispose()
					catch IOException e:
						e.printStackTrace()
					return true
				return false
		)
		try:
			client = new BJClient(new Socket(game.getServerAddress(), game.getServerPort()), this)
		catch ConnectException e:
			game.setScreen(previousScreen)
			previousScreen.displayDialogBox(e.getMessage())
			getThis().dispose()
			return
		catch IOException ignored:
		client.start()
		try:
			client.message(new NetMessage(NetMessage.MessageType.INFO, new BJBeginGame()))
		catch IOException ignored:
		stage.setClient(client)
		stage.setScreen(this)
	@Override
	public void render(float delta):
		if firstRender:
			Gdx.graphics.requestRendering()
			firstRender = false
		if !updates.isEmpty():
			stage.handleDTO(updates.remove(0))
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
		stage.updateBetDisplay()
		stage.getBatch().begin()
		stage.getBatch().setColor(Color.WHITE)
		stage.getBatch().draw(background, -((1920-stage.getViewport().getWorldWidth())/2), -((1080-stage.getViewport().getWorldHeight())/2))
		stage.getBatch().end()
		updateGlobalUI()
		stage.act(delta)
		stage.draw()
	@Override
	public void hide():
	@Override
	public void pause():
	@Override
	public void resume():
	@Override
	public void dispose():
		try:
			client.finish()
		catch IOException e:
			e.printStackTrace()
		catch NullPointerException e:
		background.dispose()
		stage.dispose()
	public void requestUpdate(DTO latestUpdate):
		updates.add(latestUpdate)
		Gdx.graphics.requestRendering()
