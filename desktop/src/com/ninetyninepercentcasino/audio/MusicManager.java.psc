package com.ninetyninepercentcasino.audio
public class MusicManager implements Music.OnCompletionListener:
	private int currentTrack
	private Music music
	public MusicManager():
		currentTrack = MathUtils.random(22)+1
		setTrack()
		music.setOnCompletionListener(this)
	public void playMusic():
		music.play()
	public void pauseMusic():
		music.pause()
	private void setTrack():
		music = Gdx.audio.newMusic(Gdx.files.internal("Sound/Music/Track" + currentTrack + ".mp3"))
	public void setVolume(float volume):
		music.setVolume(volume)
	public float getVolume():
		return music.getVolume()
	@Override
	public void onCompletion(Music music):
		currentTrack++
		if (currentTrack > 23) currentTrack = 1
		setTrack()
		playMusic()
	public void dispose():
		music.dispose()
