package com.ninetyninepercentcasino.gameparts;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.ninetyninepercentcasino.game.gameparts.Chip;

/**
 * manages a group of chips that can interact with one another
 * includes chip holders and normal chips
 * @author Grant Liang
 */
public class ChipGroup extends Group {
	private ChipCalculator calculator; //the calculator to calculate the value of all chips on chip holders
	private float spawnX; //the x spawn location of the next chip stack
	private float spawnY; //the y spawn location of the next chip stack
	private float holderSpawnX; //the x spawn location of the next chip holder
	private float holderSpawnY; //the y spawn location of the next chip holder

	/**
	 * initializes and spawns a group of chips as evenly as possible
	 * @param totalValue the total value of the chips to spawn
	 * @param numHolders the number of chip holders
	 */
	public ChipGroup(double totalValue, int numHolders, float spawnX, float spawnY, float holderSpawnX, float holderSpawnY){
		calculator = new ChipCalculator();
		int whiteChips = 0;
		int redChips = 0;
		int blueChips = 0;
		int greenChips = 0;
		int blackChips = 0;
		while(totalValue > 0){
			if(totalValue >= 141){
				totalValue -= 141;
				whiteChips++;
				redChips++;
				blueChips++;
				greenChips++;
				blackChips++;
			}
			else if(totalValue >= 41){
				totalValue -= 41;
				whiteChips++;
				redChips++;
				blueChips++;
				greenChips++;
			}
			else if(totalValue >= 16){
				totalValue -= 16;
				whiteChips++;
				redChips++;
				blueChips++;
			}
			else if(totalValue >= 6){
				totalValue -= 6;
				whiteChips++;
				redChips++;
			}
			else if(totalValue >= 1){
				totalValue--;
				whiteChips++;
			}
		}
		this.spawnX = spawnX;
		spawnY = 0;
		holderSpawnX = 0;
		holderSpawnY = 0;
		addStack(1, whiteChips);
		addStack(5, redChips);
		addStack(10, blueChips);
		addStack(25, greenChips);
		addStack(100, blackChips);
		setupHolders(numHolders);
	}

	public ChipGroup(int whiteChips, int redChips, int blueChips, int greenChips, int blackChips, int numHolders, float spawnX, float spawnY, float holderSpawnX, float holderSpawnY){
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

	/**
	 * sets up the holders, spawning them from left to right with no overlap
	 * @param numHolders the number of holders to spawn
	 */
	private void setupHolders(int numHolders){
		for(int i = 0; i < numHolders; i++){
			ChipHolder chipHolder = new ChipHolder();
			calculator.addChipHolder(chipHolder);
			chipHolder.setPosition(holderSpawnX, holderSpawnY);
			addActor(chipHolder);
			holderSpawnX += chipHolder.getWidth(); //move the spawn location over by the width of the chip so the next holder spawns to the right of this one
		}
	}

	/**
	 * adds a stack of chips to this group
	 * chips from the same addStack method are stacked on top of one another initially, but do not share any relation otherwise
	 * @param value the value of each chip in the stack
	 * @param numChips the number of chips to spawn
	 */
	public void addStack(int value, int numChips){
		ChipActor chipBelow = new ChipActor(new Chip(value));
		chipBelow.setPosition(spawnX, spawnY);
		chipBelow.setName("1");
		addActor(chipBelow);
		for(int i = 0; i < numChips-1; i++){
			ChipActor chipAbove = new ChipActor(new Chip(value));
			chipAbove.setName(Integer.toString(i));
			addActor(chipAbove);
			chipAbove.attachToChip(chipBelow);
			chipBelow = chipAbove;
		}
		spawnX += chipBelow.getWidth();
	}

	/**
	 * @return the value of all the chips on the chipHolders
	 */
	public double calculate(){
		return calculator.calculate();
	}

	/**
	 * disables all the chips on the chipHolders of this group so the client can no longer interact with them
	 */
	public void disableChipsHeld(){
		for(Actor actor : getChildren().toArray()){
			if(actor instanceof ChipHolder){
				((ChipHolder)actor).disable();
			}
		}
	}
}
