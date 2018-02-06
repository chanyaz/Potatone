package Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.gegejesatu.potatone.GameMain;

import GameHelper.Asset;
import World.ToneTrack;

/**
 * Created by LENOVO on 26/01/2018.
 */

public class Player extends Sprite {

    private GameMain game;
    public Rectangle bound;
    public TextureRegion[] regions;
    public Animation playerAnim;

    public Player(GameMain game, float x, float y){
        this.game = game;
        bound = new Rectangle();
        playerAnim = getAnim();
        set(new Sprite(regions[0]));
        setScale(0.3f);
        setSize(getWidth()*getScaleX(), getHeight()*getScaleY());
        setPosition(x, y - (getHeight()/2));
    }

    private Animation getAnim(){

        Texture playerSprite = game.asset.getTexture(Asset.LOVE);
        // get texture region
        regions = new TextureRegion[3];
        int spriteAxisX = 0;
        int spriteAxisY = 0;
        int spriteWidth = 391;
        int spriteHeight = 391;
        for(int i = 0; i< regions.length; i++){
            regions[i] = new TextureRegion(playerSprite, spriteAxisX, spriteAxisY, spriteWidth, spriteHeight);
            spriteAxisX += spriteWidth;
        }

        // sprite animations
        Animation toneAnim = new Animation(0.2f, regions);
        toneAnim.setPlayMode(Animation.PlayMode.LOOP);

        return toneAnim;
    }

    public void update(){
        bound.set(getX() + 15, getY() + 15, getWidth() - 30, getHeight() - 30);
    }

    public void draw(float delta, float runtime){
        this.game.batch.draw((TextureRegion) playerAnim.getKeyFrame(runtime), getX(), getY(), getWidth(), getHeight());
    }

    public void moveDown() {
        if (getCenterY() > (ToneTrack.TRACK_HEIGHT / 2) - 10) {
            setY(getY() - getWidth() - 10);
        }
    }

    public void moveUp(){
        if( getCenterY() < (ToneTrack.TRACK_HEIGHT/2) + 10){
            setY(getY() + getWidth() + 10);
        }
    }

    public float getCenterY() {
        return getY() + (getHeight()/2);
    }
}
