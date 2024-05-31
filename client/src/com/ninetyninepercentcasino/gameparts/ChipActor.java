package com.ninetyninepercentcasino.gameparts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.ninetyninepercentcasino.game.SFXManager;
import com.ninetyninepercentcasino.game.gameparts.Chip;

public class ChipActor extends Actor {
    private final Chip chip;
    private ChipActor chipUnderneath;
    private ChipActor chipAbove;
    private final Sprite sprite;
    private final static float POP_DISTANCE = 10;
    private final static float CHIP_DISTANCE = 20; //distance between each chip in a stack
    private final static float DETACH_DISTANCE = 40; //distance between chips where they will detach
    private final static float ATTACH_DISTANCE = 20; //distance between chips where they will attach
    private boolean popped = false;
    public ChipActor(Chip chip){
        this.chip = chip;
        chipUnderneath = null;
        chipAbove = null;
        sprite = new Sprite(findTexture());
        sprite.setSize(192, 192 * ((float) 72/128));
        setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight());
        sprite.setPosition(getX(), getY());
        addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer == -1) pop();
                SFXManager.playStackSound();
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer == -1) unpop();
                System.out.println("EXITED CHIP");
            }
        });
        addListener(new DragListener(){
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                moveBy(x - getWidth() / 2, y - getHeight()/2);
                if(chipUnderneath != null){
                    setZIndex(chipUnderneath.getZIndex()+1);
                }
                if(chipUnderneath != null && Math.sqrt(Math.pow(chipUnderneath.getX()-getX(), 2) + Math.pow(chipUnderneath.getY()-getY(), 2)) >= DETACH_DISTANCE && pointer == 0){
                    detach();
                    System.out.println("DETACHED: " + isTopChip());
                }
                else if(chipUnderneath == null && pointer == 0){
                    for(Actor actor : getStage().getActors()){
                        if(actor instanceof ChipActor){
                            ChipActor chipUnder = (ChipActor)actor;
                            if(chipUnder.isTopChip() && chipUnder != event.getTarget() && Math.sqrt(Math.pow(chipUnder.getX()-getX(), 2) + Math.pow(chipUnder.getY()-getY(), 2)) < ATTACH_DISTANCE){
                                ChipActor searcher = chipUnder;
                                boolean isInStack = false;
                                while(searcher.getChipAbove() != null){
                                    ChipActor temp = searcher.getChipAbove();
                                    if(temp == chipUnder) {
                                        isInStack = true;
                                        break;
                                    }
                                    searcher = temp;
                                }
                                if(!isInStack){
                                    attachToChip(chipUnder);
                                    System.out.println("ATTACHED UNDER");
                                    SFXManager.playStackSound();
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    public ChipActor getChipAbove() {
        return chipAbove;
    }

    public ChipActor getChipUnderneath() {
        return chipUnderneath;
    }
    public void setChipUnderneath(ChipActor chipUnderneath){
        this.chipUnderneath = chipUnderneath;
    }
    public void setChipAbove(ChipActor chipAbove){
        this.chipAbove = chipAbove;
    }
    /**
     * attaches the chip to a chip underneath it
     */
    public void attachToChip(ChipActor chipUnderneath){
        this.chipUnderneath = chipUnderneath;
        chipUnderneath.setChipAbove(this);
    }
    public void attachChipToThis(ChipActor chipAbove){
        this.chipAbove = chipAbove;
        chipAbove.setChipUnderneath(this);
    }
    public void clearChipOver(){
        chipAbove = null;
    }
    public void detach(){
        chipUnderneath.clearChipOver();
        chipUnderneath = null;
    }
    public void draw(Batch batch, float parentAlpha){
        if(chipUnderneath == this){
            chipUnderneath = null;
        }
        if(chipAbove == this){
            chipAbove = null;
        }
        if(chipUnderneath != null) {
            sprite.setPosition(chipUnderneath.getX(), chipUnderneath.getY() + CHIP_DISTANCE);
            setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        }
        if(chipAbove != null && !chipAbove.isTopChip() && chipAbove.getChipAbove() == this) chipAbove.clearChipOver();
        if(popped) batch.draw(sprite, getX(), getY()+POP_DISTANCE, sprite.getWidth(), sprite.getHeight());
        else batch.draw(sprite, getX(), getY(), sprite.getWidth(), sprite.getHeight());
    }

    private TextureRegion findTexture(){
        Texture chips = new Texture("GameAssets/Isometric/Chips/ChipsA_Outline_Flat_Small-128x72.png");
        int x = 0;
        int y = 0;
        double chipValue = chip.getValue();
        if(chipValue <= 1) {
            x = 0;
        }
        else if(chipValue <= 5){
            x = 128;
        }
        else if(chipValue <= 10){
            x = 128*2;
        }
        else if(chipValue <= 25){
            x = 128*3;
        }
        else if(chipValue <= 100){
            x = 128*4;
        }
        return new TextureRegion(chips, x+20, y+8, 88, 56);
    }
    public void pop(){
        popped = true;
        if(!isTopChip() && !chipAbove.isPopped()) chipAbove.pop();
    }
    public void unpop(){
        popped = false;
        if(!isTopChip() && chipAbove.isPopped()) chipAbove.unpop();
    }
    public boolean isTopChip(){
        return chipAbove == null;
    }
    public boolean isPopped(){
        return popped;
    }
}
