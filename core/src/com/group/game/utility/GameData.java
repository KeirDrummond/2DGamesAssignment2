package com.group.game.utility;

/**
 * Created by gerard on 23/04/2017.
 */

public class GameData {
    private float time;
    private int ammo;
    private String playerName;
    private static GameData INSTANCE;

    private GameData(){}

    public static GameData getInstance(){
        if(INSTANCE==null){
            INSTANCE = new GameData();}
        return INSTANCE;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time=time;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public void resetGameData(){
        time=0;
        ammo=0;
        playerName="";
    }
}
