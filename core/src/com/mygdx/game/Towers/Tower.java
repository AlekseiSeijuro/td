package com.mygdx.game.Towers;

import com.badlogic.gdx.math.Circle;
import com.mygdx.game.DrawableObject;
import com.mygdx.game.Towers.Bullets.Bullet;
import com.mygdx.game.Units.Monster;

import java.util.ArrayList;
import java.util.Date;

public abstract class Tower extends DrawableObject {

    private double attackSpeed;
    private long lastAttackTime;
    private int damage, attackRange;

    protected float attackPointX, attackPointY;

    private Monster target;
    public Tower(float x, float y){
        this.posX=x;
        this.posY=y;
        this.target=null;

        Date nowDate=new Date();
        this.lastAttackTime=nowDate.getTime();
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public long getLastAttackTime(){
        return lastAttackTime;
    }

    public void setLastAttackTime(long lastAttackTime) {
        this.lastAttackTime = lastAttackTime;
    }

    public int getDamage(){
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public float getAttackPointX(){
        return attackPointX;
    }

    public float getAttackPointY(){
        return attackPointY;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange){
        this.attackRange=attackRange;
    }

    public Monster getTarget(){
        return target;
    }

    public void setTarget(Monster target){
        this.target=target;
    }

    public void checkTargetInAttackRange(){
        Circle attackCircle=new Circle(this.attackPointX, this.attackPointY, attackRange);
        if((target!=null)&&(!attackCircle.contains(target.getX(), target.getY())))target=null;
    }
    public abstract ArrayList<Bullet> attack();
}
