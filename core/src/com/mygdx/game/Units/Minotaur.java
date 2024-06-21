package com.mygdx.game.Units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class Minotaur extends Monster{

    private static Texture textureImg;
    private static TextureRegion[] animSpritesMoveLeft,  animSpritesMoveRight, animSpritesMoveUp, animSpritesMoveDown;
    private static int spriteW=16, spriteH=16;
    public Minotaur(int x, int y, ArrayList<Monster> monsters){
        super( x, y, monsters);

        this.setMaxHealth(300);
        this.setMySpeed(1);
        this.setGoldReward(20);
        this.setDamage(1);
        int spriteW=16, spriteH=16;

        collisionRect=new Rectangle(x, y, spriteW, spriteH);

        this.spriteNow=new TextureRegion(textureImg,0,0, spriteW, spriteH);

    }

    public static void initTexture(Texture t){
        textureImg=t;

        animSpritesMoveLeft=new TextureRegion[5];
        animSpritesMoveRight=new TextureRegion[5];
        animSpritesMoveDown=new TextureRegion[5];
        animSpritesMoveUp=new TextureRegion[5];

        for(int i=0; i<5; i++){
            animSpritesMoveDown[i]=new TextureRegion(textureImg, i*spriteW, 0*spriteH, spriteW, spriteH);
            animSpritesMoveUp[i]=new TextureRegion(textureImg, i*spriteW, 1*spriteH, spriteW, spriteH);
            animSpritesMoveRight[i]=new TextureRegion(textureImg, i*spriteW, 2*spriteH, spriteW, spriteH);
            animSpritesMoveLeft[i]=new TextureRegion(textureImg, i*spriteW, 3*spriteH, spriteW, spriteH);
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
