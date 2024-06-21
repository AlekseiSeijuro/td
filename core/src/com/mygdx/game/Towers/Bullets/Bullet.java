package com.mygdx.game.Towers.Bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.DrawableObject;
import com.mygdx.game.Units.Monster;

import static java.lang.Math.*;

public abstract class Bullet extends DrawableObject {
    private Monster target;

    private float damage;
    private int speed;

    private int element;

    public Bullet(float x, float y, float damage, Monster target){
        this.posX=x;
        this.posY=y;
        this.damage=damage;
        this.target=target;
    }
    public Monster getTarget(){
        return target;
    }

    public void setElement(int element){
        this.element=element;
    }

    public int getElement(){
        return element;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public float getDamage(){
        return damage;
    }
    public void chaseTarget(){

        Rectangle targetCollisionRect=target.getCollisionRect();
        float collisionCenterX=targetCollisionRect.x+targetCollisionRect.width/2;
        float collisionCenterY=targetCollisionRect.y+targetCollisionRect.height/2;

        double distance=sqrt(pow(this.posY-collisionCenterY, 2)+pow((this.posX-collisionCenterX),2));
        double speedX=speed*(collisionCenterX-this.posX)/distance;
        double speedY=speed*(collisionCenterY-this.posY)/distance;

        posX+=speedX;
        posY+=speedY;
    }


}
