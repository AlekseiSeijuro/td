package com.mygdx.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.DrawableObject;

public class TowerSpot extends DrawableObject {

    private static Texture textureImg;
    private Rectangle spotRect;
    private Tower tower;

    public TowerSpot(int x, int y){

        this.posX=x;
        this.posY=y;

        int spriteW=32, spriteH=32;

        this.spriteNow=new TextureRegion(textureImg, spriteW, spriteH);
        this.spotRect=new Rectangle(x, y, spriteW, spriteH);

        this.tower=null;
    }

    public static void initTexture(Texture t){
        textureImg=t;
    }

    public void buildTower( int towerType){
        if(this.tower==null){
            if(towerType==Elements.PYRO)this.tower=new PyroTower(this.getX()+7, this.getY()+10);
            else if(towerType==Elements.CRIO)this.tower=new CrioTower(this.getX()+6, this.getY()+10);
            else if(towerType==Elements.GIDRO)this.tower=new GidroTower(this.getX()+8, this.getY()+12);
            else if(towerType==Elements.ELECTRO)this.tower=new ElectroTower(this.getX()+8, this.getY()+10);
        }
    }
    public Tower getTower(){
        return tower;
    }

    public Rectangle getSpotRect(){
        return spotRect;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        if(this.tower!=null) this.tower.draw(batch);
    }
}
