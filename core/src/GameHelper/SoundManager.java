package GameHelper;

import com.badlogic.gdx.audio.Music;
import com.gegejesatu.potatone.GameMain;

/**
 * Created by LENOVO on 28/01/2018.
 */

public class SoundManager {

    public static SoundManager soundManager;
    private static Asset asset;
    private Music homeIntro;
    private Music layer1;
    private Music layer2;
    private Music layer3;
    private Music layer4;
    private Music layer5;
    private Music storyIntro;

    public static SoundManager getSoundManager(GameMain game){
        asset = game.asset;
        if(soundManager == null){
            soundManager = new SoundManager();
        }
        return soundManager;
    }

    public void play(Music music, boolean isLoop){
        music.setLooping(isLoop);
        music.play();
    }

    public void stop(Music music){
        music.stop();
    }

    public void mute(Music music){
        music.setVolume(0f);
    }

    public void speak(Music music){
        music.setVolume(1f);
    }

    public void playMenuSound(){
        homeIntro = asset.getSound(Asset.MAIN_MENU_SOUND);
        homeIntro.setLooping(true);
        homeIntro.play();
    }

    public void stopMenuSound(){
        homeIntro.stop();
    }

    public void playStorySound(){
        storyIntro = asset.getSound(Asset.STORY_INTRO_SOUND);
        storyIntro.setLooping(true);
        storyIntro.play();
    }

    public void stopStorySound(){
        storyIntro.stop();
    }

    public void initLayersMusic(){
        layer1 = asset.getSound(Asset.LAYER1);
        layer2 = asset.getSound(Asset.LAYER2);
        layer3 = asset.getSound(Asset.LAYER3);
        layer4 = asset.getSound(Asset.LAYER4);
        layer5 = asset.getSound(Asset.LAYER5);
        layer1.setLooping(true);
        layer2.setLooping(true);
        layer3.setLooping(true);
        layer4.setLooping(true);
        layer5.setLooping(true);
        layer1.setVolume(0);
        layer2.setVolume(0);
        layer3.setVolume(0);
        layer4.setVolume(0);
        layer5.setVolume(0);
        layer1.play();
        layer2.play();
        layer3.play();
        layer4.play();
        layer5.play();
    }

    public void playLayer1(){
        layer1.setVolume(1);
    }

    public void playLayer2(){
        layer2.setVolume(1);
    }

    public void playLayer3(){
        layer3.setVolume(1);
    }

    public void playLayer4(){
        layer4.setVolume(1);
    }

    public void playLayer5(){
        layer5.setVolume(1);
    }

    public void stopAllLayerSound() {
        layer1.stop();
        layer2.stop();
        layer3.stop();
        layer4.stop();
        layer5.stop();
    }


}
