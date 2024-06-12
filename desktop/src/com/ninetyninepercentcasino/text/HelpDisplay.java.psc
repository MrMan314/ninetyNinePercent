package com.ninetyninepercentcasino.text
public class HelpDisplay extends Actor{
	private Sprite sprite
	private Label helpText
	public HelpDisplay(){
		sprite = new Sprite(new Texture("GameAssets/QuestionMark.png"))
		LabelStyleGenerator labelStyleGenerator = new LabelStyleGenerator()
		helpText = new Label("", labelStyleGenerator.getLeagueGothicLabelStyle(60))
		sprite.setSize(60, 60)
		setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight())
		addListener(new ClickListener(){
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
				displayHelpMenu()
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
				hideHelpMenu()
		)
	public void draw(Batch batch, float parentAlpha){
		batch.setColor(Color.WHITE)
		batch.draw(sprite, getX(), getY(), sprite.getWidth(), sprite.getHeight())
		helpText.setPosition(getX() + sprite.getWidth()/2, getY() - sprite.getHeight()*2)
		helpText.draw(batch, parentAlpha)
	public void displayHelpMenu(){
		helpText.setText("Move chips around by clicking and dragging them.\nBet chips by placing them onto the gray chip holders\nRead up on the rules of blackjack if you are unfamiliar with the game")
	public void hideHelpMenu(){
		helpText.setText("")
