package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gegejesatu.potatone.GameMain;

import GameHelper.Asset;
import GameHelper.GameInfo;
import GameHelper.SoundManager;
import TweenAccessors.SpriteAccessor;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by LENOVO on 27/01/2018.
 */

public class GameOverScreen implements Screen {

    private TweenManager manager;
    private Sprite sprite;
    private GameMain game;

    public GameOverScreen(GameMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        sprite = new Sprite(game.asset.getTexture(Asset.GAME_OVER));
        sprite.setColor(1, 1, 1, 0);
        sprite.setSize(GameInfo.WIDTH, GameInfo.HEIGHT);
        setupTween();
    }

    private void setupTween() {
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        manager = new TweenManager();

        TweenCallback cb = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.setScreen(new HomeScreen(game));
            }
        };

        Tween.to(sprite, SpriteAccessor.ALPHA, 4f).target(1)
                .ease(TweenEquations.easeInOutQuad).repeatYoyo(1, 2f)
                .setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE)
                .start(manager);

        SoundManager.getSoundManager(game).play(game.asset.getSound(Asset.GAME_OVER_SOUND), false);
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
