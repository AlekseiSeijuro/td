package com.mygdx.game.Waves;

public class WavePart {

    private int monsterType;
    private int monsterCount;
    private long spawnInterval;

    public WavePart(int monsterType, int monsterCount, long spawnInterval){
        this.monsterType=monsterType;
        this.monsterCount=monsterCount;
        this.spawnInterval=spawnInterval;
    }

    public int getMonsterType() {
        return monsterType;
    }

    public int getMonsterCount() {
        return monsterCount;
    }

    public long getSpawnInterval() {
        return spawnInterval;
    }
}
