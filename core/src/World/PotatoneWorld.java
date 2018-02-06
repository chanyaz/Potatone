package World;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.gegejesatu.potatone.GameMain;

import GameHelper.GameInfo;
import GameHelper.Asset;
import GameHelper.Pref;
import GameHelper.SoundManager;
import Screens.GameOverScreen;
import Screens.PlayScreen;
import Screens.VictoryScreen;
import Sprites.LifeLabel;
import Sprites.Potato;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by LENOVO on 26/01/2018.
 */

public class PotatoneWorld {

    public static final int WORLD_WIDTH = GameInfo.WIDTH;
    public static final int WORLD_HEIGHT = GameInfo.HEIGHT * 1 / 3;

    private GameMain game;
    private Rectangle bound;
    private Sprite environment;
    private Potato potato;
    private int scoreMileStone;

    private boolean isVictorySetup;
    private TweenManager manager;
    private SoundManager soundManager;

    public PotatoneWorld(GameMain game) {
        this.game = game;
        this.bound = new Rectangle(0, ToneTrack.TRACK_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT);
        this.potato = new Potato(game, 25, ToneTrack.TRACK_HEIGHT - 60);
        environment = new Sprite(game.asset.getTexture(Asset.POTATONE_ENV1));
        manager = new TweenManager();
        initScoreMileStone();
        initSounds();
    }

    private void initScoreMileStone() {
        switch (Pref.getData().getInteger(Pref.LEVEL)){
            case GameInfo.MEDIUM:
                scoreMileStone = 10;
                break;
            case GameInfo.HARD:
                scoreMileStone = 15;
                break;
            default:
                // EASY : default
                scoreMileStone = 5;
                break;
        }
    }

    private void initSounds() {
        soundManager = SoundManager.getSoundManager(game);
        soundManager.initLayersMusic();
        soundManager.playLayer1();
    }

    public void update(float delta, float runtime) {

        potato.update();
        checkScore();
        checkLife();
    }

    private void checkScore() {
        if(PlayScreen.score >= scoreMileStone && PlayScreen.score < scoreMileStone*2){
            soundManager.playLayer2();
            environment.setTexture(game.asset.getTexture(Asset.POTATONE_ENV2));
        }else if(PlayScreen.score >= scoreMileStone*2 && PlayScreen.score < scoreMileStone*3){
            soundManager.playLayer3();
            environment.setTexture(game.asset.getTexture(Asset.POTATONE_ENV3));
        }else if(PlayScreen.score >= scoreMileStone*3 && PlayScreen.score < scoreMileStone*4){
            soundManager.playLayer4();
            environment.setTexture(game.asset.getTexture(Asset.POTATONE_ENV4));
        }else if(PlayScreen.score >= scoreMileStone*4 && PlayScreen.score < scoreMileStone*5){
            soundManager.playLayer5();
            environment.setTexture(game.asset.getTexture(Asset.POTATONE_ENV5));
        }else if(PlayScreen.score >= scoreMileStone*5){
            PlayScreen.state = PlayScreen.GameState.VICTORY;
        }else {
            soundManager.playLayer1();
            environment.setTexture(game.asset.getTexture(Asset.POTATONE_ENV1));
        }
    }

    private void checkLife(){
        if(PlayScreen.life <= 0){
            soundManager.stopAllLayerSound();
            PlayScreen.state = PlayScreen.GameState.GAMEOVER;
        }
    }

    public void draw(float delta, float runtime) {
        game.batch.draw(environment, bound.x, bound.y, bound.width, bound.height);
        potato.draw(delta, runtime);

        if(PlayScreen.state == PlayScreen.GameState.VICTORY){
            setupVictory(delta);
        }
    }

    private void setupVictory(float delta) {
        final PotatoneWorld self = this;
        if(isVictorySetup){
            manager.update(delta);
        }else {
            isVictorySetup = true;
            TweenCallback cb = new TweenCallback() {
                @Override
                public void onEvent(int type, BaseTween<?> source) {
                    soundManager.stopAllLayerSound();
                    game.setScreen(new VictoryScreen(game));
                }
            };
            Tween.call(cb).delay(4.5f).start(manager);
        }
    }





}
