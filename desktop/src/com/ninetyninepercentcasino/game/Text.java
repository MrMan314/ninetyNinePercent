package com.ninetyninepercentcasino.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * utility class to retrieve LabelStyles
 * @author Grant Liang
 */
public class Text {
	public BitmapFont leagueGothicFont;
	private FreeTypeFontGenerator generator;
	private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	public Text() {
		leagueGothicFont = new BitmapFont();
		generator = new FreeTypeFontGenerator(Gdx.files.internal("League-Gothic/LeagueGothic-Regular.ttf"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
	}
	/**
	 * @param size the font size
	 * @return a LabelStyle with the leagueGothic font
	 */
	public Label.LabelStyle getLeagueGothicLabelStyle(int size){
		Label.LabelStyle labelStyle = new Label.LabelStyle();
		parameter.size = size;
		leagueGothicFont = generator.generateFont(parameter);
		labelStyle.font = leagueGothicFont;
		generator.dispose();
		return labelStyle;
	}
}
