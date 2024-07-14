package com.ninetyninepercentcasino.text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * class that will display a help menu for the user
 * @author Grant Liang
 */
public class HelpDisplay extends Actor{
	private Sprite sprite; //the sprite that will model the visuals of the actor
	private Label helpText;

	/**
	 * initializes a new help display
	 * the help display is a question mark that when hovered over displays some help text
	 */
	public HelpDisplay(){
		sprite = new Sprite(new Texture("GameAssets/QuestionMark.png"));
		LabelStyleGenerator labelStyleGenerator = new LabelStyleGenerator();
		helpText = new Label("", labelStyleGenerator.getLeagueGothicLabelStyle(60));
		sprite.setSize(60, 60);
		setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight()); //set the hit bounds of the actor to match the sprite's width and height, but the actor's x and y coordinates.
		addListener(new ClickListener(){ //listens for enter and exit events
			/**
			 * called when the cursor enters the question mark
			 * @param event the associated InputEvent
			 * @param x cursor x location
			 * @param y cursor y location
			 * @param pointer the status of the cursor
			 * @param fromActor May be null.
			 */
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
				displayHelpMenu();
			}
			/**
			 * called when the cursor exits the question mark
			 * @param event the associated InputEvent
			 * @param x cursor x location
			 * @param y cursor y location
			 * @param pointer the status of the cursor
			 * @param fromActor May be null.
			 */
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
				hideHelpMenu();
			}
		});
	}
	/**
	 * draws the actor using the sprite
	 * @param batch the batch used to draw the actor
	 * @param parentAlpha The parent alpha, to be multiplied with this actor's alpha, allowing the parent's alpha to affect all
	 *		   children.
	 */
	public void draw(Batch batch, float parentAlpha){
		batch.setColor(Color.WHITE); //ensures this is drawn without any tint
		batch.draw(sprite, getX(), getY(), sprite.getWidth(), sprite.getHeight());
		helpText.setPosition(getX() + sprite.getWidth()/2, getY() - sprite.getHeight()*2);
		helpText.draw(batch, parentAlpha);
	}

	/**
	 * displays the text by setting the text on the label
	 */
	public void displayHelpMenu(){
		helpText.setText("Move chips around by clicking and dragging them.\nBet chips by placing them onto the gray chip holders\nRead up on the rules of blackjack if you are unfamiliar with the game");
	}

	/**
	 * hides the text on the label by setting the text to nothing
	 */
	public void hideHelpMenu(){
		helpText.setText("");
	}
}
