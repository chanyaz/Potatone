package Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.gegejesatu.potatone.GameMain;

import java.util.ArrayList;
import java.util.List;

import GameHelper.Asset;
import GameHelper.GameInfo;
import World.ToneTrack;

/**
 * Created by LENOVO on 27/01/2018.
 */

public class LifeLabel{

    private GameMain game;
    public TextureRegion[] regions;
    public Animation lifeAnim;
    public List<Sprite> lifes;

    public LifeLabel(GameMain game){
        this.game = game;
        lifes = new ArrayList<Sprite>();
        lifeAnim = getAnim();
        initLifes();
    }

    private void initLifes() {
        int posX = GameInfo.WIDTH - 350;
        for(int i=0; i<3; i++){
            Sprite life = new Sprite(regions[0]);
            life.setScale(0.4f);
            life.setSize(life.getWidth()*life.getScaleX(), life.getHeight()*life.getScaleY());
            life.setPosition(posX, 350);
            lifes.add(life);
            posX += 50;
        }
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

    public void draw(float delta, float runtime){
        for(int i=0; i<lifes.size(); i++){
            lifes.get(i).setRegion((TextureRegion) lifeAnim.getKeyFrame(runtime));
            lifes.get(i).draw(game.batch);
        }
    }
}
