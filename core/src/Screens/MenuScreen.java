package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.gegejesatu.potatone.GameMain;

import GameHelper.Asset;
import GameHelper.GameInfo;
import GameHelper.Pref;
import GameHelper.SoundManager;
import Sprites.SimpleButton;

/**
 * Created by LENOVO on 27/01/2018.
 */

public class MenuScreen implements Screen, InputProcessor{

    private GameMain game;
    private OrthographicCamera cam;
    private float runtime;

    private Sprite potato;
    private SimpleButton buttonLevelEasy;
    private SimpleButton buttonLevelMedium;
    private SimpleButton buttonLevelHard;
    private SimpleButton backbutton;

    public MenuScreen(GameMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        potato = new Sprite(game.asset.getTexture(Asset.POTATONE_ONLY));
        potato.setScale(0.5f);
        potato.setSize(potato.getWidth()*potato.getScaleX(), potato.getHeight()*potato.getScaleY());
        potato.setPosition(-100,100);
        initCamera();
        setInput();
        initButtonLevelEasy();
        initButtonLevelMedium();
        initButtonLevelHard();
        initBackButton();
    }

    private void initBackButton() {
        backbutton = new SimpleButton(game, (GameInfo.WIDTH) - 150, 100,
                game.asset.getTexture(Asset.BACK_BUTTON), game.asset.getTexture(Asset.POTATO_BUTTON_DOWN), 0.5f);
    }

    private void initButtonLevelEasy() {
        buttonLevelEasy = new SimpleButton(game, (GameInfo.WIDTH/2) - 300, (GameInfo.HEIGHT/2),
                game.asset.getTexture(Asset.EASY_BUTTON), game.asset.getTexture(Asset.POTATO_BUTTON_DOWN), 1f);
    }

    private void initButtonLevelMedium() {
        buttonLevelMedium = new SimpleButton(game, (GameInfo.WIDTH/2), (GameInfo.HEIGHT/2),
                game.asset.getTexture(Asset.MEDIUM_BUTTON), game.asset.getTexture(Asset.POTATO_BUTTON_DOWN), 1f);
    }

    private void initButtonLevelHard() {
        buttonLevelHard = new SimpleButton(game, (GameInfo.WIDTH/2) + 300, (GameInfo.HEIGHT/2),
                game.asset.getTexture(Asset.HARD_BUTTON), game.asset.getTexture(Asset.POTATO_BUTTON_DOWN), 1f);
    }

    private void setInput() {
        Gdx.input.setInputProcessor(this);
    }

    private void initCamera() {
        cam = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
        cam.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);
        game.batch.setProjectionMatrix(cam.combined);
    }

    @Override
    public void render(float delta) {
        runtime += delta;

        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw batch
        game.batch.begin();

        potato.draw(game.batch);
        buttonLevelEasy.draw(game.batch);
        buttonLevelMedium.draw(game.batch);
        buttonLevelHard.draw(game.batch);
        backbutton.draw(game.batch);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    /**
     * Called when a key was pressed
     *
     * @return whether the input was processed
     */
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    /**
     * Called when a key was released
     *
     * @return whether the input was processed
     */
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    /**
     * Called when a key was typed
     *
     * @param character The character
     * @return whether the input was processed
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Called when the screen was touched or a mouse button was pressed.
     *
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float touchX = scaleX(screenX);
        float touchY = scaleY(screenY);

        buttonLevelEasy.isTouchDown(touchX, touchY);
        buttonLevelMedium.isTouchDown(touchX, touchY);
        buttonLevelHard.isTouchDown(touchX, touchY);
        backbutton.isTouchDown(touchX, touchY);

        return true;
    }

    public float scaleX(int screenX) {
        float scaleFactorX = ((float) screenX) / Gdx.graphics.getWidth();
        float touchX = scaleFactorX * GameInfo.WIDTH;
        return touchX;
    }

    public float scaleY(int screenY) {
        float scaleFactorY = ((float) screenY) / Gdx.graphics.getHeight();
        float touchY = scaleFactorY * GameInfo.HEIGHT;
        return GameInfo.HEIGHT - touchY;
    }

    /**
     * Called when a finger was lifted or a mouse button was released.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.
     * @param button  the button   @return whether the input was processed
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        float touchX = scaleX(screenX);
        float touchY = scaleY(screenY);
        if (buttonLevelEasy.isTouchUp(touchX, touchY)) {
            gotoPlayScreen(GameInfo.EASY);
        }else if(buttonLevelMedium.isTouchUp(touchX, touchY)) {
            gotoPlayScreen(GameInfo.MEDIUM);
        }else if(buttonLevelHard.isTouchUp(touchX, touchY)) {
            gotoPlayScreen(GameInfo.HARD);
        }else if(backbutton.isTouchUp(touchX, touchY)) {
            game.setScreen(new HomeScreen(game));
        }

        return true;
    }

    private void gotoPlayScreen(int level) {
        Pref.getData().setInteger(Pref.LEVEL, level);
        SoundManager.getSoundManager(game).stopMenuSound();
        game.setScreen(new PlayScreen(game));
    }

    /**
     * Called when a finger or the mouse was dragged.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.  @return whether the input was processed
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    /**
     * Called when the mouse was moved without any buttons being pressed. Will not be called on iOS.
     *
     * @param screenX
     * @param screenY
     * @return whether the input was processed
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    /**
     * Called when the mouse wheel was scrolled. Will not be called on iOS.
     *
     * @param amount the scroll amount, -1 or 1 depending on the direction the wheel was scrolled.
     * @return whether the input was processed.
     */
    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
