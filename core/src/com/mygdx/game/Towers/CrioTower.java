package com.mygdx.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Towers.Bullets.Bullet;
import com.mygdx.game.Towers.Bullets.CrioBullet;

import java.util.ArrayList;
import java.util.Date;

public class CrioTower extends Tower{
    private static Texture textureImg;
    public static int price=130;
    public CrioTower(float x, float y) {
        super(x, y);

        setAttackSpeed(1.5);
        setAttackRange(70);
        setDamage(15);

        int spriteW=21, spriteH=20;

        this.spriteNow=new TextureRegion(textureImg,0,0, spriteW, spriteH);
        this.attackPointX=x-3+ (float) spriteW /2;
        this.attackPointY=y-2+ (float) spriteH /2;
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
            bullets.add(new CrioBullet(this.attackPointX, this.attackPointY, this.getDamage(), this.getTarget()));
            return bullets;
        }
        else return null;
    };
}
