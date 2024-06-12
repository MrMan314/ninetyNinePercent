package com.ninetyninepercentcasino.game
public class ChipActor extends Actor:
	private Chip chip
	protected ChipActor chipAbove
	private ChipActor chipBelow
	private Sprite sprite
	protected static final float SCALE_FACTOR = 0.8f
	private static final float POP_DISTANCE = 15
	protected static final float CHIP_DISTANCE = 12 * SCALE_FACTOR
	protected static final float DETACH_DISTANCE = 50
	protected static final float ATTACH_DISTANCE = 40
	public static final float CHIP_WIDTH = 120 * SCALE_FACTOR
	public static final float CHIP_HEIGHT = CHIP_WIDTH * (72f/128)
	private Vector2 cursorToOrigin
	private boolean rising = false
	private ChipActor chipBelowBeforeRise
	private boolean popped = false
	public ChipActor(Chip chip):
		setOrigin(getOriginX(), 0)
		this.chip = chip
		chipBelow = null
		chipAbove = null
		sprite = new Sprite(findTexture(chip.getValue()))
		sprite.setSize(CHIP_WIDTH, CHIP_HEIGHT)
		setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight())
		sprite.setPosition(getX(), getY())
		cursorToOrigin = new Vector2()
		addListener(new ClickListener():
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor):
				if (pointer == -1) pop()
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor):
				if (pointer == -1) unpop()
				SFXManager.playChipLaySound()
		)
		addListener(new DragListener():
			public void dragStart InputEvent event, float x, float y, int pointer:
				cursorToOrigin.x = getDragStartX() - getOriginX()
				cursorToOrigin.y = getDragStartY() - getOriginY()
				setOrigin(cursorToOrigin.x, cursorToOrigin.y)
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer):
				if chipBelow != null:
					focusStack(chipBelow.getZIndex()+1)
				else:
					focusStack(getParent().getChildren().size+1)
				moveBy(x - getWidth() / 2, y - getHeight() / 2 )
				sprite.translate(x - getWidth() / 2, y - getHeight() / 2)
				if chipBelow != null && Math.sqrt(Math.pow(chipBelow.getX()-getX(), 2) + Math.pow(chipBelow.getY()-getY(), 2)) >= DETACH_DISTANCE:
					detach()
					SFXManager.playChipGrabSound()
				else if chipBelow == null:
					for Actor actor in getParent().getChildren():
						if actor instanceof ChipHolder:
							ChipHolder holder = (ChipHolder)actor
							Vector2 distance = new Vector2(holder.getX() - getX(), holder.getY() - getY())
							if distance.len() < ATTACH_DISTANCE && holder.isTopChip():
								attachToChip(holder)
								SFXManager.playChipLaySound()
						else if (actor instanceof ChipActor){
							ChipActor chipAttachCandidate = (ChipActor)actor
							Vector2 distance = new Vector2(chipAttachCandidate.getX() - getX(), chipAttachCandidate.getY() - getY())
							if chipAttachCandidate.isTopChip() && chipAttachCandidate != event.getTarget() && distance.len() < ATTACH_DISTANCE:
								if !isInStack(chipAttachCandidate):
									attachToChip(chipAttachCandidate)
									SFXManager.playChipLaySound()
		)
	public ChipActor():
	public void setChipBelow(ChipActor chipBelow):
		this.chipBelow = chipBelow
	public void setChipAbove(ChipActor chipAbove):
		this.chipAbove = chipAbove
	public void attachToChip(ChipActor chipBelow):
		if (this.chipBelow != null) detach()
		this.chipBelow = chipBelow
		chipBelow.setChipAbove(this)
		unpop()
	public void clearChipAbove():
		chipAbove = null
	public void detach():
		chipBelow.clearChipAbove()
		chipBelow = null
	public void draw(Batch batch, float parentAlpha):
		batch.setColor(Color.WHITE)
		if chipBelow != null:
			sprite.setPosition(chipBelow.getX(), chipBelow.getY() + CHIP_DISTANCE)
			setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight())
		if popped:
			batch.draw(sprite, getX(), getY()+POP_DISTANCE, sprite.getWidth(), sprite.getHeight())
		else batch.draw(sprite, getX(), getY(), sprite.getWidth(), sprite.getHeight())
		if rising && chipBelowBeforeRise != null && Math.abs(getY() - chipBelowBeforeRise.getY()) > DETACH_DISTANCE * 4 && !chipBelowBeforeRise.isRising():
			chipBelowBeforeRise.floatAway()
	protected TextureRegion findTexture(int chipValue):
		Texture chips = new Texture("GameAssets/Isometric/Chips/ChipsA_Outline_Flat_Small-128x72.png")
		int x = 0
		int y = 0
		if chipValue <= 1:
			x = 0
		else if chipValue <= 5:
			x = 128
		else if chipValue <= 10:
			x = 128*2
		else if chipValue <= 25:
			x = 128*3
		else if chipValue <= 100:
			x = 128*4
		return new TextureRegion(chips, x+20, y+8, 88, 56)
	public void pop():
		popped = true
		if (!isTopChip()) chipAbove.pop()
	public void unpop():
		popped = false
		if (!isTopChip()) chipAbove.unpop()
	public void disable():
		setTouchable(Touchable.disabled)
		if (!isTopChip()) chipAbove.disable()
	public void enable():
		setTouchable(Touchable.enabled)
		if (!isTopChip()) chipAbove.enable()
	public boolean isTopChip():
		return chipAbove == null
	public boolean isInStack(ChipActor target):
		return isInStackAbove(target) || isInStackBelow(target)
	private boolean isInStackAbove(ChipActor target):
		if (target == this) return true
		if (chipAbove != null) return chipAbove.isInStackAbove(target)
		return false
	private boolean isInStackBelow(ChipActor target):
		if (target == this) return true
		if (chipBelow != null) return chipBelow.isInStackBelow(target)
		return false
	public void focusStack(int z):
		setZIndex(z)
		if chipAbove != null:
			chipAbove.focusStack(z+1)
	public void transferStackToGroup(ChipGroupBet chipGroupBet):
		if (this instanceof ChipHolder) println("holder added.")
		chipGroupBet.addActor(this)
		if (!isTopChip()) chipAbove.transferStackToGroup(chipGroupBet)
	public int calculate():
		if (chipAbove != null) return chipAbove.calculate() + chip.getValue()
		else return chip.getValue()
	public boolean isRising():
		return rising
	public void floatAway():
		if isTopChip():
			if chipBelow != null:
				chipBelowBeforeRise = chipBelow
				detach()
			rising = true
			MoveByAction floatAction = new MoveByAction()
			floatAction.setAmountY(getStage().getHeight() * 2f)
			floatAction.setDuration(3f)
			VisibleAction disappear = new VisibleAction()
			disappear.setVisible(false)
			addAction(sequence(floatAction, disappear))
		else chipAbove.floatAway()
