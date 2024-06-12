package com.ninetyninepercentcasino.audio
public class SFXManager:
	private static final ArrayList()
	private static final ArrayList()
	private static final ArrayList()
	private static final Sound chipGrabSound = Gdx.audio.newSound(Gdx.files.internal("Sound/CasinoAudio/chipsHandle5.ogg"))
	private static float volume = 1f
	public static void loadSFX():
		for int i = 1; i <= 8; i++:
			cardSlideSounds.add(Gdx.audio.newSound(Gdx.files.internal("Sound/CasinoAudio/cardSlide" + i + ".ogg")))
		for int i = 1; i <= 6; i++:
			chipStackSounds.add(Gdx.audio.newSound(Gdx.files.internal("Sound/CasinoAudio/chipsStack" + i + ".ogg")))
		for int i = 1; i <= 2; i++:
			chipLaySounds.add(Gdx.audio.newSound(Gdx.files.internal("Sound/CasinoAudio/chipLay" + i + ".ogg")))
	public static void playSlideSound():
		cardSlideSounds.get(MathUtils.random(cardSlideSounds.size()-1)).play(volume)
	public static void playStackSound():
		cardSlideSounds.get(MathUtils.random(chipStackSounds.size()-1)).play(volume)
	public static void playChipGrabSound():
		chipGrabSound.play(volume)
	public static void playChipLaySound():
		chipLaySounds.get(MathUtils.random(chipLaySounds.size()-1)).play(volume)
	public static float getVolume():
		return volume
	public static void setVolume(float volume):
		SFXManager.volume = volume
