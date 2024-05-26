package com.ninetyninepercentcasino.net;

public class BJActionUpdate extends DTO {
    private BJAction chosenAction;
    public BJActionUpdate(BJAction action){
        chosenAction = action;
    }
    public BJAction getChosenAction(){
        return chosenAction;
    }
}
