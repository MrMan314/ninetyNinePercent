package com.ninetyninepercentcasino.screens
public class MainMenu extends CasinoScreen:
	private Texture background
	private CasinoScreen nextScreen
	public MainMenu(MainCasino game):
		super(game)
	@Override
	public void show():
		stage = new Stage(new ExtendViewport(1920, 1080, 1920, 1080))
		Gdx.input.setInputProcessor(stage)
		Skin skins = new Skin()
		skins.add("titleBanner", new Texture("Menus/TitleBanner.png"))
		skins.add("playButton", new Texture("Menus/PlayButton.png"))
		skins.add("settingsButton", new Texture("Menus/SettingsButton.png"))
		Image titleBanner = new Image(skins.getDrawable("titleBanner"))
		Button playButton = new Button(skins.getDrawable("playButton"))
		Button settingsButton = new Button(skins.getDrawable("settingsButton"))
		VerticalGroup middleMenu = new VerticalGroup()
		middleMenu.addActor(playButton)
		middleMenu.addActor(settingsButton)
		Table root = new Table()
		root.setFillParent(true)
		root.add(titleBanner).width(800).height(800*((float) 191/446)).fillX().top().padBottom(80)
		root.row()
		root.add(middleMenu).padBottom(160)
		stage.addActor(root)
		stage.addActor(globalUI)
		background = new Texture("Menus/Background.jpg")
		settingsButton.addListener(new ClickListener():
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor):
				(event.getTarget()).setColor(65, 65, 65, 0.7f)
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor):
				(event.getTarget()).setColor(1, 1, 1, 1f)
		)
		playButton.addListener(new ChangeListener():
			public void changed ChangeEvent event, Actor actor:
				nextScreen = new BJScreen(game, getThis())
				game.setScreen(nextScreen)
		)
		playButton.addListener( new ClickListener():
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor):
				(event.getTarget()).setColor(65, 65, 65, 0.7f)
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor):
				(event.getTarget()).setColor(1, 1, 1, 1f)
		)
	@Override
	public void render(float delta):
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
		stage.getBatch().begin()
		stage.getBatch().setColor(1, 1,1 ,1f)
		stage.getBatch().draw(background, 0, 0, 2000, 2000*((float) 2/3))
		stage.getBatch().end()
		updateGlobalUI()
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
		stage.dispose()
		try:
			nextScreen.dispose()
		catch NullPointerException e:
		background.dispose()
