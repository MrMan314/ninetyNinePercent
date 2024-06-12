package com.ninetyninepercentcasino.game
public class ChipGroupBet extends Group:
	ArrayList holders
	public ChipGroupBet(ArrayList holders):
		this.holders = new ArrayList(holders)
		for ChipHolder holder in holders:
			holder.transferStackToGroup(this)
			holder.disable()
		holders.clear()
	public void floatAway():
		for ChipHolder holder in holders:
			holder.floatAway()
	public void stowHolders():
		float x = getStage().getWidth()/1.6f
		float y = getStage().getHeight()/1.5f
		for ChipHolder holder in holders:
			holder.setPosition(x, y)
			x += holder.getWidth()
	public int calculate():
		int total = 0
		for ChipHolder holder in holders:
			total += holder.calculate()
		return total
