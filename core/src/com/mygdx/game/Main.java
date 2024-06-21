package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Screens.LevelsScreen;
import com.mygdx.game.Screens.MainMenuScreen;

public class Main extends Game {
	public SpriteBatch batch;

	public static int width=1200, height=700;

	public MainMenuScreen mainSc;
	public LevelsScreen levelsSc;

	public GameScreen gameSc;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		mainSc= new MainMenuScreen(this);
		levelsSc=new LevelsScreen(this);

		setScreen(mainSc);
	}

	public void resize(int width, int height) {

	}


	@Override
	public void dispose () {
		super.dispose();

		batch.dispose();
	}
}
