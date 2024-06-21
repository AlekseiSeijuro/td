package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Main;
import com.mygdx.game.Towers.*;
import com.mygdx.game.Towers.Bullets.Bullet;
import com.mygdx.game.Towers.Bullets.CrioBullet;
import com.mygdx.game.Towers.Bullets.ElectroBullet;
import com.mygdx.game.Towers.Bullets.PyroBullet;
import com.mygdx.game.Towers.PyroTower;
import com.mygdx.game.UIelements.TowerButton;
import com.mygdx.game.Units.*;
import com.mygdx.game.Waves.Wave;
import com.mygdx.game.Waves.WavePart;

import java.awt.*;
import java.security.Key;
import java.util.ArrayList;

import static java.lang.Math.*;
import static java.lang.Thread.sleep;

public class GameScreen implements Screen {

    Main main;

    private Texture gardenTexture, roadTexture, castleTexture, heartTexture, goldTexture;
    private Drawable drawablePyroTowerButton, drawableCrioTowerButton, drawableGydroTowerButton, drawableElectroTowerButton,
    drawableCloseButton, drawableTMBackground;
    private Stage towerButtonsStage;
    int routeLength;
    private Point[] routePoints;

    private boolean started=false;

    private Point spawnPoint;
    private Thread spawnDaemon, monsterMover;
    private int hearts;
    private int gold;

    private Wave waves[];
    private int waveNow=0;

    private BitmapFont goldFont;
    private ArrayList<Monster> monsters;

    private int towerSpotsCount;
    private ArrayList<TowerSpot> towerSpots;

    private ArrayList<Bullet> bullets;

    ShapeRenderer renderer;

