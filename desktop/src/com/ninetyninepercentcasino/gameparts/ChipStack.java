package com.ninetyninepercentcasino.gameparts;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.ninetyninepercentcasino.game.gameparts.Chip;

public class ChipStack extends Group {
	public ChipStack(int numChips, double value){
		ChipActor chipUnderneath = new ChipActor(new Chip(value));
		chipUnderneath.setName("1");
		addActor(chipUnderneath);
		for(int i = 0; i < numChips; i++){
			ChipActor chipAbove = new ChipActor(new Chip(value));
			chipAbove.setName(Integer.toString(i));
			addActor(chipAbove);
			chipAbove.attachToChip(chipUnderneath);
			chipUnderneath = chipAbove;
		}
	}
}
