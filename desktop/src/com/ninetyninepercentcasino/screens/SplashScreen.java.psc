package com.ninetyninepercentcasino.screens
public class SplashScreen implements Screen:
	private float time
	private float alpha
	private final MainCasino game
	private Stage stage
	private Texture splashScreen
	public SplashScreen(MainCasino game){
		this.game = game
	@Override
	public void show():
		time = 0
		alpha = 0
		splashScreen = new Texture("GameAssets/SplashScreen.png")
		stage = new Stage(new ExtendViewport(1920, 1080, 1920, 1080))
	@Override
	public void render(float delta):
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
		time += delta
		if(time < 1){
			alpha += delta
		else if(time > 3){
			alpha -= delta
			println(alpha)
		if(time > 4f){
			dispose()
			Gdx.graphics.setContinuousRendering(false)
			game.music.playMusic()
			game.setScreen(new MainMenu(game))
			Gdx.graphics.requestRendering()
		else:
			stage.getBatch().begin()
			stage.getBatch().setColor(255, 255, 255, alpha)
			stage.getBatch().draw(splashScreen, 0, 0)
			stage.getBatch().end()
	@Override
	public void resize(int width, int height):
		stage.getViewport().update(width, height, true)
	@Override
	public void pause():
	@Override
	public void resume():
	@Override
	public void hide():
	@Override
	public void dispose():
		splashScreen.dispose()
		stage.dispose()
