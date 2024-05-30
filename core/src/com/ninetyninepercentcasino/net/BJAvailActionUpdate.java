package com.ninetyninepercentcasino.net;

import java.util.HashMap;
import java.util.HashSet;

/**
 * DTO that the server uses to send the player what actions are available to them
 * @author Grant Liang
 */
public class BJAvailActionUpdate extends DTO {
    private HashMap<BJAction, Boolean> actions;
    private BJAction chosenAction;
    public BJAvailActionUpdate(HashMap<BJAction, Boolean> actions){
        this.actions = actions;
    }
    public HashMap<BJAction, Boolean> getActions(){
        return actions;
    }
    public HashSet<BJAction> getAvailableActions(){
        HashSet<BJAction> availableActions = new HashSet<>();
        for(BJAction action : actions.keySet()){
            if(actions.get(action)) availableActions.add(action);
        }
        return availableActions;
    }
}
