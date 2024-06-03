package com.ninetyninepercentcasino.gameparts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.ninetyninepercentcasino.game.SFXManager;
import com.ninetyninepercentcasino.game.gameparts.Chip;

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

	private boolean popped = false;

	public ChipActor(Chip chip){
		this.chip = chip;
		chipBelow = null;
		chipAbove = null;
		sprite = new Sprite(findTexture());
		sprite.setSize(192*SCALE_FACTOR, 192 * ((float) 72/128) * SCALE_FACTOR);
		setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight());
		sprite.setPosition(getX(), getY());
		addListener(new ClickListener(){
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
				if(pointer == -1) pop();
			}
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
				if(pointer == -1) unpop();
				SFXManager.playChipLaySound();
			}
		});
		addListener(new DragListener(){
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				if(chipBelow != null){
					setZIndex(chipBelow.getZIndex()+1);
					focusStack(chipBelow.getZIndex()+2);
				}
				else {
					setZIndex(getParent().getChildren().size);
					focusStack(getParent().getChildren().size+1);
				}
				if(isTopChip()) setZIndex(getParent().getChildren().size);
				moveBy(x - getWidth() / 2, y - getHeight() / 2);
				sprite.translate(x - getWidth()/2, y - getHeight()/2);
				if(chipBelow != null && Math.sqrt(Math.pow(chipBelow.getX()-getX(), 2) + Math.pow(chipBelow.getY()-getY(), 2)) >= DETACH_DISTANCE && pointer == 0){
					setZIndex(chipBelow.getZIndex()+1);
					focusStack(chipBelow.getZIndex()+2);
					detach();
					SFXManager.playChipGrabSound();
				}
				else if(chipBelow == null && pointer == 0){
					for(Actor actor : getParent().getChildren()){
						if(actor instanceof ChipHolder){
							ChipHolder holder = (ChipHolder)actor;
							Vector2 distance = new Vector2(holder.getX() - getX(), holder.getY() - getY());
							if(distance.len() < ATTACH_DISTANCE + 20 && holder.isTopChip()){
								attachToChip(holder);
								SFXManager.playChipLaySound();
							}
						}
						else if(actor instanceof ChipActor){
							ChipActor chipUnder = (ChipActor)actor;
							Vector2 distance = new Vector2(chipUnder.getX() - getX(), chipUnder.getY() - getY());
							if(chipUnder.isTopChip() && chipUnder != event.getTarget() && distance.len() < ATTACH_DISTANCE){
								if(!isInStackAbove(chipUnder) && !isInStackBelow(chipUnder)){
									attachToChip(chipUnder);
									SFXManager.playChipLaySound();
								}
							}
						}
					}
				}
			}
		});
	}

	public ChipActor() {
	}
	public void setGroup(ChipGroup chipGroup){
		super.setParent(chipGroup);
	}
	public ChipActor getChipAbove() {
		return chipAbove;
	}

	public ChipActor getChipBelow() {
		return chipBelow;
	}
	public void setChipBelow(ChipActor chipBelow){
		this.chipBelow = chipBelow;
	}
	public void setChipAbove(ChipActor chipAbove){
		this.chipAbove = chipAbove;
	}
	/**
	 * attaches the chip to a chip underneath it
	 */
	public void attachToChip(ChipActor chipBelow){
		this.chipBelow = chipBelow;
		chipBelow.setChipAbove(this);
		unpop();
	}
	public void clearChipAbove(){
		chipAbove = null;
	}
	public void detach(){
		chipBelow.clearChipAbove();
		chipBelow = null;
	}
	public void draw(Batch batch, float parentAlpha){
		if(chipBelow != null) {
			sprite.setPosition(chipBelow.getX(), chipBelow.getY() + CHIP_DISTANCE);
			setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
		}
		if(popped) batch.draw(sprite, getX(), getY()+POP_DISTANCE, sprite.getWidth(), sprite.getHeight());
		else batch.draw(sprite, getX(), getY(), sprite.getWidth(), sprite.getHeight());
	}

	private TextureRegion findTexture(){
		Texture chips = new Texture("GameAssets/Isometric/Chips/ChipsA_Outline_Flat_Small-128x72.png");
		int x = 0;
		int y = 0;
		double chipValue = chip.getValue();
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
	public void pop(){
		popped = true;
		if(!isTopChip()) chipAbove.pop();
	}
	public void unpop(){
		popped = false;
		if(!isTopChip()) chipAbove.unpop();
	}
	public boolean isTopChip(){
		return chipAbove == null;
	}
	public boolean isInStackAbove(ChipActor target){
		if(target == this) return true;
		if(chipAbove != null) return chipAbove.isInStackAbove(target);
		return false;
	}
	public boolean isInStackBelow(ChipActor target){
		if(target == this) return true;
		if(chipBelow != null) return chipBelow.isInStackBelow(target);
		return false;
	}
	public void focusStack(int z){
		if(chipAbove != null) {
			chipAbove.setZIndex(z);
			chipAbove.focusStack(z+1);
		}
	}
	public double calculate(){
		if(chipAbove != null) return chipAbove.calculate() + chip.getValue();
		else return chip.getValue();
	}
}
