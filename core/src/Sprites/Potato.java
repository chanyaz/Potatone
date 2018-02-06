package Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.gegejesatu.potatone.GameMain;

import GameHelper.Asset;
import GameHelper.GameInfo;

/**
 * Created by LENOVO on 27/01/2018.
 */

public class Potato extends Sprite{
    private GameMain game;
    public Rectangle bound;
    public TextureRegion[] regions;
    public Animation potatoAnim;

    public Potato(GameMain game, float x, float y){
        this.game = game;
        bound = new Rectangle();
        potatoAnim = getAnim();
        set(new Sprite(regions[0]));
        setScale(0.4f);
        setSize(getWidth()*getScaleX(), getHeight()*getScaleY());
        setPosition(x, y);
    }

    private Animation getAnim(){

        Texture playerSprite = game.asset.getTexture(Asset.POTATONE_SPRITES);
        // get texture region
        regions = new TextureRegion[3];
        int spriteAxisX = 0;
        int spriteAxisY = 0;
        int spriteWidth = 760;
        int spriteHeight = 761;
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
        bound.set(getX(), getY(), getWidth(), getHeight());
    }

    public void draw(float delta, float runtime){
        this.game.batch.draw((TextureRegion) potatoAnim.getKeyFrame(runtime), getX(), getY(), getWidth(), getHeight());
    }

    public void moveUp() {
        if (getCenterY() > (GameInfo.HEIGHT / 2) - 10) {
            setX(getX() - getWidth());
        }
    }

    public void moveDown(){
        if( getCenterY() < (GameInfo.HEIGHT/2) + 10){
            setX(getX()  + getWidth());
        }
    }

    public float getCenterY() {
        return getY() + (getHeight()/2);
    }
}
