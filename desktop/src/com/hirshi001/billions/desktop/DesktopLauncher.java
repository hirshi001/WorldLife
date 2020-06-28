package com.hirshi001.billions.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hirshi001.billions.MainApp;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = MainApp.size.height;
		config.width = MainApp.size.width;

		//config.resizable = false;
		new LwjglApplication(new MainApp(), config);
	}
}
