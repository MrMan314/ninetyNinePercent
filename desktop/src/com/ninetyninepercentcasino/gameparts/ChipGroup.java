package com.ninetyninepercentcasino.gameparts;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.ninetyninepercentcasino.game.gameparts.Chip;

/**
 * models a group of chips that can be stacked on top of one another
 * includes chip holders and normal chips
 * @author Grant Liang
 */
public class ChipGroup extends Group {
	private ChipCalculator calculator;
	private float spawnX;
	private float spawnY;
	private float holderSpawnX;
	private float holderSpawnY;
	public ChipGroup(int whiteChips, int redChips, int blueChips, int greenChips, int blackChips, int numHolders){
		calculator = new ChipCalculator();
		spawnX = 0;
		spawnY = 0;
		addStack(1, whiteChips);
		addStack(5, redChips);
		addStack(10, blueChips);
		addStack(25, greenChips);
		addStack(100, blackChips);
		setupHolders(numHolders);
	}
	private void setupHolders(int numHolders){
		for(int i = 0; i < numHolders; i++){
			ChipHolder chipHolder = new ChipHolder();
			calculator.addChipHolder(chipHolder);
			chipHolder.setPosition(holderSpawnX, holderSpawnY);
			addActor(chipHolder);
			holderSpawnX += chipHolder.getWidth();
		}
	}
	public void addStack(int value, int numChips){
		ChipActor chipBelow = new ChipActor(new Chip(value));
		chipBelow.setPosition(spawnX, spawnY);
		chipBelow.setName("1");
		addActor(chipBelow);
		for(int i = 0; i < numChips; i++){
			ChipActor chipAbove = new ChipActor(new Chip(value));
			chipAbove.setName(Integer.toString(i));
			addActor(chipAbove);
			chipAbove.attachToChip(chipBelow);
			chipBelow = chipAbove;
		}
		spawnX += chipBelow.getWidth();
	}
	public double calculate(){
		return calculator.calculate();
	}
	public void disableChipsHeld(){
		for(Actor actor : getChildren().toArray()){
			if(actor instanceof ChipHolder){
				((ChipHolder)actor).disable();
			}
		}
	}
}
