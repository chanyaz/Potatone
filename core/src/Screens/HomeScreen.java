package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
 * Created by LENOVO on 26/01/2018.
 */

public class HomeScreen implements Screen, InputProcessor{

    private GameMain game;

    private ShapeRenderer shapeRenderer;
    private OrthographicCamera cam;
    private float runtime;

    private Sprite homePotatone;
    private Sprite homeHouse;
    private SimpleButton buttonPlay;
    private SimpleButton buttonCredit;

    public HomeScreen(GameMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        homePotatone = new Sprite(game.asset.getTexture(Asset.POTATONE_HOME));
        buttonPlay = new SimpleButton(game, GameInfo.WIDTH - 200, 200,
                game.asset.getTexture(Asset.PLAY_BUTTON), game.asset.getTexture(Asset.POTATO_BUTTON_DOWN), 0.4f);
        buttonCredit = new SimpleButton(game, GameInfo.WIDTH - 300, 100,
                game.asset.getTexture(Asset.CREDIT_BUTTON), game.asset.getTexture(Asset.POTATO_BUTTON_DOWN), 0.4f);
        initCamera();
        setInput();
        SoundManager.getSoundManager(game).playMenuSound();
    }

    private void setInput() {
        Gdx.input.setInputProcessor(this);
    }

    private void initCamera() {
        shapeRenderer = new ShapeRenderer();
        cam = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
        cam.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);
        game.batch.setProjectionMatrix(cam.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
    }

    @Override
    public void render(float delta) {
        runtime += delta;

        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw batch
        game.batch.begin();

        game.batch.draw(homePotatone, homePotatone.getX(), homePotatone.getY(),
                GameInfo.WIDTH, GameInfo.HEIGHT);
        buttonPlay.draw(game.batch);
        buttonCredit.draw(game.batch);

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

        buttonPlay.isTouchDown(touchX, touchY);
        buttonCredit.isTouchDown(touchX, touchY);
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
        if (buttonPlay.isTouchUp(touchX, touchY)) {
            if(Pref.getData().getBoolean(Pref.IS_STORY_READ)){
                Pref.getData().setInteger(Pref.LEVEL, GameInfo.EASY);
                game.setScreen(new MenuScreen(game));
            }else {
                // should go to menu screen
                Pref.getData().setInteger(Pref.LEVEL, GameInfo.EASY);
                SoundManager.getSoundManager(game).stopMenuSound();
                game.setScreen(new StoryScreen(game));
            }
        }else if(buttonCredit.isTouchUp(touchX, touchY)){
            game.setScreen(new CreditScreen(game));
        }
        return false;
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
