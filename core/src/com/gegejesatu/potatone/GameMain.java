package com.gegejesatu.potatone;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import GameHelper.Asset;
import GameHelper.Pref;
import Screens.SplashScreen;

public class GameMain extends Game {

	public SpriteBatch batch;
	public Asset asset;
	private boolean isLoaded = false;

	@Override
	public void create () {
		batch = new SpriteBatch();
		asset = new Asset();
		asset.loadAssets();
		resetCache();
	}

	private void resetCache() {
		Pref.getData().setBoolean(Pref.IS_TUTORIAL_DONE, false);
		Pref.getData().setBoolean(Pref.IS_STORY_READ, false);
	}

	@Override
	public void render () {
		super.render();
		if(!isLoaded){
			if(asset.isAllLoaded()){
				isLoaded = true;
				asset.getSound(Asset.SOUND_SPLASH).play();
				setScreen(new SplashScreen(this));
			}else {
				asset.manager.update();
			}
		}

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		asset.dispose();
	}
}
