package com.ninetyninepercentcasino.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.audio.Music;

public class MusicManager implements Music.OnCompletionListener {
    private int currentTrack = MathUtils.random(22)+1;
    private Music music;
    public MusicManager(){
        setTrack();
        music.setOnCompletionListener(this);
    }
    public void playMusic(){
        music.play();
    }
    public void pauseMusic(){
        music.pause();
    }
    private void setTrack(){
        music = Gdx.audio.newMusic(Gdx.files.internal("Music/Track" + currentTrack + ".mp3"));
    }
    public void setVolume(float volume){
        music.setVolume(volume);
    }
    @Override
    public void onCompletion(Music music) {
        currentTrack++;
        if(currentTrack > 23) currentTrack = 1;
        setTrack();
        playMusic();
    }
    public void dispose(){
        music.dispose();
    }
}
