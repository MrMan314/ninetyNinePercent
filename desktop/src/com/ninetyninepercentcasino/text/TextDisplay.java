package com.ninetyninepercentcasino.text;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class TextDisplay extends Label {
	private static LabelStyleGenerator labelStyleGenerator = new LabelStyleGenerator();
	public TextDisplay(CharSequence text, int fontSize) {
		super(text, labelStyleGenerator.getLeagueGothicLabelStyle(fontSize));
	}
}
