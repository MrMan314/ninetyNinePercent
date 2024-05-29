package com.ninetyninepercentcasino.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class SFXManager {
    private static final ArrayList<Sound> cardSlideSounds = new ArrayList<>();
    public SFXManager() {

    }
    public static void prepare(){
        for(int i = 1; i <= 8; i++){
            cardSlideSounds.add(Gdx.audio.newSound(Gdx.files.internal("Sound/CasinoAudio/cardSlide" + i + ".ogg")));
        }
    }
    public static void playSlideSound(){
        cardSlideSounds.get(MathUtils.random(cardSlideSounds.size()-1)).play();
    }
}
