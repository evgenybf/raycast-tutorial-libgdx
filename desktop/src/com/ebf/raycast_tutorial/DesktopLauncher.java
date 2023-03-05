package com.ebf.raycast_tutorial;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.ebf.raycast_tutorial.GameMain;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
//		config.setWindowedMode(800, 600);
		config.setWindowSizeLimits(640, 480, -1, -1);
		config.useVsync(true);
		config.setForegroundFPS(60);
		config.setTitle("Ray-casting Tutorial");
		new Lwjgl3Application(new GameMain(), config);
	}
}
