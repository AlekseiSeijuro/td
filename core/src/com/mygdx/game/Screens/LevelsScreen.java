package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Main;
import com.mygdx.game.Waves.Wave;
import com.mygdx.game.Waves.WavePart;

import java.awt.*;
import java.util.Random;

public class LevelsScreen implements Screen {


    Main main;

    private Stage stage;
    public Texture levelsMenuPicture;

    public LevelsScreen(Main main) {
        this.main=main;

        stage = new Stage(new ScreenViewport());

        //кнопка назад
        levelsMenuPicture=new Texture("ScreensPictures/LevelsScreenPicture.png");

        Texture backButtonTexture = new Texture("Buttons/BackButton.png");
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(backButtonTexture));
        ImageButton backButton = new ImageButton(drawable);
        backButton.setPosition(0, Main.height-64);

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(main.mainSc);
            }
        });

        //кнопки уровней
        Texture levelButton_1_Texture=new Texture("Buttons/LevelButton_1.png");
        ImageButton levelButton_1=new ImageButton(new TextureRegionDrawable(new TextureRegion(levelButton_1_Texture)));
        levelButton_1.setPosition(550,40);

        Texture levelButton_2_Texture=new Texture("Buttons/LevelButton_2.png");
        ImageButton levelButton_2=new ImageButton(new TextureRegionDrawable(new TextureRegion(levelButton_2_Texture)));
        levelButton_2.setPosition(550,150);

        Texture levelButton_3_Texture=new Texture("Buttons/LevelButton_3.png");
        ImageButton levelButton_3=new ImageButton(new TextureRegionDrawable(new TextureRegion(levelButton_3_Texture)));
        levelButton_3.setPosition(550,260);

        levelButton_1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Начальное золото
                int gold=2000;
                //Точки дороги
                Point[] points={new Point(552, 0), new Point(552, 204),new Point(804, 204),
                        new Point(804, 504), new Point(300, 504),new Point(300, 660)};
                //Волны врагов
                Wave[] waves=new Wave[3];
                for(int i=0;i<3;i++)waves[i]=new Wave();

                waves[0].addWavePart(new WavePart(Wave.ORC, 10, Wave.MEDIUM));
                waves[0].addWavePart(new WavePart(Wave.GOBLIN, 5, Wave.SLOW));
                waves[1].addWavePart(new WavePart(Wave.MINOTAUR, 5, Wave.MEDIUM));
                waves[2].addWavePart(new WavePart(Wave.ORC, 20, Wave.FAST));
                waves[2].addWavePart(new WavePart(Wave.MINOTAUR, 10, Wave.MEDIUM));
                //Места для башен
                Point[] dataTowerSpotsPoints={new Point(570, 240), new Point(650, 240), new Point(850, 300),
                        new Point(750, 350), new Point(825, 150), new Point(750, 550), new Point(650, 450),
                        new Point(500, 450), new Point(600, 100), new Point(300, 450)};


                LevelDescription description=new LevelDescription(gold, points, waves, dataTowerSpotsPoints);

                main.gameSc=new GameScreen(main, description);
                main.setScreen(main.gameSc);
            }
        });

        levelButton_2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int gold=500;

                Point[] points={new Point(0, 240), new Point(300, 240),new Point(300, 48),
                        new Point(504, 48), new Point(504, 504),new Point(1104, 504)};

                Wave[] waves=new Wave[3];
                for(int i=0;i<3;i++)waves[i]=new Wave();

                waves[0].addWavePart(new WavePart(Wave.REDDRAGON, 1, Wave.SLOW));
                waves[0].addWavePart(new WavePart(Wave.ORC, 10, Wave.MEDIUM));
                waves[0].addWavePart(new WavePart(Wave.GOBLIN, 5, Wave.SLOW));
                waves[1].addWavePart(new WavePart(Wave.MINOTAUR, 5, Wave.MEDIUM));
                waves[2].addWavePart(new WavePart(Wave.ORC, 20, Wave.FAST));
                waves[2].addWavePart(new WavePart(Wave.MINOTAUR, 10, Wave.MEDIUM));

                Point[] dataTowerSpotsPoints={
                        new Point(350, 240),
                        new Point(400, 90),
                        new Point(550, 150),
                        new Point(450, 480),
                        new Point(450, 550),
                        new Point(575, 270),
                        new Point(1000, 550),
                        new Point(575, 450),
                        new Point(575, 330),
                };


                LevelDescription description=new LevelDescription(gold, points, waves, dataTowerSpotsPoints);

                main.gameSc=new GameScreen(main, description);
                main.setScreen(main.gameSc);
            }
        });

        levelButton_3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int gold=1000;


                Point[] points={new Point(600, 696), new Point(600, 48),new Point(144, 48),
                        new Point(144, 300), new Point(1104, 300)};

                Wave[] waves=new Wave[3];
                for(int i=0;i<3;i++)waves[i]=new Wave();

                waves[0].addWavePart(new WavePart(Wave.ORC, 10, Wave.MEDIUM));
                waves[0].addWavePart(new WavePart(Wave.GOBLIN, 5, Wave.SLOW));
                waves[1].addWavePart(new WavePart(Wave.MINOTAUR, 5, Wave.MEDIUM));
                waves[2].addWavePart(new WavePart(Wave.ORC, 20, Wave.FAST));
                waves[2].addWavePart(new WavePart(Wave.MINOTAUR, 10, Wave.MEDIUM));

                Point[] dataTowerSpotsPoints={new Point(525, 350), new Point(650, 500), new Point(525, 250),
                        new Point(650, 100), new Point(540, 160), new Point(180, 110), new Point(75, 130),
                        new Point(75, 250),  new Point(350, 335), new Point(900, 245)};



                LevelDescription description=new LevelDescription(gold, points, waves, dataTowerSpotsPoints);

                main.gameSc=new GameScreen(main, description);
                main.setScreen(main.gameSc);
            }
        });


        stage.addActor(backButton);
        stage.addActor(levelButton_1);
        stage.addActor(levelButton_2);
        stage.addActor(levelButton_3);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        main.batch.begin();
        main.batch.draw(levelsMenuPicture,0,0);
        main.batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
        levelsMenuPicture.dispose();
    }
}
