package com.gegejesatu.potatone.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gegejesatu.potatone.GameMain;

import GameHelper.GameInfo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Potatone";
		config.width = GameInfo.WIDTH/2;
		config.height = GameInfo.HEIGHT/2;
		new LwjglApplication(new GameMain(), config);
	}
}
