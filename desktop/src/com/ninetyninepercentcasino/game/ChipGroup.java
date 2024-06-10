package com.ninetyninepercentcasino.game;

import com.badlogic.gdx.scenes.scene2d.Group;

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
	private float holderSpawnX; //the left bound of the x spawn location of the next chip holder
	private float holderSpawnY; //the bottom bound of the y spawn location of the next chip holder
	private ArrayList<ChipHolder> holders;
	private ArrayList<ChipHolder> insuranceHolders;

	/**
	 * initializes and spawns a group of chips as evenly as possible
	 * @param totalValue the total value of the chips to spawn
	 * @param numHolders the number of chip holders
	 * @param spawnX the center of the x location the chips will be spawned at
	 * @param spawnY the bottom bound of the y location the chips will be spawned at
	 * @param holderSpawnX the center of the x location the holders will be spawned at
	 * @param holderSpawnY the bottom bound of the y location the holders will be spawned at
	 */
	public ChipGroup(int totalValue, int numHolders, float spawnX, float spawnY, float holderSpawnX, float holderSpawnY){
		holders = new ArrayList<>();
		insuranceHolders = new ArrayList<>();
		int whiteChips = 0;
		int redChips = 0;
		int blueChips = 0;
		int greenChips = 0;
		int blackChips = 0;
		//this while loop will try to equalize the number of each type of chip being spawned while keeping the total number of chips reasonable
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
			else {
				totalValue--;
				whiteChips++;
			}
		}
		this.spawnX = spawnX - (calculateNumStacks(whiteChips, redChips, blueChips, greenChips, blackChips) * ChipActor.CHIP_WIDTH) / 2; //calculates the left bound of the x position of the leftmost chip stack
		this.spawnY = spawnY;
		this.holderSpawnX = holderSpawnX - (numHolders * ChipActor.CHIP_WIDTH) / 2; //calculates the left bound of the x position of the leftmost holder
		this.holderSpawnY = holderSpawnY;
		//add chips to the chip groups
		spawnChips(1, whiteChips);
		spawnChips(5, redChips);
		spawnChips(10, blueChips);
		spawnChips(25, greenChips);
		spawnChips(100, blackChips);
		setupHolders(numHolders, true);
	}

	public ChipGroup(int whiteChips, int redChips, int blueChips, int greenChips, int blackChips, int numHolders, float spawnX, float spawnY, float holderSpawnX, float holderSpawnY){
		holders = new ArrayList<>();
		this.spawnX = spawnX - (calculateNumStacks(whiteChips, redChips, blueChips, greenChips, blackChips) * ChipActor.CHIP_WIDTH) / 2; //calculates the left bound of the x position of the leftmost chip stack
		this.spawnY = spawnY;
		this.holderSpawnX = holderSpawnX - (numHolders * ChipActor.CHIP_WIDTH) / 2; //calculates the left bound of the x position of the leftmost holder
		this.holderSpawnY = holderSpawnY;
		spawnChips(1, whiteChips);
		spawnChips(5, redChips);
		spawnChips(10, blueChips);
		spawnChips(25, greenChips);
		spawnChips(100, blackChips);
		setupHolders(numHolders, true);
	}

	/**
	 * sets up the holders, spawning them from left to right with no overlap
	 * @param numHolders the number of holders to spawn
	 */
	private void setupHolders(int numHolders, boolean isNormalHolder){
		for(int i = 0; i < numHolders; i++){
			ChipHolder chipHolder = new ChipHolder();
			chipHolder.setPosition(holderSpawnX, holderSpawnY);
			addActor(chipHolder);
			if(isNormalHolder) holders.add(chipHolder);
			else insuranceHolders.add(chipHolder);
			holderSpawnX += chipHolder.getWidth(); //move the spawn location over by the width of the chip so the next holder spawns to the right of this one
		}
	}

	/**
	 * spawns chips in this group
	 * chips from the same spawnChips method are stacked on top of one another initially, but do not share any relation otherwise
	 * @param value the value of each chip in the stack
	 * @param numChips the number of chips to spawn
	 */
	private void spawnChips(int value, int numChips){
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
				chipAbove.attachToChip(chipBelow); //attach the newly spawned chip to the chip previously spawned to make a stack
				chipBelow = chipAbove;
				leftInStack--;
				numChips--;
			}
			spawnX += chipBelow.getWidth(); //increment spawnX to spawn the next stack to the right of this stack
			leftInStack = STACK_SIZE;
		}
	}
	/**
	 * spawns a chip into this group
	 * @param value the value of the chip
	 * @param x the x location of the spawned chip
	 * @param y the y location of the spawned chip
	 */
	public void spawnChip(int value, float x, float y){
		ChipActor chipActor = new ChipActor(new Chip(value));
		chipActor.setPosition(x, y);
		chipActor.setZIndex(Integer.MAX_VALUE);
		addActor(chipActor);

	}

	/**
	 * calculates the number of stacks that will be spawned given a certain number of each chip
	 * @return the number of stacks that will be spawned
	 */
	private int calculateNumStacks(int whiteChips, int redChips, int blueChips, int greenChips, int blackChips){
		int numStacks = 0;
		while(whiteChips > 0){
			whiteChips -= STACK_SIZE;
			numStacks++;
		}
		while(redChips > 0){
			redChips -= STACK_SIZE;
			numStacks++;
		}
		while(blueChips > 0){
			blueChips -= STACK_SIZE;
			numStacks++;
		}
		while(greenChips > 0){
			greenChips -= STACK_SIZE;
			numStacks++;
		}
		while(blackChips > 0){
			blackChips -= STACK_SIZE;
			numStacks++;
		}
		return numStacks;
	}
	/**
	 * @return the value of all the chips on the chipHolders
	 */
	public int calculate(){
		int total = 0;
		for(ChipHolder holder : holders){
			total += holder.calculate();
		}
		for(ChipHolder holder : insuranceHolders){
			total += holder.calculate();
		}
		return total;
	}

	/**
	 * @return the initial bet holders in the chip group
	 */
	public ArrayList<ChipHolder> getHolders(){
		return holders;
	}

	/**
	 * @return the insurance holders in the chip group
	 */
	public ArrayList<ChipHolder> getInsuranceHolders(){
		return insuranceHolders;
	}
	public void addInsuranceHolders(int numHolders, float spawnX, float spawnY){
		this.holderSpawnX = spawnX - (numHolders * ChipActor.CHIP_WIDTH) / 2;
		this.holderSpawnY = spawnY;
		setupHolders(numHolders, false);
	}
}
