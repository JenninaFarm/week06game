package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.pauseWhenBackground = true;
		config.x = 0;
		config.y = 0;
		config.width = 800;
		config.height = 400;
		config.title = "swimming beaver";

		new LwjglApplication(new MyGdxGame(), config);
	}
}
