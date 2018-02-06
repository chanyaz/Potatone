package Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.gegejesatu.potatone.GameMain;

import GameHelper.GameInfo;
import GameHelper.Asset;
import World.ToneTrack;

/**
 * Created by LENOVO on 27/01/2018.
 */

public class Tone extends Sprite {

    public static final int GOOD = 0;
    public static final int BAD = 1;
    public static final int OBSTACLE = 2;
    public static final int STAR = 3;
    public static final int BOMB = 4;

    private GameMain game;
    private Animation toneAnim;
    private TextureRegion[] toneRegions;
    public Rectangle bound;
    private float speed;
    private int type;

    public Tone(GameMain game, float speed, int type){
        this.game = game;
        this.type = type;
        this.toneAnim = getToneAnim();
        this.speed = speed;
        this.bound = new Rectangle();
        this.set(new Sprite(toneRegions[0]));

        setScale(0.3f);
        setSize(getWidth()*getScaleX(), getHeight()*getScaleY());
        setPositionY();
    }

    private Animation getToneAnim(){

        String fileName = getRandomToneFileName();
        Texture toneTexture = game.asset.getTexture(fileName);
        // get texture region
        toneRegions = new TextureRegion[3];
        int spriteAxisX = 0;
        int spriteAxisY = 0;
        int spriteWidth = 391;
        int spriteHeight = 391;
        for(int i = 0; i< toneRegions.length; i++){
            toneRegions[i] = new TextureRegion(toneTexture, spriteAxisX, spriteAxisY, spriteWidth, spriteHeight);
            spriteAxisX += spriteWidth;
        }

        // sprite animations
        Animation toneAnim = new Animation(0.2f, toneRegions);
        toneAnim.setPlayMode(Animation.PlayMode.LOOP);

        return toneAnim;
    }

    private String getRandomToneFileName() {
        String fileName;
        switch (type){
            case GOOD:
                fileName = getRandomGoodTone();
                break;
            case BAD:
                fileName = getRandomBadTone();
                break;
            case OBSTACLE:
                fileName = getRandomObstacleTone();
                break;
            case STAR:
                fileName = getRandomStarTone();
                break;
            case BOMB:
                fileName = getRandomBombTone();
                break;
            default:
                fileName = "";
                break;
        }
        return fileName;
    }

    private String getRandomGoodTone() {
        int random = (int) (Math.random() * 3 + 1);
        String fileName;
        switch (random){
            case 1:
                fileName = Asset.GOOD_TONE1;
                break;
            case 2:
                fileName = Asset.GOOD_TONE2;
                break;
            default:
                fileName = Asset.GOOD_TONE3;
                break;
        }
        return fileName;
    }

    private String getRandomBadTone() {
        int random = (int) (Math.random() * 4 + 1);
        String fileName;
        switch (random){
            case 1:
                fileName = Asset.BAD_TONE1;
                break;
            case 2:
                fileName = Asset.BAD_TONE2;
                break;
            case 3:
                fileName = Asset.BAD_TONE3;
                break;
            default:
                fileName = Asset.BAD_TONE4;
                break;
        }
        return fileName;
    }

    private String getRandomObstacleTone() {
        return Asset.INSECT;
    }

    private String getRandomStarTone() {
        return Asset.STAR;
    }

    private String getRandomBombTone() {
        return Asset.BOMB;
    }

    public void update(){
        setX(getX() + speed);
        bound.set(getX() + 15, getY() + 15, getWidth() - 30, getHeight() - 30);
    }

    public void draw(float delta, float runtime){
        if(getX() + getWidth() > 0){
            game.batch.draw((TextureRegion) toneAnim.getKeyFrame(runtime), getX(), getY(), getWidth(), getHeight());
        }
    }

    public void setPositionY() {
        float posY;
        int randomPos = (int) (Math.random() * 3 + 1);
            switch (randomPos){
            case 1:
                posY = getY() - getHeight() - 10;
                break;
            case 3:
                posY = getY() + getHeight() + 10;
                break;
            default:
                posY = getY();
                break;
        }

        setPosition(GameInfo.WIDTH + 8, posY + (ToneTrack.TRACK_HEIGHT/2) - (getHeight()/2));
    }
}
