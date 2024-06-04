package com.ninetyninepercentcasino.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

/**
 * Manages the sound effects (SFX) for the game
 * @author Grant Liang
 */
public class SFXManager {
	//the following array lists hold different variations of the same sound effect
	private static final ArrayList<Sound> cardSlideSounds = new ArrayList<>();
	private static final ArrayList<Sound> chipStackSounds = new ArrayList<>();
	private static final ArrayList<Sound> chipLaySounds = new ArrayList<>();
	private static final Sound chipGrabSound = Gdx.audio.newSound(Gdx.files.internal("Sound/CasinoAudio/chipsHandle5.ogg"));
	private static float volume = 1f;

	//no constructor needed because this is a utility class meant for only static methods

	/**
	 * loads the sound effects. must be called once within the game for the SFX to be loaded
	 */
	public static void loadSFX(){
		for(int i = 1; i <= 8; i++){
			cardSlideSounds.add(Gdx.audio.newSound(Gdx.files.internal("Sound/CasinoAudio/cardSlide" + i + ".ogg")));
		}
		for(int i = 1; i <= 6; i++){
			chipStackSounds.add(Gdx.audio.newSound(Gdx.files.internal("Sound/CasinoAudio/chipsStack" + i + ".ogg")));
		}
		for(int i = 1; i <= 3; i++){
			chipLaySounds.add(Gdx.audio.newSound(Gdx.files.internal("Sound/CasinoAudio/chipLay" + i + ".ogg")));
		}
	}

	/**
	 * plays a random card slide sound
	 */
	public static void playSlideSound(){
		cardSlideSounds.get(MathUtils.random(cardSlideSounds.size()-1)).play(volume);
	}

	/**
	 * plays a random chip stack sound
	 */
	public static void playStackSound(){
		cardSlideSounds.get(MathUtils.random(chipStackSounds.size()-1)).play(volume);
	}

	/**
	 * plays the chip grabbing sound
	 */
	public static void playChipGrabSound(){
		chipGrabSound.play(volume);
	}
	public static void playChipLaySound(){
		chipLaySounds.get(MathUtils.random(chipLaySounds.size()-1)).play(volume);
	}
	public static float getVolume(){
		return volume;
	}
	public static void setVolume(float volume){
		SFXManager.volume = volume;
	}
}
