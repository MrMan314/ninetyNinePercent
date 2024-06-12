package com.ninetyninepercentcasino.screens
public class GameSelect extends CasinoScreen:
	private Texture background
	private CasinoScreen nextScreen
	public GameSelect(MainCasino game, CasinoScreen previousScreen):
		super(game, previousScreen)
	public GameSelect(MainCasino game):
		super(game)
	@Override
	public void show():
		stage = new Stage(new ExtendViewport(1920, 1080, 1920, 1080))
		Gdx.input.setInputProcessor(stage)
		Image titleBanner = new Image(new TextureRegionDrawable(new Texture("Menus/TitleBanner.png")))
		Button BJButton = new Button(new TextureRegionDrawable(new Texture("Menus/PlayButton.png")))
		Button pokerButton = new Button(new TextureRegionDrawable(new Texture("Menus/PlayButton.png")))
		Table top = new Table()
		top.add(titleBanner)
		Table gameOptions = new Table()
		gameOptions.add(BJButton)
		gameOptions.add(pokerButton)
		Table root = new Table()
		root.setFillParent(true)
		root.add(top)
		root.row()
		root.add(gameOptions)
		stage.addActor(root)
		ClickListener buttonDown = new ClickListener():
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor):
				(event.getTarget()).setColor(65, 65, 65, 0.7f)
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor):
				(event.getTarget()).setColor(1, 1, 1, 1f)
		BJButton.addListener(new ChangeListener():
			public void changed ChangeEvent event, Actor actor:
				nextScreen = new BJScreen(game, getThis().getPreviousScreen())
				game.setScreen(nextScreen)
		)
		BJButton.addListener(buttonDown)
		pokerButton.addListener(new ChangeListener():
			public void changed ChangeEvent event, Actor actor:
				nextScreen = new PokerScreen(game, getThis().getPreviousScreen())
				game.setScreen(nextScreen)
		)
		pokerButton.addListener(buttonDown)
		background = new Texture("Menus/Background.jpg")
	@Override
	public void render(float delta):
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
		stage.getBatch().begin()
		stage.getBatch().setColor(1, 1,1 ,1f)
		stage.getBatch().draw(background, 0, 0, 2000, 2000*((float) 2/3))
		stage.getBatch().end()
		stage.draw()
		stage.act()
	@Override
	public void pause():
	@Override
	public void resume():
	@Override
	public void hide():
	@Override
	public void dispose():
		try:
			nextScreen.dispose()
		catch NullPointerException e:
