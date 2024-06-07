package com.ninetyninepercentcasino.gameparts;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.ninetyninepercentcasino.game.gameparts.Chip;

import java.util.ArrayList;

/**
 * manages a group of chips that can interact with one another
 * includes chip holders and normal chips
 * @author Grant Liang
 */
public class ChipGroup extends Group {
	private static final int STACK_SIZE = 50; //the maximum height of each stack
	private float spawnX; //the x spawn location of the next chip stack
	private float spawnY; //the y spawn location of the next chip stack
	private float holderSpawnX; //the x spawn location of the next chip holder
	private float holderSpawnY; //the y spawn location of the next chip holder
	private ArrayList<ChipHolder> holders;

	/**
	 * initializes and spawns a group of chips as evenly as possible
	 * @param totalValue the total value of the chips to spawn
	 * @param numHolders the number of chip holders
	 */
	public ChipGroup(int totalValue, int numHolders, float spawnX, float spawnY, float holderSpawnX, float holderSpawnY){
		holders = new ArrayList<>();
		int whiteChips = 0;
		int redChips = 0;
		int blueChips = 0;
		int greenChips = 0;
		int blackChips = 0;
		while(totalValue > 0){
			if(totalValue >= 2500){
				totalValue -= 100;
				blackChips++;
			}
			else if(totalValue >= 141){
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
		addChips(1, whiteChips);
		addChips(5, redChips);
		addChips(10, blueChips);
		addChips(25, greenChips);
		addChips(100, blackChips);
		setupHolders(numHolders);
	}

	public ChipGroup(int whiteChips, int redChips, int blueChips, int greenChips, int blackChips, int numHolders, float spawnX, float spawnY, float holderSpawnX, float holderSpawnY){
		holders = new ArrayList<>();
		spawnX = 0;
		spawnY = 0;
		addChips(1, whiteChips);
		addChips(5, redChips);
		addChips(10, blueChips);
		addChips(25, greenChips);
		addChips(100, blackChips);
		setupHolders(numHolders);
	}

	/**
	 * sets up the holders, spawning them from left to right with no overlap
	 * @param numHolders the number of holders to spawn
	 */
	private void setupHolders(int numHolders){
		for(int i = 0; i < numHolders; i++){
			ChipHolder chipHolder = new ChipHolder();
			chipHolder.setPosition(holderSpawnX, holderSpawnY);
			addActor(chipHolder);
			holders.add(chipHolder);
			holderSpawnX += chipHolder.getWidth(); //move the spawn location over by the width of the chip so the next holder spawns to the right of this one
		}
	}

	/**
	 * adds chips do this group
	 * chips from the same addChips method are stacked on top of one another initially, but do not share any relation otherwise
	 * @param value the value of each chip in the stack
	 * @param numChips the number of chips to spawn
	 */
	public void addChips(int value, int numChips){
		int leftInStack = STACK_SIZE;
		while(numChips > 0){
			ChipActor chipBelow = new ChipActor(new Chip(value));
			chipBelow.setPosition(spawnX, spawnY);
			addActor(chipBelow);
			leftInStack--;
			numChips--;
			while(leftInStack > 0 && numChips > 0){
				ChipActor chipAbove = new ChipActor(new Chip(value));
				addActor(chipAbove);
				chipAbove.attachToChip(chipBelow);
				chipBelow = chipAbove;
				leftInStack--;
				numChips--;
			}
			spawnX += chipBelow.getWidth();
			leftInStack = STACK_SIZE;
		}
	}

	/**
	 * @return the value of all the chips on the chipHolders
	 */
	public int calculate(){
		int total = 0;
		for(ChipHolder holder : holders){
			total += holder.calculate();
		}
		return total;
	}

	/**
	 * disables all the chips on the chipHolders of this group so the client can no longer interact with them
	 */
	public void disableChipsHeld(){
		for(ChipHolder holder : holders){
			holder.disable();
		}
	}
	/**
	 * enables all the chips on the chipHolders of this group so the client can interact with them
	 */
	public void enableChipsHeld(){
		for(ChipHolder holder : holders){
			holder.enable();
		}
	}
}
