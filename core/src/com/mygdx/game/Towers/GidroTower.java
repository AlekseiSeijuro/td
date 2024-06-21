package com.mygdx.game.Towers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.mygdx.game.Towers.Bullets.Bullet;
import com.mygdx.game.Units.Monster;

import java.util.ArrayList;
import java.util.Date;

public class GidroTower extends Tower{

    private static Texture textureImg, poolImg;
    private static ArrayList<Monster> monsters;
    public static int price=80;
    public GidroTower(float x, float y) {
        super(x, y);

        setAttackSpeed(2);
        setAttackRange(80);
        setDamage(5);

        int spriteW=16, spriteH=15;

        this.spriteNow=new TextureRegion(textureImg,0,0, spriteW, spriteH);
        this.attackPointX=x+ (float) spriteW /2;
        this.attackPointY=y+ (float) spriteH /2;
    }

    public static void initTexture(Texture t, Texture poolTexture){
        textureImg=t;
        poolImg=poolTexture;
    }
    public static void initMonstersList(ArrayList<Monster> m){
        monsters=m;
    }

    public ArrayList<Bullet> attack(){
        Date nowDate=new Date();
        long nowTime=nowDate.getTime();

        if((this.getTarget()!=null)&&(nowTime-this.getLastAttackTime()>1000/getAttackSpeed())) {
            setLastAttackTime(nowTime);

            Circle attackCircle=new Circle(this.attackPointX, this.attackPointY, this.getAttackRange());
            for(Monster monster : monsters){
                if(attackCircle.contains(monster.getX(), monster.getY())){
                    monster.setHealth(monster.getHealth()-this.getDamage());

                    if((monster.getEffect()==Elements.CRIO)){
                        monster.setSpeed(0);
                        monster.setFreezeSetTime(System.currentTimeMillis());
                        monster.setReactAnimStart(System.currentTimeMillis());
                        monster.setEffect(Elements.NONE);
                        monster.setColor(new Color(Color.rgba8888(0,0.1f, 1f, 0.8f)));
                    }else{
                        monster.setEffect(Elements.GIDRO);
                    }
                }
            }

        }
        return null;
    };

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(poolImg, attackPointX-this.getAttackRange(), attackPointY-this.getAttackRange(),
                this.getAttackRange()*2, this.getAttackRange()*2);
        super.draw(batch);
    }
}
