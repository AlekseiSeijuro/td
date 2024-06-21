package com.mygdx.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.mygdx.game.Towers.Bullets.Bullet;
import com.mygdx.game.Towers.Bullets.ElectroBullet;
import com.mygdx.game.Units.Monster;

import java.util.ArrayList;
import java.util.Date;

public class ElectroTower extends Tower{
    private static Texture textureImg;
    private static ArrayList<Monster> monsters;
    public static int price=150;
    public ElectroTower(float x, float y) {
        super(x, y);

        setAttackSpeed(4);
        setAttackRange(120);
        setDamage(8);

        int spriteW=16, spriteH=35;

        this.spriteNow=new TextureRegion(textureImg,0,0, spriteW, spriteH);
        this.attackPointX=x-3+ (float) spriteW /2;
        this.attackPointY=y+3+ (float) spriteH /2;
    }

    public static void initTexture(Texture t){
        textureImg=t;
    }

    public static void initMonstersList(ArrayList<Monster> m){
        monsters=m;
    }
    public ArrayList<Bullet> attack(){
        Date nowDate=new Date();
        long nowTime=nowDate.getTime();
        ArrayList<Bullet> bullets=new ArrayList<Bullet>();
        if((this.getTarget()!=null)&&(nowTime-this.getLastAttackTime()>1000/getAttackSpeed())) {
            setLastAttackTime(nowTime);

            Circle attackCircle=new Circle(this.attackPointX, this.attackPointY, this.getAttackRange());
            for(Monster monster : monsters){
                if(attackCircle.contains(monster.getX(), monster.getY())&&(monster.getEffect()!=Elements.ELECTRO)){
                    bullets.add(new ElectroBullet(attackPointX, attackPointY, this.getDamage(), monster));
                    return bullets;
                }
            }
            bullets.add(new ElectroBullet(attackPointX, attackPointY, this.getDamage(), getTarget()));
        }
        return bullets;
    };

}
