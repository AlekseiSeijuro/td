package com.mygdx.game.Towers.Bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Towers.Bullets.Bullet;
import com.mygdx.game.Towers.Elements;
import com.mygdx.game.Units.Monster;

public class PyroBullet extends Bullet {

    private static Texture textureImg;
    public PyroBullet(float x, float y, float damage, Monster target){
        super(x, y, damage, target);
        this.setSpeed(3);
        this.setElement(Elements.PYRO);

        int spriteW=10, spriteH=10;
        this.spriteNow=new TextureRegion(textureImg,0,0, spriteW, spriteH);
    }

    public static void initTexture(Texture t){
        textureImg=t;
    }
}
