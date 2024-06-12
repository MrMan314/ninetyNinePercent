package com.ninetyninepercentcasino.game
public class ChipGroup extends Group:
	private static final int STACK_SIZE = 50
	private float spawnX
	private float spawnY
	private float holderSpawnX
	private float holderSpawnY
	private ArrayList holders
	private ArrayList insuranceHolders
	public ChipGroup(int totalValue, int numHolders, float spawnX, float spawnY, float holderSpawnX, float holderSpawnY):
		holders = new ArrayList()
		insuranceHolders = new ArrayList()
		int whiteChips = 0
		int redChips = 0
		int blueChips = 0
		int greenChips = 0
		int blackChips = 0
		while totalValue > 0:
			if totalValue >= 2500:
				totalValue -= 100
				blackChips++
			else if totalValue >= 141:
				totalValue -= 141
				whiteChips++
				redChips++
				blueChips++
				greenChips++
				blackChips++
			else if totalValue >= 41:
				totalValue -= 41
				whiteChips++
				redChips++
				blueChips++
				greenChips++
			else if totalValue >= 16:
				totalValue -= 16
				whiteChips++
				redChips++
				blueChips++
			else if totalValue >= 6:
				totalValue -= 6
				whiteChips++
				redChips++
			else:
				totalValue--
				whiteChips++
		this.spawnX = spawnX - (calculateNumStacks(whiteChips, redChips, blueChips, greenChips, blackChips) * ChipActor.CHIP_WIDTH) / 2
		this.spawnY = spawnY
		this.holderSpawnX = holderSpawnX - (numHolders * ChipActor.CHIP_WIDTH) / 2
		this.holderSpawnY = holderSpawnY
		spawnChips(1, whiteChips)
		spawnChips(5, redChips)
		spawnChips(10, blueChips)
		spawnChips(25, greenChips)
		spawnChips(100, blackChips)
		setupHolders(numHolders, true)
	public ChipGroup(int whiteChips, int redChips, int blueChips, int greenChips, int blackChips, int numHolders, float spawnX, float spawnY, float holderSpawnX, float holderSpawnY):
		holders = new ArrayList()
		this.spawnX = spawnX - (calculateNumStacks(whiteChips, redChips, blueChips, greenChips, blackChips) * ChipActor.CHIP_WIDTH) / 2
		this.spawnY = spawnY
		this.holderSpawnX = holderSpawnX - (numHolders * ChipActor.CHIP_WIDTH) / 2
		this.holderSpawnY = holderSpawnY
		spawnChips(1, whiteChips)
		spawnChips(5, redChips)
		spawnChips(10, blueChips)
		spawnChips(25, greenChips)
		spawnChips(100, blackChips)
		setupHolders(numHolders, true)
	private void setupHolders(int numHolders, boolean isNormalHolder):
		for int i = 0; i < numHolders; i++:
			ChipHolder chipHolder = new ChipHolder()
			chipHolder.setPosition(holderSpawnX, holderSpawnY)
			addActor(chipHolder)
			if (isNormalHolder) holders.add(chipHolder)
			else insuranceHolders.add(chipHolder)
			holderSpawnX += chipHolder.getWidth()
	private void spawnChips(int value, int numChips):
		int leftInStack = STACK_SIZE
		while numChips > 0:
			ChipActor chipBelow = new ChipActor(new Chip(value))
			chipBelow.setPosition(spawnX, spawnY)
			addActor(chipBelow)
			leftInStack--
			numChips--
			while leftInStack > 0 && numChips > 0:
				ChipActor chipAbove = new ChipActor(new Chip(value))
				addActor(chipAbove)
				chipAbove.attachToChip(chipBelow)
				chipBelow = chipAbove
				leftInStack--
				numChips--
			spawnX += chipBelow.getWidth()
			leftInStack = STACK_SIZE
	public void spawnChip(int value, float x, float y):
		ChipActor chipActor = new ChipActor(new Chip(value))
		chipActor.setPosition(x, y)
		chipActor.setZIndex(Integer.MAX_VALUE)
		addActor(chipActor)
	public void rainChips(int totalValue):
		int whiteChips = 0
		int redChips = 0
		int blueChips = 0
		int greenChips = 0
		int blackChips = 0
		while totalValue > 0:
			if totalValue >= 2500:
				totalValue -= 100
				blackChips++
			else if totalValue >= 141:
				totalValue -= 141
				whiteChips++
				redChips++
				blueChips++
				greenChips++
				blackChips++
			else if totalValue >= 41:
				totalValue -= 41
				whiteChips++
				redChips++
				blueChips++
				greenChips++
			else if totalValue >= 16:
				totalValue -= 16
				whiteChips++
				redChips++
				blueChips++
			else if totalValue >= 6:
				totalValue -= 6
				whiteChips++
				redChips++
			else:
				totalValue--
				whiteChips++
	private int calculateNumStacks(int whiteChips, int redChips, int blueChips, int greenChips, int blackChips):
		int numStacks = 0
		while whiteChips > 0:
			whiteChips -= STACK_SIZE
			numStacks++
		while redChips > 0:
			redChips -= STACK_SIZE
			numStacks++
		while blueChips > 0:
			blueChips -= STACK_SIZE
			numStacks++
		while greenChips > 0:
			greenChips -= STACK_SIZE
			numStacks++
		while blackChips > 0:
			blackChips -= STACK_SIZE
			numStacks++
		return numStacks
	public int calculate():
		int total = 0
		for ChipHolder holder in holders:
			total += holder.calculate()
		for ChipHolder holder in insuranceHolders:
			total += holder.calculate()
		return total
	public ArrayList getHolders():
		return holders
	public ArrayList getInsuranceHolders():
		return insuranceHolders
	public void addInsuranceHolders(int numHolders, float spawnX, float spawnY):
		this.holderSpawnX = spawnX - (numHolders * ChipActor.CHIP_WIDTH) / 2
		this.holderSpawnY = spawnY
		setupHolders(numHolders, false)
