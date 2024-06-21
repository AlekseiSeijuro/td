package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Main;

public class MainMenuScreen implements Screen {

    Main main;

    private Stage stage;
    public Texture mainMenuPicture;


    public MainMenuScreen(Main main){
        this.main=main;
        mainMenuPicture = new Texture("ScreensPictures/MainMenuPicture.jpg");

        stage = new Stage(new ScreenViewport());

        //Кнопки
        Texture playTexture = new Texture("Buttons/LevelsButton.png");
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(playTexture));
        ImageButton levelsButton = new ImageButton(drawable);
        levelsButton.setPosition(Main.width/2-64, Main.height/2);

        levelsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(main.levelsSc);
            }
        });

        stage.addActor(levelsButton);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        main.batch.begin();
        main.batch.draw(mainMenuPicture,0,0);
        main.batch.end();

        stage.act();
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
        mainMenuPicture.dispose();
    }
}
