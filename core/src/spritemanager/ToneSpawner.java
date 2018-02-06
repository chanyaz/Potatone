package spritemanager;

import com.gegejesatu.potatone.GameMain;

import java.util.ArrayList;
import java.util.List;

import Sprites.Tone;

/**
 * Created by LENOVO on 27/01/2018.
 */

public class ToneSpawner {
    private GameMain game;
    public List<Tone> tones;
    private float timerSpawn;
    private float spawnTime;
    private float speed;
    private float timerSpeed;
    private int type;

    public ToneSpawner(GameMain game, int type, float spawnTime, float speed){
        this.game = game;
        this.type = type;
        this.spawnTime = spawnTime;
        this.speed = speed;
        this.tones = new ArrayList<Tone>();
    }

    public void update(float delta) {
        spawn(delta);
        updateTone();
    }

    private void updateTone() {
        for (int i = 0; i< tones.size(); i++) {
            Tone tone = tones.get(i);
            tone.update();
        }
    }

    private void spawn(float delta) {
        Tone tone;
        if(spawnTime < 0.2f){
            spawnTime = 0.2f;
        }
        if(timerSpawn >= spawnTime){
            timerSpawn = 0;
            tone = new Tone(game, speed, type);
            tones.add(tone);
        }else {
            timerSpawn += delta;
        }
    }

    private void changeSpeed(float delta) {
        if(timerSpeed >= 6){
            timerSpeed = 0;
            speed -= .5f;
            spawnTime -= .125f;
        }else {
            timerSpeed += delta;
        }
    }

    public void draw(float delta, float runtime){
        for (int i = 0; i< tones.size(); i++){
            Tone tone = tones.get(i);
            if(tone != null){
                tone.draw(delta, runtime);
            }

            if(tone.getX() + tone.getHeight() < 0){
                tones.remove(tone);
            }
        }

    }
}
