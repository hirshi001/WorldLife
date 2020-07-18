package com.hirshi001.billions.gamepieces.structures;

import com.hirshi001.billions.gamepieces.entities.GameMob;

import java.util.EventListener;

public interface EnterDoorEventListener extends EventListener {

    public void onEnteringDoor(GameMob mob);

}
