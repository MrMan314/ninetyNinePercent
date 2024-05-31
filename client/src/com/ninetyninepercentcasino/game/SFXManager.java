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
    }

    /**
     * plays a random card slide sound
     */
    public static void playSlideSound(){
        cardSlideSounds.get(MathUtils.random(cardSlideSounds.size()-1)).play();
    }

    /**
     * plays a random chip stack sound
     */
    public static void playStackSound(){
        cardSlideSounds.get(MathUtils.random(chipStackSounds.size()-1)).play();
    }
}
