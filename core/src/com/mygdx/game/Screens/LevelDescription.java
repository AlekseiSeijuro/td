package com.mygdx.game.Screens;

import com.mygdx.game.Waves.Wave;

import java.awt.*;

public class LevelDescription {
    public int startGold;
    public Point[] dataRoutePoints;

    public Point[] dataTowerSpotsPoints;

    Wave waves[];

    LevelDescription(int gold, Point[] routePoints, Wave[] waves, Point[] towerSpots){
        startGold=gold;
        dataRoutePoints=routePoints;
        this.waves=waves;
        this.dataTowerSpotsPoints=towerSpots;

    }

}
