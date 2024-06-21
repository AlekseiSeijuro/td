package com.mygdx.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Elements {

    public static int NONE=100, PYRO=101, ELECTRO=102, GIDRO=103, CRIO=104;
    private static Texture elementsImg;


    public static void initTexture(Texture t){
        elementsImg=t;
    }

    public static TextureRegion getPyroTexture(){
        return new TextureRegion(elementsImg, 0, 0, 75, 70);
    }

    public static TextureRegion getGidroTexture(){
        return new TextureRegion(elementsImg, 75, 0, 75, 70);
    }

    public static TextureRegion getElectroTexture(){
        return new TextureRegion(elementsImg, 0, 70, 75, 70);
    }

    public static TextureRegion getCrioTexture(){
        return new TextureRegion(elementsImg, 75, 70, 75, 70);
    }
}
