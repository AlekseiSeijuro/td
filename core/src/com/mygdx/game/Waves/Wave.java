package com.mygdx.game.Waves;

import java.util.ArrayList;

public class Wave {
    public static int ORC=1001, GOBLIN=1002, MINOTAUR=1003, REDDRAGON=1004;
    public static int SLOW=2000, MEDIUM=1000, FAST=500;

    private ArrayList<WavePart> waveParts;

    public Wave(){
        this.waveParts=new ArrayList<WavePart>();
    }

    public void addWavePart(WavePart newWavePart){
        waveParts.add(newWavePart);
    }

    public int getWavePartsCount(){
        return waveParts.size();
    }

    public WavePart getWavePart(int i){
        return waveParts.get(i);
    }
}
