package com.ninetyninepercentcasino.net;

/**
 * DTO for a client's chosen action
 * @author Grant Liang
 */
public class BJActionUpdate extends DTO {
    private BJAction chosenAction;
    public BJActionUpdate(BJAction action){
        chosenAction = action;
    }
    public BJAction getChosenAction(){
        return chosenAction;
    }
}
