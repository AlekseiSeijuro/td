package com.mygdx.game.Towers.Bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Towers.Elements;
import com.mygdx.game.Units.Monster;

import static java.lang.Math.abs;

public class ElectroBullet extends Bullet{
    private static Texture textureImg;
    public ElectroBullet(float x, float y, float damage, Monster target){
        super(x, y, damage, target);
        this.setSpeed(5);
        this.setElement(Elements.ELECTRO);

        int spriteW=10, spriteH=10;
        this.spriteNow=new TextureRegion(textureImg,0,0, spriteW, spriteH);
    }

    public static void initTexture(Texture t){
        textureImg=t;
    }

}
