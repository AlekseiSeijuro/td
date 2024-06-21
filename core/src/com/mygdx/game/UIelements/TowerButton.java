package com.mygdx.game.UIelements;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class TowerButton extends ImageButton {
    private int towerType;
    public TowerButton(int x, int y, Drawable drawable, int towerType){
        super(drawable);
        this.towerType=towerType;
        this.setPosition(x, y);
    }

}
