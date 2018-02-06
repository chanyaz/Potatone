package Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.gegejesatu.potatone.GameMain;

import GameHelper.Asset;

/**
 * Created by LENOVO on 27/01/2018.
 */

public class SimpleButton extends Sprite {

    private GameMain game;
    private float x, y, width, height;
    private Texture buttonUp;
    private Texture buttonDown;
    private Rectangle bounds;
    private boolean isPressed = false;

    public SimpleButton(GameMain game, float x, float y, Texture buttonUp, Texture buttonDown, float scale) {
        this.game = game;
        this.width = buttonUp.getWidth() * scale;
        this.height = buttonUp.getHeight() * scale;
        this.x = x - (width/2);
        this.y = y - (height/2);

        this.buttonUp = buttonUp;
        this.buttonDown = buttonDown;

        bounds = new Rectangle(this.x, this.y, width, height);
        setPosition(this.x, this.y);
        setSize(this.width, this.height);
    }

    public boolean isClicked(int screenX, int screenY) {
        return bounds.contains(screenX, screenY);
    }

    public void draw(SpriteBatch batcher) {
        if (isPressed) {
            batcher.draw(buttonDown, x, y, width, height);
        } else {
            batcher.draw(buttonUp, x, y, width, height);
        }
    }

    public boolean isTouchDown(float screenX, float screenY) {

        if (bounds.contains(screenX, screenY)) {
            isPressed = true;
            return true;
        }

        return false;
    }

    public boolean isTouchUp(float screenX, float screenY) {

        // It only counts as a touchUp if the button is in a pressed state.
        if (bounds.contains(screenX, screenY) && isPressed) {
            isPressed = false;
            return true;
        }

        // Whenever a finger is released, we will cancel any presses.
        isPressed = false;
        return false;
    }
}
