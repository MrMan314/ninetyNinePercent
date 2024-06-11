package com.ninetyninepercentcasino.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.audio.Music;

/**
 * Class to manage game music
 * @author Grant Liang
 */
public class MusicManager implements Music.OnCompletionListener {
	private int currentTrack; //this will hold the number of the track of music currently playing
	private Music music; //holds the music instance representing the currently streaming audio file

	/**
	 * Initializes the music manager
	 */
	public MusicManager() {
		currentTrack = MathUtils.random(22)+1; //sets the track number to a random number
		setTrack(); //call to update the music instance to match the current track number
		music.setOnCompletionListener(this); //when the music instance is done playing, it will notify the listener, which will be the music manager
	}

	/**
	 * plays the current track
	 */
	public void playMusic() {
		music.play();
	}
	/**
	 * pauses the current track
	 */
	public void pauseMusic() {
		music.pause();
	}
	/**
	 * sets the current file being streamed to match currentTrack
	 */
	private void setTrack() {
		music = Gdx.audio.newMusic(Gdx.files.internal("Sound/Music/Track" + currentTrack + ".mp3")); //retrieves the file with the number matching currentTrack
	}

	/**
	 * sets the volume of the music
	 * @param volume volume of the music
	 */
	public void setVolume(float volume) {
		music.setVolume(volume);
	}

	/**
	 * @return the current volume of the music
	 */
	public float getVolume() {
		return music.getVolume();
	}

	/**
	 * when the music is finished playing, this will be called to play the next track
	 * @param music the Music that reached the end of the file
	 */
	@Override
	public void onCompletion(Music music) {
		currentTrack++;
		if (currentTrack > 23) currentTrack = 1; //loops track back to 1 because we don't have more than 22 tracks to play
		setTrack(); //update the music
		playMusic();
	}

	/**
	 * disposes the music instance to save memory
	 */
	public void dispose() {
		music.dispose();
	}
}
