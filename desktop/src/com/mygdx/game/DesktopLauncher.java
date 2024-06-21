package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.Main;

import java.util.ConcurrentModificationException;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		try {
			Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
			config.setForegroundFPS(60);
			config.setTitle("TDFULL");
			config.setWindowedMode(Main.width, Main.height);
			new Lwjgl3Application(new Main(), config);
		}
		catch(ConcurrentModificationException e){
			System.out.println("Конкурентный диалог!");
		}
	}
}
