package com.mygdx.game.Units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class RedDragon extends Monster{

    private static Texture textureImg;
    private static TextureRegion[] animSpritesMoveLeft,  animSpritesMoveRight, animSpritesMoveUp, animSpritesMoveDown;
    private static int spriteW=64, spriteH=64;

    public RedDragon( int x, int y, ArrayList<Monster> monsters){
        super(x, y, monsters);

        this.setDamage(5);
        this.setMaxHealth(10000);
        this.setMySpeed(1);
        this.setGoldReward(35);

        collisionRect=new Rectangle(x, y, spriteW, spriteH);

        this.spriteNow=new TextureRegion(textureImg,0,0, spriteW, spriteH);

    }

    public float getX(){
        return (collisionRect.x+20);
    }
    public float getY(){
        return (collisionRect.y+20);
    }

    public static void initTexture(Texture t){
        textureImg=t;

        animSpritesMoveLeft=new TextureRegion[4];
        animSpritesMoveRight=new TextureRegion[4];
        animSpritesMoveDown=new TextureRegion[4];
        animSpritesMoveUp=new TextureRegion[4];

        for(int i=0; i<4; i++){
            animSpritesMoveDown[i]=new TextureRegion(textureImg, i*spriteW, 0*spriteH, spriteW, spriteH);
            animSpritesMoveUp[i]=new TextureRegion(textureImg, i*spriteW, 1*spriteH, spriteW, spriteH);
            animSpritesMoveRight[i]=new TextureRegion(textureImg, i*spriteW, 3*spriteH, spriteW, spriteH);
            animSpritesMoveLeft[i]=new TextureRegion(textureImg, i*spriteW, 2*spriteH, spriteW, spriteH);

        }
    }

    public void move(int dx, int dy){
        if(dx>0){
            spriteNow=animSpritesMoveRight[animIndexNow];
            animIndexNow=(animIndexNow+1)%4;
        }
        else if(dx<0){
            spriteNow=animSpritesMoveLeft[animIndexNow];
            animIndexNow=(animIndexNow+1)%4;
        }
        else if(dy>0){
            spriteNow=animSpritesMoveUp[animIndexNow];
            animIndexNow=(animIndexNow+1)%4;
        }
        else if(dy<0){
            spriteNow=animSpritesMoveDown[animIndexNow];
            animIndexNow=(animIndexNow+1)%4;
        }

        posX+=dx;
        posY+=dy;
        collisionRect.x+=dx;
        collisionRect.y+=dy;
    }


}