    public GameScreen(Main main, LevelDescription levelDescription){
        this.main=main;

        //загрузка текстур
        gardenTexture=new Texture("EnvironmentSprites/grass.png");
        roadTexture=new Texture("EnvironmentSprites/road.png");
        castleTexture=new Texture("EnvironmentSprites/bigCastle.png");
        heartTexture=new Texture("EnvironmentSprites/heart.png");
        goldTexture=new Texture("EnvironmentSprites/GoldIcon.png");

        drawablePyroTowerButton = new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/PyroTowerButton.png")));
        drawableCrioTowerButton = new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/CrioTowerButton.png")));
        drawableGydroTowerButton = new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/GidroTowerButton.png")));
        drawableElectroTowerButton = new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/ElectroTowerButton.png")));
        drawableCloseButton = new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/CloseButton.png")));
        drawableTMBackground = new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/TowerMenuBackground.png")));

        Orc.initTexture(new Texture("Monsters/Orc.png"));
        Goblin.initTexture(new Texture("Monsters/Goblin.png"));
        Minotaur.initTexture(new Texture("Monsters/Minotaur.png"));
        RedDragon.initTexture(new Texture("Monsters/RedDragon.png"));

        TowerSpot.initTexture(new Texture("EnvironmentSprites/Cliff.png"));
        PyroTower.initTexture(new Texture("Towers/PyroTower.png"));
        CrioTower.initTexture(new Texture("Towers/CrioTower.png"));
        GidroTower.initTexture(new Texture("Towers/GidroTower.png"), new Texture("Towers/GidroPool.png"));
        ElectroTower.initTexture(new Texture("Towers/ElectroTower.png"));

        PyroBullet.initTexture(new Texture("Towers/PyroBullet.png"));
        CrioBullet.initTexture(new Texture("Towers/CrioBullet.png"));
        ElectroBullet.initTexture(new Texture("Towers/ElectroBullet.png"));

        Elements.initTexture(new Texture("EnvironmentSprites/Elements.png"));


        //инициализация уровня
        hearts=5;

        gold=levelDescription.startGold;
        goldFont=new BitmapFont();
        goldFont.setColor(Color.YELLOW);

        routePoints= levelDescription.dataRoutePoints;
        routeLength=routePoints.length;

        //инициализация волн монстров
        waves=levelDescription.waves;

        //списки игровыых объектов
        monsters=new ArrayList<Monster>();
        bullets=new ArrayList<Bullet>();
        towerSpots=new ArrayList<TowerSpot>();

        //гидро и электро тавер требует список монстров
        GidroTower.initMonstersList(monsters);
        ElectroTower.initMonstersList(monsters);

        Point[] dataTowerSpotsPoints= levelDescription.dataTowerSpotsPoints;
        towerSpotsCount=dataTowerSpotsPoints.length;

        for(int i=0; i<towerSpotsCount; i++){
            towerSpots.add(new TowerSpot(dataTowerSpotsPoints[i].x, dataTowerSpotsPoints[i].y));
        }

        spawnPoint=routePoints[0];

        renderer=new ShapeRenderer();

        started=true;

        spawnDaemon=new Thread(new Runnable() {
            @Override
            public void run() {
                while(started) {
                    for(int i=0; i<waves[waveNow].getWavePartsCount(); i++){
                        WavePart wavePartNow=waves[waveNow].getWavePart(i);
                        for(int j=0; j<wavePartNow.getMonsterCount(); j++){
                            synchronized (monsters) {
                                if (wavePartNow.getMonsterType() == Wave.ORC)
                                    monsters.add(new Orc(spawnPoint.x, spawnPoint.y, monsters));
                                if (wavePartNow.getMonsterType() == Wave.GOBLIN)
                                    monsters.add(new Goblin(spawnPoint.x, spawnPoint.y, monsters));
                                if (wavePartNow.getMonsterType() == Wave.MINOTAUR)
                                    monsters.add(new Minotaur(spawnPoint.x, spawnPoint.y, monsters));
                                if (wavePartNow.getMonsterType() == Wave.REDDRAGON)
                                    monsters.add(new RedDragon(spawnPoint.x, spawnPoint.y, monsters));
                            }
                            try {
                                sleep(wavePartNow.getSpawnInterval());
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    waveNow=(waveNow+1)%waves.length;
                }
            }
        });
        spawnDaemon.start();

        monsterMover=new Thread(new Runnable() {
            @Override
            public void run() {
                while (started) {
                    synchronized (monsters){
                    for(int i=0;i<monsters.size();i++) {
                        if (monsters.get(i).getTargetPointIndex() < routeLength) {
                            Point nowTargetPoint = routePoints[monsters.get(i).getTargetPointIndex()];

                            float difx = nowTargetPoint.x - monsters.get(i).getX();
                            float dify = nowTargetPoint.y - monsters.get(i).getY();
                            if (Math.abs(difx) >= monsters.get(i).getSpeed()) {
                                monsters.get(i).move((int)Math.signum(difx)*monsters.get(i).getSpeed(), 0);
                            }
                            else if (Math.abs(dify) >= monsters.get(i).getSpeed()) {
                                monsters.get(i).move(0, (int)Math.signum(dify)*monsters.get(i).getSpeed());
                            } else{
                                monsters.get(i).move((int)difx, (int)dify);
                                monsters.get(i).setTargetPointIndex(monsters.get(i).getTargetPointIndex() + 1);
                            }

                        }
                    }
                    }
                    try {
                        sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        monsterMover.start();

    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {

            //Взврат в меню уровней
            public boolean keyDown(int keycode){
                if (keycode== Input.Keys.ESCAPE){
                    started=false;
                    monsterMover.interrupt();
                    spawnDaemon.interrupt();
                    main.setScreen(main.levelsSc);
                }

                return true;
            }


            //Постройка таверов
            @Override
            public boolean touchDown (int x, int y, int pointer, int button) {
                InputAdapter mainInputAdapter=this;

                TowerSpot pickedSpot=null;
                for(TowerSpot towerSpot : towerSpots){
                    if((towerSpot.getTower()==null)&&(towerSpot.getSpotRect().contains(x,Main.height - y))){
                        pickedSpot=towerSpot;
                        break;
                    }
                }

                TowerSpot finalPickedSpot = pickedSpot;

                if(finalPickedSpot!=null) {
                    towerButtonsStage = new Stage(new ScreenViewport());

                    TowerButton pyroTowerButton = new TowerButton(Main.width/2-242, 23, drawablePyroTowerButton, Elements.PYRO);
                    pyroTowerButton.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            if (PyroTower.price <= gold) {
                                finalPickedSpot.buildTower(Elements.PYRO);
                                gold -= PyroTower.price;
                                Gdx.input.setInputProcessor(mainInputAdapter);
                                towerButtonsStage = null;
                            }
                        }
                    });

                    TowerButton crioTowerButton = new TowerButton(Main.width/2-122, 23, drawableCrioTowerButton, Elements.CRIO);
                    crioTowerButton.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            if (CrioTower.price <= gold) {
                                finalPickedSpot.buildTower(Elements.CRIO);
                                gold -= CrioTower.price;
                                Gdx.input.setInputProcessor(mainInputAdapter);
                                towerButtonsStage = null;
                            }
                        }
                    });

                    TowerButton gydroTowerButton=new TowerButton(Main.width/2-2, 23, drawableGydroTowerButton, Elements.GIDRO);
                    gydroTowerButton.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            if(GidroTower.price <= gold) {
                                finalPickedSpot.buildTower(Elements.GIDRO);
                                gold -= GidroTower.price;
                                Gdx.input.setInputProcessor(mainInputAdapter);
                                towerButtonsStage = null;
                            }
                        }
                    });

                    TowerButton electroTowerButton=new TowerButton(Main.width/2+118, 23, drawableElectroTowerButton, Elements.ELECTRO);
                    electroTowerButton.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            if(ElectroTower.price <= gold) {
                                finalPickedSpot.buildTower(Elements.ELECTRO);
                                gold -= ElectroTower.price;
                                Gdx.input.setInputProcessor(mainInputAdapter);
                                towerButtonsStage = null;
                            }
                        }
                    });

