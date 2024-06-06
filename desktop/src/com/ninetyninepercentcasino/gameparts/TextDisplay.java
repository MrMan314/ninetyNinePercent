package com.ninetyninepercentcasino.gameparts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Null;

public class TextDisplay extends Label {

	public TextDisplay(CharSequence text, Skin skin) {
		super(text, skin);
	}
}
