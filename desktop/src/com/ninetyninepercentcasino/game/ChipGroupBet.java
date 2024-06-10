package com.ninetyninepercentcasino.game;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;

/**
 * Models a group of chips that has already been bet
 * The player cannot interact with chips in this group
 * @author Grant Liang
 */
public class ChipGroupBet extends Group {
	ArrayList<ChipHolder> holders;
	public ChipGroupBet(ArrayList<ChipHolder> holders){
		this.holders = holders;
		for(ChipHolder holder : holders){
			holder.transferStackToGroup(this);
			//holder.disable();
		}
	}
	public void floatAway(){
		for(ChipHolder holder : holders){
			holder.floatAway();
		}
	}
	public void stowHolders(){
		Table root = new Table();
		for (ChipHolder holder : holders) {
			root.add(holder);
		}
		root.setPosition(getStage().getWidth()/1.2f , getStage().getHeight()/59f);
		root.debug();
		getStage().addActor(root);
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
