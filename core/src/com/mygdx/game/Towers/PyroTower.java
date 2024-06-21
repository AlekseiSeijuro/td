package com.mygdx.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Towers.Bullets.Bullet;
import com.mygdx.game.Towers.Bullets.CrioBullet;
import com.mygdx.game.Towers.Bullets.PyroBullet;
import com.mygdx.game.Towers.Tower;

import java.util.ArrayList;
import java.util.Date;

public class PyroTower extends Tower {

    private static Texture textureImg;
    public static int price=100;
    public PyroTower( float x, float y) {
        super(x, y);

        setAttackSpeed(2);
        setAttackRange(100);
        setDamage(20);

        int spriteW=19, spriteH=35;

        this.spriteNow=new TextureRegion(textureImg,0,0, spriteW, spriteH);
        this.attackPointX=x-4+ (float) spriteW /2;
        this.attackPointY=y+ (float) spriteH /2;
    }

    public static void initTexture(Texture t){
        textureImg=t;
    }

    public ArrayList<Bullet> attack(){
        Date nowDate=new Date();
        long nowTime=nowDate.getTime();
        ArrayList<Bullet> bullets= new ArrayList<Bullet>();

        if((this.getTarget()!=null)&&(nowTime-this.getLastAttackTime()>1000/getAttackSpeed())) {
            setLastAttackTime(nowTime);
            bullets.add(new PyroBullet(this.attackPointX, this.attackPointY, this.getDamage(), this.getTarget()));
            return bullets;
        }
        else return null;
    };
}
