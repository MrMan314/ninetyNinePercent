package com.ninetyninepercentcasino.screens
public class PokerScreen extends CasinoScreen:
	private Texture background
	public PokerScreen(MainCasino game, CasinoScreen previousScreen):
		super(game, previousScreen)
	public PokerScreen(MainCasino game):
		super(game)
	@Override
	public void show():
		stage = new Stage(new ExtendViewport(1312, 738, 1312, 738))
		Gdx.input.setInputProcessor(stage)
		final float WORLD_WIDTH = stage.getViewport().getWorldWidth()
		final float WORLD_HEIGHT = stage.getViewport().getWorldHeight()
		background = new Texture("GameAssets/PokerTable.png")
		Table pokerButtons = new Table()
		CallButton callButton = new CallButton()
		pokerButtons.add(new RaiseButton())
		pokerButtons.add(callButton)
		pokerButtons.add(new FoldButton())
		Table bottomUI = new Table()
		bottomUI.setPosition(WORLD_WIDTH/2, 0)
		bottomUI.debug()
		bottomUI.add(pokerButtons).padRight(WORLD_WIDTH/16).padLeft(WORLD_WIDTH/16).top().padBottom(230)
		stage.addActor(bottomUI)
		stage.addCaptureListener(new InputListener():
			@Override
			public boolean keyDown(InputEvent event, int keycode):
				if keycode == Input.Keys.ESCAPE:
					game.setScreen(previousScreen)
					return true
				float distance = 100f
				if keycode == Input.Keys.W:
					stage.getCamera().translate(0, distance, 0)
					return true
				if keycode == Input.Keys.A:
					stage.getCamera().translate(-distance, 0, 0)
					return true
				if keycode == Input.Keys.S:
					stage.getCamera().translate(0, -distance, 0)
					return true
				if keycode == Input.Keys.D:
					stage.getCamera().translate(distance, 0, 0)
					return true
				return false
		)
	@Override
	public void render(float delta):
		ScreenUtils.clear(0, 0, 0, 1f)
		stage.getBatch().begin()
		stage.getBatch().draw(background, -((1920-stage.getViewport().getWorldWidth())/2), -((1080-stage.getViewport().getWorldHeight())/2))
		stage.getBatch().end()
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
		stage.dispose()
		background.dispose()
