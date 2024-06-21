package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class DrawableObject {
    protected TextureRegion spriteNow;
    protected float posX, posY;

    public float getX() {
        return posX;
    }

    public float getY() {
        return posY;
    }

    public void draw(SpriteBatch batch){
        batch.draw(spriteNow, posX, posY);
    }
}
