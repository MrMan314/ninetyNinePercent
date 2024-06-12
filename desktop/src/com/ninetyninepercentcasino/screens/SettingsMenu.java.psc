package com.ninetyninepercentcasino.screens
public class SettingsMenu extends CasinoScreen:
	private Texture background
	public SettingsMenu(MainCasino game, CasinoScreen previousScreen):
		super(game, previousScreen)
		stage = new Stage(new ScreenViewport())
	public SettingsMenu(MainCasino game):
		super(game)
		stage = new Stage(new ScreenViewport())
	@Override
	public void show():
		game.music.setVolume(0.1f)
		stage = new Stage(new ScreenViewport())
		Gdx.input.setInputProcessor(stage)
		Skin skins = new Skin()
		skins.add("titleBanner", new Texture("Menus/TitleBanner.png"))
		skins.add("settingsButton", new Texture("Menus/SettingsButton.png"))
		Image titleBanner = new Image(skins.getDrawable("titleBanner"))
		Button settingsButton = new Button(skins.getDrawable("settingsButton"))
		VerticalGroup middleMenu = new VerticalGroup()
		middleMenu.addActor(settingsButton)
		ChipGroup sfxVolumeStack = new ChipGroup(0, 0, 0, 0, (int) (SFXManager.getVolume()*10), 0, 0f, 0f, 0f, 0f)
		ChipGroup sfxExtraStack = new ChipGroup(0, 0, 0, 0, (int) ((1 - SFXManager.getVolume())*10), 0, 0f, 0f, 0f, 0f)
		ChipGroup musicVolumeStack = new ChipGroup(0, 0, 0, 0, (int) (game.music.getVolume()*10), 0, 0f, 0f, 0f, 0f)
		ChipGroup musicExtraStack = new ChipGroup(0, 0, 0, 0, (int) (1 - game.music.getVolume()*10), 0, 0f, 0f, 0f, 0f)
		Table root = new Table()
		root.setFillParent(true)
		root.add(titleBanner).width(800).height(800*((float) 191/446)).fillX().align(Align.top).padBottom(100).top()
		root.row()
		root.add(middleMenu)
		stage.addActor(root)
		background = new Texture("Menus/Background.jpg")
		stage.addCaptureListener(new InputListener():
			@Override
			public boolean keyDown(InputEvent event, int keycode):
				if keycode == Input.Keys.ESCAPE:
					game.setScreen(previousScreen)
					return true
				return false
		)
		ClickListener buttonDown = new ClickListener():
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor):
				(event.getTarget()).setColor(65, 65, 65, 0.7f)
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor):
				(event.getTarget()).setColor(1, 1, 1, 1f)
		settingsButton.addListener(buttonDown)
		settingsButton.addListener(new ChangeListener():
			public void changed ChangeEvent event, Actor actor:
				game.setScreen(previousScreen)
		)
	@Override
	public void render(float delta):
		ScreenUtils.clear(0, 0, 0f, 1)
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
		game.music.setVolume(1f)
	@Override
	public void dispose():
		stage.dispose()
		background.dispose()
