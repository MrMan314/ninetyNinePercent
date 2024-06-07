package com.ninetyninepercentcasino.gameparts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.VisibleAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.ninetyninepercentcasino.game.SFXManager;
import com.ninetyninepercentcasino.game.gameparts.Chip;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * models an interactive, visible Chip
 * ChipActors can be dragged around and will stack on top of each other when close enough
 * @author Grant Liang
 */

public class ChipActor extends Actor {
	private Chip chip;
	protected ChipActor chipAbove;
	private ChipActor chipBelow;
	private Sprite sprite;

	protected final static float SCALE_FACTOR = 0.8f; //the chip texture is reduced by a factor of this
	private final static float POP_DISTANCE = 15; //the distance the chip will travel upwards when hovered over
	protected final static float CHIP_DISTANCE = 22 * SCALE_FACTOR; //distance between each chip in a stack
	protected final static float DETACH_DISTANCE = 50; //distance between chips where they will detach
	protected final static float ATTACH_DISTANCE = 40; //distance between chips where they will attach

	private boolean rising = false;
	private ChipActor chipBelowBeforeRise;
	private boolean popped = false;

	/**
	 * initializes a new ChipActor with the given Chip
	 * @param chip the Chip this will wrap
	 */
	public ChipActor(Chip chip){
		this.chip = chip;
		chipBelow = null; //in the beginning there will be no chip above or below this chip
		chipAbove = null;
		sprite = new Sprite(findTexture()); //update the sprite to the appropriate texture
		sprite.setSize(192*SCALE_FACTOR, 192 * ((float) 72/128) * SCALE_FACTOR);
		setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight());
		sprite.setPosition(getX(), getY());
		addListener(new ClickListener(){ //listens for cursor enter and exit events
			/**
			 * pops this ChipActor and the stack above it because the cursor has entered it
			 */
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
				if(pointer == -1) pop(); //only pops if the cursor is -1, meaning tha the mouse isn't down
			}
			/**
			 * unpops this ChipActor and the stack above it because the cursor has exited it
			 */
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
				if(pointer == -1) unpop(); //only unpops if the cursor is -1, meaning that the mouse isn't down
				SFXManager.playChipLaySound();
			}
		});
		addListener(new DragListener(){ //add a drag listener that will enable the chips to be dragged around by the mouse
			/**
			 * called when the mouse is dragging across the screen
			 * will update the chip's position and check for attachment/detachment parameters
			 */
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				if(chipBelow != null){ //focuses the chip being dragged, so it appears above other chips
					focusStack(chipBelow.getZIndex()+1);
				}
				else { //there no chip below this chip, so just focus the stack based on how many chips the ChipGroup has
					focusStack(getParent().getChildren().size+1);
				}
				moveBy(x - getWidth() / 2, y - getHeight() / 2); //moves the chip by the given x and y values
				sprite.translate(x - getWidth()/2, y - getHeight()/2); //translates the visual of the chip as well
				if(chipBelow != null && Math.sqrt(Math.pow(chipBelow.getX()-getX(), 2) + Math.pow(chipBelow.getY()-getY(), 2)) >= DETACH_DISTANCE){
					//this chip is more than DETACH_DISTANCE away from the chip below, so detach
					detach();
					SFXManager.playChipGrabSound();
				}
				else if(chipBelow == null){ //search for chips to attach to if there is no chip below this chip
					for(Actor actor : getParent().getChildren()){ //loop through each actor in the ChipGroup
						if(actor instanceof ChipHolder){
							ChipHolder holder = (ChipHolder)actor;
							Vector2 distance = new Vector2(holder.getX() - getX(), holder.getY() - getY());
							if(distance.len() < ATTACH_DISTANCE && holder.isTopChip()){
								//attach to the holder if the distance is small enough
								attachToChip(holder);
								SFXManager.playChipLaySound();
							}
						}
						else if(actor instanceof ChipActor){
							ChipActor chipAttachCandidate = (ChipActor)actor;
							Vector2 distance = new Vector2(chipAttachCandidate.getX() - getX(), chipAttachCandidate.getY() - getY());
							if(chipAttachCandidate.isTopChip() && chipAttachCandidate != event.getTarget() && distance.len() < ATTACH_DISTANCE){
								if(!isInStack(chipAttachCandidate)){
									//attach to the chip if the distance is small enough and if the chip isn't already in the same stack as this chip
									attachToChip(chipAttachCandidate);
									SFXManager.playChipLaySound();
								}
							}
						}
					}
				}
			}
		});
	}

	/**
	 * initializes a new chipActor
	 * this is for the subclass ChipHolder to use
	 */
	public ChipActor() {

	}

	/**
	 * sets the chip below this chip to chipBelow
	 * @param chipBelow the chip below this chip
	 */
	public void setChipBelow(ChipActor chipBelow){
		this.chipBelow = chipBelow;
	}

	/**
	 * sets the chip above this chip to chipAbove
	 * @param chipAbove the chip above this chip
	 */
	public void setChipAbove(ChipActor chipAbove){
		this.chipAbove = chipAbove;
	}
	/**
	 * attaches the chip to a chip underneath it
	 */
	public void attachToChip(ChipActor chipBelow){
		if(this.chipBelow != null) detach();
		this.chipBelow = chipBelow;
		chipBelow.setChipAbove(this);
		unpop();
	}

	/**
	 * clears the reference to the chip above this chip
	 */
	public void clearChipAbove(){
		chipAbove = null;
	}

	/**
	 * detaches this chip from the one below it
	 */
	public void detach(){
		chipBelow.clearChipAbove();
		chipBelow = null;
	}

	/**
	 * draws the chip, popped if necessary.
	 * chips will inherit the position of the chips underneath them
	 * @param batch the batch used to draw
	 * @param parentAlpha The parent alpha, to be multiplied with this actor's alpha, allowing the parent's alpha to affect all children.
	 */
	public void draw(Batch batch, float parentAlpha){
		if(chipBelow != null) {
			sprite.setPosition(chipBelow.getX(), chipBelow.getY() + CHIP_DISTANCE);
			setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
		}
		if(popped) batch.draw(sprite, getX(), getY()+POP_DISTANCE, sprite.getWidth(), sprite.getHeight());
		else batch.draw(sprite, getX(), getY(), sprite.getWidth(), sprite.getHeight());
		if(rising && chipBelowBeforeRise != null && Math.abs(getY() - chipBelowBeforeRise.getY()) > DETACH_DISTANCE * 4 && !chipBelowBeforeRise.isRising()){
			chipBelowBeforeRise.floatAway();
		}
	}

	/**
	 * finds the appropriately colored chip texture for this chip
	 * @return the TextureRegion corresponding to the Chip
	 */
	private TextureRegion findTexture(){
		Texture chips = new Texture("GameAssets/Isometric/Chips/ChipsA_Outline_Flat_Small-128x72.png");
		int x = 0;
		int y = 0;
		int chipValue = chip.getValue();
		if(chipValue <= 1) {
			x = 0;
		}
		else if(chipValue <= 5){
			x = 128;
		}
		else if(chipValue <= 10){
			x = 128*2;
		}
		else if(chipValue <= 25){
			x = 128*3;
		}
		else if(chipValue <= 100){
			x = 128*4;
		}
		return new TextureRegion(chips, x+20, y+8, 88, 56);
	}

	/**
	 * pops this chip up, and all chips above it
	 */
	public void pop(){
		popped = true;
		if(!isTopChip()) chipAbove.pop();
	}

	/**
	 * unpops this chip, and all chips above it
	 */
	public void unpop(){
		popped = false;
		if(!isTopChip()) chipAbove.unpop();
	}

	/**
	 * disables this chip and all chips above it, making the user unable to interact with it
	 */
	public void disable(){
		setTouchable(Touchable.disabled);
		if(!isTopChip()) chipAbove.disable();
	}
	/**
	 * enable this chip and all chips above it, making the user able to interact with it
	 */
	public void enable(){
		setTouchable(Touchable.enabled);
		if(!isTopChip()) chipAbove.enable();
	}

	/**
	 * @return if this is the top chip in a stack
	 */
	public boolean isTopChip(){
		return chipAbove == null;
	}

	/**
	 * checks if target is in the same stack as this
	 * @param target the ChipActor to be searched for
	 * @return if target is in the same stack as this
	 */
	public boolean isInStack(ChipActor target){
		return isInStackAbove(target) || isInStackBelow(target);
	}

	/**
	 * helper method that checks if target is in the stack above this chip
	 * @param target the ChipActor to be searched for
	 * @return if target is in the stack above this
	 */
	private boolean isInStackAbove(ChipActor target){
		if(target == this) return true;
		if(chipAbove != null) return chipAbove.isInStackAbove(target);
		return false;
	}
	/**
	 * helper method that checks if target is in the stack below this chip
	 * @param target the ChipActor to be searched for
	 * @return if target is in the stack below this
	 */
	private boolean isInStackBelow(ChipActor target){
		if(target == this) return true;
		if(chipBelow != null) return chipBelow.isInStackBelow(target);
		return false;
	}
	/**
	 * moves this chip and all chips above this chip to the front layer
	 */
	public void focusStack(int z){
		setZIndex(z);
		if(chipAbove != null) {
			chipAbove.focusStack(z+1);
		}
	}

	/**
	 * calculates the currency value of this chip and all chips above it
	 * @return the value of this chip and all chips above it
	 */
	public int calculate(){
		if(chipAbove != null) return chipAbove.calculate() + chip.getValue();
		else return chip.getValue();
	}
	public boolean isRising(){
		return rising;
	}
	public void floatAway(){
		if(isTopChip()){
			if(chipBelow != null) {
				chipBelowBeforeRise = chipBelow;
				detach();
			}
			rising = true;
			MoveByAction floatAction = new MoveByAction();
			floatAction.setAmountY(getStage().getHeight()*2f);
			floatAction.setDuration(3f);
			VisibleAction disappear = new VisibleAction();
			disappear.setVisible(false);
			addAction(sequence(floatAction, disappear));
		}
		else chipAbove.floatAway();
	}
}
