package com.ninetyninepercentcasino.gameparts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.ninetyninepercentcasino.game.SFXManager;
import com.ninetyninepercentcasino.game.gameparts.Chip;

public class ChipActor extends Actor {
	private final Chip chip;
	private ChipActor chipBelow;
	private ChipActor chipAbove;
	private final Sprite sprite;
	private final static float POP_DISTANCE = 10;
	private final static float CHIP_DISTANCE = 22; //distance between each chip in a stack
	private final static float DETACH_DISTANCE = 40; //distance between chips where they will detach
	private final static float ATTACH_DISTANCE = 20; //distance between chips where they will attach
	private boolean popped = false;
	private String name; //TODO remove this and methods
	public ChipActor(Chip chip){
		this.chip = chip;
		chipBelow = null;
		chipAbove = null;
		sprite = new Sprite(findTexture());
		sprite.setSize(192, 192 * ((float) 72/128));
		setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight());
		sprite.setPosition(getX(), getY());
		addListener(new ClickListener(){
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
				if(pointer == -1) pop();
				SFXManager.playStackSound();
			}
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
				if(pointer == -1) unpop();
				System.out.println("EXITED CHIP");
			}
		});
		addListener(new DragListener(){
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				if(chipBelow != null){
					setZIndex(chipBelow.getZIndex()+1);
					focusStack(chipBelow.getZIndex()+2);
				}
				if(isTopChip()) setZIndex(getParent().getChildren().size);
				moveBy(x - getWidth() / 2, y - getHeight()/2);
				if(chipBelow != null && Math.sqrt(Math.pow(chipBelow.getX()-getX(), 2) + Math.pow(chipBelow.getY()-getY(), 2)) >= DETACH_DISTANCE && pointer == 0){
					detach();
					SFXManager.playChipGrabSound();
					System.out.println("DETACHED: " + isTopChip());
				}
				else if(chipBelow == null && pointer == 0){
					for(Actor actor : getParent().getChildren()){
						if(actor instanceof ChipActor){
							ChipActor chipUnder = (ChipActor)actor;
							if(chipUnder.isTopChip() && chipUnder != event.getTarget() && Math.sqrt(Math.pow(chipUnder.getX()-getX(), 2) + Math.pow(chipUnder.getY()-getY(), 2)) < ATTACH_DISTANCE){
								if(!isInStackAbove(chipUnder) && !isInStackBelow(chipUnder)){
									attachToChip(chipUnder);
									System.out.println("ATTACHED UNDER");
									SFXManager.playChipLaySound();
								}
							}
						}
					}
				}
			}
		});
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
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
	public void attachToChip(ChipActor chipUnderneath){
		this.chipBelow = chipUnderneath;
		chipUnderneath.setChipAbove(this);
	}
	public void attachChipToThis(ChipActor chipAbove){
		this.chipAbove = chipAbove;
		chipAbove.setChipBelow(this);
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
		if(chipAbove != null && !chipAbove.isTopChip() && chipAbove.getChipAbove() == this) chipAbove.clearChipAbove();
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
		if(!isTopChip() && !chipAbove.isPopped()) chipAbove.pop();
	}
	public void unpop(){
		popped = false;
		if(!isTopChip() && chipAbove.isPopped()) chipAbove.unpop();
	}
	public boolean isTopChip(){
		return chipAbove == null;
	}
	public boolean isPopped(){
		return popped;
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
}
