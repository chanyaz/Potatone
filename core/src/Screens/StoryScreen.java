package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.gegejesatu.potatone.GameMain;

import GameHelper.Asset;
import GameHelper.GameInfo;
import GameHelper.Pref;
import GameHelper.SoundManager;
import Sprites.SimpleButton;
import TweenAccessors.SpriteAccessor;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by LENOVO on 27/01/2018.
 */

public class StoryScreen implements Screen {

    private TweenManager manager;
    private Sprite sprite;
    private GameMain game;

    private StoryState storyState;
    private enum StoryState{
        STORY_1, STORY_2, STORY_3
    }

    public StoryScreen(GameMain game) {
        this.game = game;
        storyState = StoryState.STORY_1;
        SoundManager.getSoundManager(game).playStorySound();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void show() {
        sprite = new Sprite(game.asset.getTexture(Asset.STORY1));
        sprite.setColor(1, 1, 1, 0);
        sprite.setSize(GameInfo.WIDTH, GameInfo.HEIGHT);
        sprite.setPosition(0,0);

        setupTween();
    }

    private void setupTween() {
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        manager = new TweenManager();

        TweenCallback cb = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                switch (storyState){
                    case STORY_1:
                        storyState = StoryState.STORY_2;
                        sprite.setTexture(game.asset.getTexture(Asset.STORY2));
                        setupTween();
                        break;
                    case STORY_2:
                        storyState = StoryState.STORY_3;
                        sprite.setTexture(game.asset.getTexture(Asset.STORY3));
                        setupTween();
                        break;
                    case STORY_3:
                        Pref.getData().setBoolean(Pref.IS_STORY_READ, true);
                        SoundManager.getSoundManager(game).stopStorySound();
                        game.setScreen(new PlayScreen(game));
                        break;
                }
            }
        };

        Tween.to(sprite, SpriteAccessor.ALPHA, 2f).target(1)
                .ease(TweenEquations.easeInOutQuad).repeatYoyo(1, .9f)
                .setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE)
                .start(manager);
    }

    @Override
    public void render(float delta) {
        manager.update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        sprite.draw(game.batch);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }
}
