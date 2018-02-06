package World;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.gegejesatu.potatone.GameMain;

import GameHelper.GameInfo;
import GameHelper.Asset;
import GameHelper.Pref;
import GameHelper.SoundManager;
import Screens.GameOverScreen;
import Screens.PlayScreen;
import Sprites.Player;
import Sprites.Tone;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import spritemanager.ToneSpawner;

/**
 * Created by LENOVO on 26/01/2018.
 */

public class ToneTrack {

    public static final int TRACK_WIDTH = GameInfo.WIDTH;
    public static final int TRACK_HEIGHT = GameInfo.HEIGHT * 2 / 3;
    private final SoundManager soundManager;

    private GameMain game;
    private PlayScreen playScreen;
    private int padding;

    private final Sprite toneTrackBg;
    private Sprite toneTrackFrame;

    private ToneSpawner goodToneSpawner;
    private ToneSpawner badToneSpawner;
    private ToneSpawner obstacleSpawner;
    private ToneSpawner starSpawner;
    private ToneSpawner bombSpawner;

    private Sprite explosion;
    private Sprite doubleScore;
    private TextureRegion[] explosionRegions;
    private Animation explosionAnim;

    public Player player;
    private TweenManager gameOverTween;
    private TweenManager doubleScoreTween;
    private boolean isGameOverSetup;
    private int scoreIncrease;

    private ScoreState scoreState;
    private boolean isDoubleScoreSetup;

    private enum ScoreState{
        NORMAL, DOUBLE
    }

    public ToneTrack(GameMain game, PlayScreen playScreen) {
        this.game = game;
        this.playScreen = playScreen;
        this.padding = 70;
        this.player = new Player(game, padding*2, TRACK_HEIGHT/2 );
        this.toneTrackBg = new Sprite(game.asset.getTexture(Asset.POTATO_FRAME_BG));
        this.toneTrackFrame = new Sprite(game.asset.getTexture(Asset.POTATONE_FRAME1));
        this.scoreIncrease = 1;
        this.scoreState = ScoreState.NORMAL;
        this.soundManager = SoundManager.getSoundManager(game);
        initSpawner();
        initExplosion();
        initDoubleScore();
    }

    private void initDoubleScore() {
        doubleScore = new Sprite(game.asset.getTexture(Asset.DOUBLE_SCORE));
        doubleScore.setScale(0.7f);
        doubleScore.setSize(doubleScore.getWidth()*doubleScore.getScaleX(), doubleScore.getHeight()*doubleScore.getScaleY());
        doubleScore.setPosition((GameInfo.WIDTH/2) - (doubleScore.getWidth()/2), (GameInfo.HEIGHT)-(doubleScore.getWidth()/2));
        doubleScoreTween = new TweenManager();
    }

    private void initExplosion() {
        explosionAnim = getExplosionAnim();
        explosion = new Sprite(explosionRegions[0]);
        gameOverTween = new TweenManager();
    }

    private void initSpawner() {
        switch (Pref.getData().getInteger(Pref.LEVEL)){
            case GameInfo.EASY:
                initSpawnerEasy();
                break;
            case GameInfo.MEDIUM:
                initSpawnerMedium();
                break;
            case GameInfo.HARD:
                initSpawnerHard();
                break;
            default:
                break;
        }
    }

    private void initSpawnerEasy(){
        goodToneSpawner = new ToneSpawner(game, Tone.GOOD, 2f, -3f);
        badToneSpawner = new ToneSpawner(game, Tone.BAD, 4f, -4f);
        obstacleSpawner = new ToneSpawner(game, Tone.OBSTACLE, 2.5f, -2f);
        starSpawner = new ToneSpawner(game, Tone.STAR, 9f, -1f);
        bombSpawner = new ToneSpawner(game, Tone.BOMB, 7f, -3.5f);
    }

    private void initSpawnerMedium(){
        goodToneSpawner = new ToneSpawner(game, Tone.GOOD, 2f, -3f);
        badToneSpawner = new ToneSpawner(game, Tone.BAD, 2f, -4f);
        obstacleSpawner = new ToneSpawner(game, Tone.OBSTACLE, 1.5f, -2f);
        starSpawner = new ToneSpawner(game, Tone.STAR, 5f, -1f);
        bombSpawner = new ToneSpawner(game, Tone.BOMB, 4f, -3.5f);
    }

    private void initSpawnerHard(){
        goodToneSpawner = new ToneSpawner(game, Tone.GOOD, 2f, -3f);
        badToneSpawner = new ToneSpawner(game, Tone.BAD, 1f, -4f);
        obstacleSpawner = new ToneSpawner(game, Tone.OBSTACLE, 1.2f, -2f);
        starSpawner = new ToneSpawner(game, Tone.STAR, 3f, -1f);
        bombSpawner = new ToneSpawner(game, Tone.BOMB, 0.7f, -3.5f);
    }

    public void update(float delta, float runtime) {
        player.update();
        explosion.setPosition(player.getX()-(player.getWidth()/2) + 10, player.getY()-(player.getHeight()/2));
        goodToneSpawner.update(delta);
        badToneSpawner.update(delta);
        obstacleSpawner.update(delta);
        starSpawner.update(delta);
        bombSpawner.update(delta);

        if(PlayScreen.state != PlayScreen.GameState.VICTORY){
            detectCollision();
        }

    }

