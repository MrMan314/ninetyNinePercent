package com.ninetyninepercentcasino.game;

import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;

/**
 * Models a group of chips that has already been bet
 * The player cannot interact with chips in this group
 * @author Grant Liang
 */
public class ChipGroupBet extends Group {
	ArrayList<ChipHolder> holders;

	/**
	 * initializes a new group of chips that have been bet
	 * @param holders the holders that held the chips that were bet
	 */
	public ChipGroupBet(ArrayList<ChipHolder> holders){
		this.holders = new ArrayList<>(holders);
		for(ChipHolder holder : holders){
			holder.transferStackToGroup(this); //transfer all chips on the holders to this group
			holder.disable(); //disable all bet chips
		}
		holders.clear();
	}

	/**
	 * makes all chips on this group's chip holders fly away into the sky
	 */
	public void floatAway(){
		for(ChipHolder holder : holders){
			holder.floatAway();
		}
	}

	public void stowHolders(){
		float x = getStage().getWidth()/1.6f;
		float y = getStage().getHeight()/1.5f;
		for (ChipHolder holder : holders) {
			holder.setPosition(x, y);
			x += holder.getWidth();
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
}
