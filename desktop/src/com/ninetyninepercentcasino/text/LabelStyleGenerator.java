package com.ninetyninepercentcasino.text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * utility class to retrieve LabelStyles
 * @author Grant Liang
 */
public class LabelStyleGenerator {
	public BitmapFont leagueGothicFont;
	private FreeTypeFontGenerator generator;
	private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

	/**
	 * initializes a new label style generator
	 */
	public LabelStyleGenerator() {
		leagueGothicFont = new BitmapFont();
		generator = new FreeTypeFontGenerator(Gdx.files.internal("League-Gothic/LeagueGothic-Regular.ttf"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
	}
	/**
	 * @param size the font size
	 * @return a LabelStyle with the leagueGothic font
	 */
	public Label.LabelStyle getLeagueGothicLabelStyle(int size) {
		Label.LabelStyle labelStyle = new Label.LabelStyle();
		parameter.size = size; //set the font size to the requested size
		leagueGothicFont = generator.generateFont(parameter);
		labelStyle.font = leagueGothicFont;
		generator.dispose(); //dispose of the generator to avoid memory leaks
		return labelStyle;
	}
}