                    ImageButton closeButton = new ImageButton(drawableCloseButton);
                    closeButton.setPosition(Main.width/2+217, 115);
                    closeButton.addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            Gdx.input.setInputProcessor(mainInputAdapter);
                            towerButtonsStage = null;
                        }
                    });

                    Image menuBackground=new Image(drawableTMBackground);
                    menuBackground.setPosition(Main.width/2-262, 10);

                    towerButtonsStage.addActor(menuBackground);
                    towerButtonsStage.addActor(pyroTowerButton);
                    towerButtonsStage.addActor(crioTowerButton);
                    towerButtonsStage.addActor(gydroTowerButton);
                    towerButtonsStage.addActor(electroTowerButton);
                    towerButtonsStage.addActor(closeButton);

                    Gdx.input.setInputProcessor(towerButtonsStage);
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        main.batch.begin();

        //отрисовка травы
        for(int x=0; x<Main.width; x+=16){
            for(int y=0; y<Main.height; y+=16){
                main.batch.draw(gardenTexture, x, y);
            }
        }

        //отрисовка дороги
        for(int i=0; i< routeLength-1; i++){
            if(routePoints[i].x==routePoints[i+1].x){//если участок расположен вертикально
                for(int y=min(routePoints[i].y, routePoints[i+1].y); y<max(routePoints[i].y, routePoints[i+1].y); y+=16){
                    main.batch.draw(roadTexture, routePoints[i].x, y);
                    main.batch.draw(roadTexture, routePoints[i].x-16, y);
                    main.batch.draw(roadTexture, routePoints[i].x+16, y);
                }
            }else if(routePoints[i].y==routePoints[i+1].y){//если участок расположен горизонтально
                for(int x=min(routePoints[i].x, routePoints[i+1].x); x<max(routePoints[i+1].x, routePoints[i].x); x+=16){
                    main.batch.draw(roadTexture, x, routePoints[i].y);
                    main.batch.draw(roadTexture, x, routePoints[i].y-16);
                    main.batch.draw(roadTexture, x, routePoints[i].y+16);
                }
            }

            //обрисовка точек при поворотах
            for(int x=routePoints[i].x-16; x<=routePoints[i].x+16; x+=16){
                for(int y=routePoints[i].y-16; y<=routePoints[i].y+16; y+=16){
                    main.batch.draw(roadTexture, x, y);
                }
            }
        }


        //отрисовка башен
        for(TowerSpot towerSpot : towerSpots){
            towerSpot.draw(main.batch);

            Tower tower=towerSpot.getTower();
            if(tower!=null){
                tower.checkTargetInAttackRange();
                if (tower.getTarget() == null) {
                    synchronized (monsters) {
                        for (Monster monster : monsters) {
                            Circle attackCircle = new Circle(tower.getX(), tower.getY(), tower.getAttackRange());
                            if (attackCircle.contains(monster.getX(), monster.getY())) {
                                tower.setTarget(monster);
                                break;
                            }
                        }
                    }
                }
                ArrayList<Bullet> newBullets=tower.attack();
                if(newBullets!=null){
                    for(Bullet newBullet : newBullets) bullets.add(newBullet);
                }
            }
        }

        //отрисовка монстров
        synchronized (monsters) {
            for (Monster monster : monsters){
                monster.draw(main.batch);
            }
        }



        //отрисовка пуль
        for(Bullet bullet : bullets) {
            bullet.chaseTarget();
            bullet.draw(main.batch);
        }

        //долетела ли пуля до цели
        for(int i=0; i<bullets.size(); i++) {
            Bullet bullet=bullets.get(i);
            if(bullet.getTarget().checkCollisionWithBullet(bullet)){
                bullets.remove(i);
            };
        }


        //отрисовка замка
        main.batch.draw(castleTexture, routePoints[routeLength-1].x-24, routePoints[routeLength-1].y-24);

        //отрисовка сердечек
        for(int i=0; i<hearts; i++){
            main.batch.draw(heartTexture, (Main.width-40)-20*i, Main.height-32);
        }
        //отрисовка золота
        main.batch.draw(goldTexture, 20, Main.height-40);
        goldFont.draw(main.batch, ""+gold, 40, Main.height-22);


        //монстры дошедшие до конца или умершие
        synchronized (monsters) {
            for (int i = 0; i < monsters.size(); i++) {
                Monster monster = monsters.get(i);

                if (monster.getHealth() <= 0) {
                    for (TowerSpot towerSpot : towerSpots) {
                        if ((towerSpot.getTower() != null) && (towerSpot.getTower().getTarget() == monster))
                            towerSpot.getTower().setTarget(null);
                    }
                    gold += monster.getGoldReward();
                    monsters.remove(i);
                } else if (monster.getTargetPointIndex() >= routeLength) {
                    for (TowerSpot towerSpot : towerSpots) {
                        if ((towerSpot.getTower() != null) && (towerSpot.getTower().getTarget() == monster))
                            towerSpot.getTower().setTarget(null);
                    }

                    hearts-=monster.getDamage();
                    monsters.remove(i);
                }
            }
        }

        main.batch.end();


        //отрисовка хпбаров
        synchronized (monsters) {
            for (Monster monster : monsters) {
                renderer.begin(ShapeRenderer.ShapeType.Filled);
                monster.drawHPRect(renderer);
                renderer.end();
            }
        }

        //отрисовка радиусов атаки башен
        for(TowerSpot towerSpot : towerSpots) {
            if(towerSpot.getTower()!=null) {
                renderer.begin(ShapeRenderer.ShapeType.Line);
                renderer.setColor(Color.CYAN);
                renderer.circle(towerSpot.getTower().getAttackPointX(), towerSpot.getTower().getAttackPointY(), towerSpot.getTower().getAttackRange());
                renderer.end();
            }
        }

        if(hearts<=0){
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(new Color(99/255f,120/255f,99/255f,1));
            renderer.rect(Main.width/4, Main.height/4, Main.width/2, Main.height/2);
            renderer.end();

            main.batch.begin();
            BitmapFont ggFont=new BitmapFont();
            ggFont.setColor(Color.BLACK);
            ggFont.getData().setScale(5);
            ggFont.draw(main.batch, "Game Over", Main.width/4+120, Main.height/2+40);
            main.batch.end();

            started=false;
            monsterMover.interrupt();
            spawnDaemon.interrupt();
        }

        //отрисовка меню башен
        if(towerButtonsStage!=null){
            towerButtonsStage.act(Gdx.graphics.getDeltaTime());
            towerButtonsStage.draw();

            main.batch.begin();
            goldFont.draw(main.batch, ""+PyroTower.price, Main.width/2-242+40, 40);
            goldFont.draw(main.batch, ""+CrioTower.price, Main.width/2-122+40, 40);
            goldFont.draw(main.batch, ""+GidroTower.price, Main.width/2-2+40, 40);
            goldFont.draw(main.batch, ""+ElectroTower.price, Main.width/2+118+40, 40);
            main.batch.end();
        }

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