    private void detectCollision() {
        collisionWithGoodTone();
        collisionWithBadTone();
        collisionWithObstacle();
        collisionWithStar();
        collisionWithBomb();
    }

    private void collisionWithGoodTone() {
        for(int i = 0; i< goodToneSpawner.tones.size(); i++){
            Tone tone = goodToneSpawner.tones.get(i);
            if(Intersector.overlaps(tone.bound, player.bound)){
                goodToneSpawner.tones.remove(tone);
                PlayScreen.score += scoreIncrease;
                soundManager.play(game.asset.getSound(Asset.SFX_GOODNOTE), false);
            }
        }
    }

    private void collisionWithBadTone() {
        for(int i = 0; i< badToneSpawner.tones.size(); i++){
            Tone tone = badToneSpawner.tones.get(i);
            if(Intersector.overlaps(tone.bound, player.bound)){
                badToneSpawner.tones.remove(tone);
                PlayScreen.score--;
                playScreen.prepareTransition(255, 255, 255, .3f);
                soundManager.play(game.asset.getSound(Asset.SFX_BADNOTE), false);
            }
        }
    }

    private void collisionWithObstacle() {
        for(int i = 0; i< obstacleSpawner.tones.size(); i++){
            Tone tone = obstacleSpawner.tones.get(i);
            if(Intersector.overlaps(tone.bound, player.bound)){
                obstacleSpawner.tones.remove(tone);
                PlayScreen.life--;
                PlayScreen.lifeLabel.lifes.remove(PlayScreen.life);
                playScreen.prepareTransition(255, 255, 255, .3f);
                soundManager.play(game.asset.getSound(Asset.SFX_INSECT), false);
            }
        }
    }

    private void collisionWithStar() {
        for(int i = 0; i< starSpawner.tones.size(); i++){
            Tone tone = starSpawner.tones.get(i);
            if(Intersector.overlaps(tone.bound, player.bound)){
                starSpawner.tones.remove(tone);
                // double score
                scoreIncrease = 2;
                PlayScreen.score += 2;
                scoreState = ScoreState.DOUBLE;
                soundManager.play(game.asset.getSound(Asset.SFX_STAR), false);
            }
        }
    }

    private void collisionWithBomb() {
        for(int i = 0; i< bombSpawner.tones.size(); i++){
            Tone tone = bombSpawner.tones.get(i);
            if(Intersector.overlaps(tone.bound, player.bound)){
                bombSpawner.tones.remove(tone);
                PlayScreen.lifeLabel.lifes.clear();
                PlayScreen.life = 0;
                PlayScreen.state = PlayScreen.GameState.GAMEOVER;
                playScreen.prepareTransition(255, 255, 255, .3f);
                soundManager.play(game.asset.getSound(Asset.SFX_BOMB), false);
            }
        }
    }

    public void draw(float delta, float runtime) {
        toneTrackBg.draw(game.batch);
        player.draw(delta, runtime);
        goodToneSpawner.draw(delta, runtime);
        badToneSpawner.draw(delta, runtime);
        obstacleSpawner.draw(delta, runtime);
        starSpawner.draw(delta, runtime);
        bombSpawner.draw(delta, runtime);
        toneTrackFrame.draw(game.batch);
    }

    public void drawExplosion(float delta, float runtime){
        if(PlayScreen.state == PlayScreen.GameState.GAMEOVER){
            TextureRegion explosionRegion = (TextureRegion) explosionAnim.getKeyFrame(runtime);
            explosion.setRegion(explosionRegion);
            explosion.draw(game.batch);

            if(isGameOverSetup){
                gameOverTween.update(delta);
            }else {
                setupGameOver();
                isGameOverSetup = true;
            }
        }
    }

    public void drawDoubleScore(float delta, float runtime){
        if(scoreState == ScoreState.DOUBLE) {
            doubleScore.draw(game.batch);
            setupDoubleScore(delta);
        }
    }

    private Animation getExplosionAnim() {

        Texture toneTexture = game.asset.getTexture(Asset.EXPLOSION);
        // get texture region
        explosionRegions = new TextureRegion[3];
        int spriteAxisX = 0;
        int spriteAxisY = 0;
        int spriteWidth = 391;
        int spriteHeight = 391;
        for (int i = 0; i < explosionRegions.length; i++) {
            explosionRegions[i] = new TextureRegion(toneTexture, spriteAxisX, spriteAxisY, spriteWidth, spriteHeight);
            spriteAxisX += spriteWidth;
        }

        // sprite animations
        Animation toneAnim = new Animation(0.2f, explosionRegions);
        toneAnim.setPlayMode(Animation.PlayMode.LOOP);

        return toneAnim;
    }

    private void setupGameOver() {
        TweenCallback cb = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.setScreen(new GameOverScreen(game));
            }
        };

        Tween.call(cb).delay(2.5f).start(gameOverTween);
    }

    private void setupDoubleScore(float delta) {
        if(isDoubleScoreSetup){
            doubleScoreTween.update(delta);
        }else {
            isDoubleScoreSetup = true;
            TweenCallback cb = new TweenCallback() {
                @Override
                public void onEvent(int type, BaseTween<?> source) {
                    isDoubleScoreSetup = false;
                    scoreIncrease = 1;
                    scoreState = ScoreState.NORMAL;
                }
            };

            Tween.call(cb).delay(7f).start(doubleScoreTween);
        }
    }
}
