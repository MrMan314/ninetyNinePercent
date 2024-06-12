package com.ninetyninepercentcasino
public class MainCasino extends Game:
	public MusicManager music
	public CasinoScreen menu
	private String serverAddress
	private int serverPort
	public MainCasino(String serverAddress, int serverPort):
		super()
		this.serverAddress = serverAddress
		this.serverPort = serverPort
	public int balance
	@Override
	public void create :
		balance = 1000
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode())
		music = new MusicManager()
		SFXManager.loadSFX()
		setScreen(new SplashScreen(this))
	public String getServerAddress():
		return serverAddress
	public int getServerPort():
		return serverPort
	@Override
	public void dispose():
		music.dispose()
		menu.dispose()
