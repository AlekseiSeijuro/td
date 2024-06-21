package com.mygdx.game.Units;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.DrawableObject;
import com.mygdx.game.Towers.Bullets.Bullet;
import com.mygdx.game.Towers.Elements;

import java.util.ArrayList;

public abstract class Monster extends DrawableObject {

    private float maxHealth;
    private float health;

    private Color color;
    private long reactAnimStart;

    private ArrayList<Monster> monsters;

    private int mySpeed;
    private int speed;
    private int goldReward;
    private int targetPointIndex;

    private int damage;

    private int effect;

    private long freezeSetTime;
    protected int animIndexNow;
    protected Rectangle collisionRect;

    public Monster( int x, int y, ArrayList<Monster> monsters){
        this.posX=x;
        this.posY=y;
        this.animIndexNow=0;
        this.targetPointIndex=1;
        this.effect= Elements.NONE;
        this.freezeSetTime=0;
        this.color=null;
        this.reactAnimStart=0;
        this.monsters=monsters;
    }

    public int getDamage(){
        return damage;
    }
    public void setDamage(int d){
        damage=d;
    }
    public void setTargetPointIndex(int i){
        this.targetPointIndex=i;
    }

    public int getTargetPointIndex(){
        return targetPointIndex;
    }

    public Rectangle getCollisionRect(){
        return collisionRect;
    }
    public void setGoldReward(int goldReward) {
        this.goldReward = goldReward;
    }
    public int getGoldReward() {
        return goldReward;
    }
    public void setHealth(float h){
        this.health=h;
    }

    public float getHealth(){
        return health;
    }

    public float getMaxHealth(){
        return maxHealth;
    }

    public void setReactAnimStart(long reactAnimStart) {
        this.reactAnimStart = reactAnimStart;
    }
    public void setColor(Color c){
        this.color=c;
    }

    public void setMaxHealth(float maxHealth){
        this.health=maxHealth;
        this.maxHealth=maxHealth;
    }

    public void setMySpeed(int mySpeed) {
        this.mySpeed = mySpeed;
        this.speed=mySpeed;
    }

    public int getMySpeed() {
        return mySpeed;
    }

    public void setSpeed(int s){
        this.speed=s;
    }

    public void setFreezeSetTime(long t){
        freezeSetTime=t;
    };
    public int getSpeed(){
        return speed;
    }

    public void setEffect(int newEffect){
        this.effect=newEffect;
    }
    public int getEffect(){
        return effect;
    }
    public void draw(SpriteBatch batch){

        Color c=new Color(batch.getColor());
        if(color!=null){
            batch.setColor(color);
        }
        super.draw(batch);
        batch.setColor(c);

        if(System.currentTimeMillis()-this.freezeSetTime>=3*1000){
            this.setSpeed(mySpeed);
            this.freezeSetTime=0;
            if(System.currentTimeMillis()-this.reactAnimStart>=0.2*1000){
                this.color=null;
            }
        }



        if(this.effect!=Elements.NONE){
            float elemSize=collisionRect.width/2;
            float elemX=collisionRect.x+elemSize/2;
            float elemY=collisionRect.y+collisionRect.height+6;

            TextureRegion elemTexture = null;

            if(this.effect==Elements.PYRO)elemTexture=Elements.getPyroTexture();
            else if(this.effect==Elements.GIDRO)elemTexture=Elements.getGidroTexture();
            else if(this.effect==Elements.ELECTRO)elemTexture=Elements.getElectroTexture();
            else if(this.effect==Elements.CRIO)elemTexture=Elements.getCrioTexture();

            batch.draw(elemTexture, elemX, elemY, elemSize, elemSize);
        }
    }



    public abstract void move(int dx, int dy);

    public boolean checkCollisionWithBullet(Bullet bullet){
        if(collisionRect.contains(bullet.getX(), bullet.getY())){
            float damage= bullet.getDamage();
            //реакция таяние
            if(((this.effect==Elements.PYRO)&&(bullet.getElement()==Elements.CRIO))||
                    ((this.effect==Elements.CRIO)&&(bullet.getElement()==Elements.PYRO))){
                damage*=2;
                this.setEffect(Elements.NONE);
                reactAnimStart=System.currentTimeMillis();
                this.color=new Color(Color.BLUE);
                health-=damage;
            }
            //реакция пар
            else if(((this.effect==Elements.PYRO)&&(bullet.getElement()==Elements.GIDRO))||
                    ((this.effect==Elements.GIDRO)&&(bullet.getElement()==Elements.PYRO))){
                damage*=1.4;
                this.setEffect(Elements.NONE);
                reactAnimStart=System.currentTimeMillis();
                this.color=new Color(Color.GRAY);
                health-=damage;
            //реакция заморозка
            }else if((this.effect==Elements.GIDRO)&&(bullet.getElement()==Elements.CRIO)){
                this.setSpeed(0);
                this.freezeSetTime=System.currentTimeMillis();
                this.reactAnimStart=System.currentTimeMillis();
                this.setEffect(Elements.NONE);
                this.color=new Color(Color.rgba8888(0,0.1f, 1f, 0.8f));
                health-=damage;
            //реакция перегрузка
            }else if(((this.effect==Elements.PYRO)&&(bullet.getElement()==Elements.ELECTRO))||
                    ((this.effect==Elements.ELECTRO)&&(bullet.getElement()==Elements.PYRO))) {
                this.setEffect(Elements.NONE);
                int overloadDamage=20;
                int overloadRange=35;

                this.reactAnimStart=System.currentTimeMillis();
                this.color=new Color(Color.RED);
                health-=damage;

                //урон по области
                Circle overloadCircle=new Circle(this.getX(), this.getY(),overloadRange );
                for(Monster monster:monsters){
                    if(overloadCircle.contains(monster.getX(), monster.getY())){
                        monster.setReactAnimStart(this.reactAnimStart);
                        monster.setColor(this.color);
                        monster.setHealth(monster.getHealth()-overloadDamage);
                    }
                }
            }
            else{
                this.setEffect(bullet.getElement());
                health-=damage;
            }
            return true;
        }else{
            return false;
        }
    }

    public void drawHPRect(ShapeRenderer renderer){

        float hpRectW=collisionRect.width/2;
        float hpRectH=2;
        float hpRectX=collisionRect.x+hpRectW/2;
        float hpRectY=collisionRect.y+collisionRect.height+3;

        float hpRatio=health/maxHealth;

        renderer.setColor(Color.RED);
        renderer.rect(hpRectX, hpRectY, hpRectW, hpRectH);

        renderer.setColor(Color.GREEN);
        renderer.rect(hpRectX, hpRectY, hpRectW*hpRatio, hpRectH);

    }

}
