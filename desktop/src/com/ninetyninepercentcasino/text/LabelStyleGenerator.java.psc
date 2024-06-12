package com.ninetyninepercentcasino.text
public class LabelStyleGenerator:
	public BitmapFont leagueGothicFont
	private FreeTypeFontGenerator generator
	private FreeTypeFontGenerator.FreeTypeFontParameter parameter
	public LabelStyleGenerator():
		leagueGothicFont = new BitmapFont()
		generator = new FreeTypeFontGenerator(Gdx.files.internal("League-Gothic/LeagueGothic-Regular.ttf"))
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter()
	public Label.LabelStyle getLeagueGothicLabelStyle(int size):
		Label.LabelStyle labelStyle = new Label.LabelStyle()
		parameter.size = size
		leagueGothicFont = generator.generateFont(parameter)
		labelStyle.font = leagueGothicFont
		generator.dispose()
		return labelStyle
