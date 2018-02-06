package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.gegejesatu.potatone.GameMain;

import GameHelper.Asset;
import GameHelper.GameInfo;
import GameHelper.Pref;
import GameHelper.SoundManager;
import Sprites.LifeLabel;
import TweenAccessors.Value;
import TweenAccessors.ValueAccessor;
import World.PotatoneWorld;
import World.ToneTrack;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by LENOVO on 26/01/2018.
 */

public class PlayScreen implements Screen, GestureDetector.GestureListener, InputProcessor {

    GameMain game;
    ToneTrack toneTrack;
    PotatoneWorld potatoneWorld;
    public static int score;
    public static int life;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private float runtime;
    public static LifeLabel lifeLabel;
    private TweenManager manager;
    private TweenManager tutorialTween;
    private Value alpha;
    private Color transitionColor;
    public static GameState state;
    private boolean isTurialSetup;
    private Sprite tutorialLayout;

    public enum GameState{
        TUTORIAL, PLAY, PAUSE, GAMEOVER, VICTORY
    }

    public PlayScreen(GameMain game){
        this.game = game;
        toneTrack = new ToneTrack(game, this);
        potatoneWorld = new PotatoneWorld(game);
        lifeLabel = new LifeLabel(game);
        tutorialLayout = new Sprite(game.asset.getTexture(Asset.TUTORIAL));
        tutorialLayout.setSize(GameInfo.WIDTH*0.85f, GameInfo.HEIGHT*0.7f);
        tutorialLayout.setPosition((GameInfo.WIDTH/2) - (tutorialLayout.getWidth()/2),
                (GameInfo.HEIGHT/2) - (tutorialLayout.getHeight()/2));
        manager = new TweenManager();
        tutorialTween = new TweenManager();
        alpha = new Value();
        transitionColor = new Color();
        prepareTransition(255, 255, 255, .5f);
    }

    @Override
    public void show() {
        initCamera();
        setInput();
        score = 0;
        life = 3;
        if(Pref.getData().getBoolean(Pref.IS_TUTORIAL_DONE)){
            state = GameState.PLAY;
        }else {
            state = GameState.TUTORIAL;
        }
    }

    private void setInput() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new GestureDetector(this));
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void initCamera() {
        shapeRenderer = new ShapeRenderer();
        cam = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
        cam.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);
        game.batch.setProjectionMatrix(cam.combined);
        shapeRenderer.setProjectionMatrix(cam.combined);
    }

    @Override
    public void render(float delta) {
        runtime += delta;
        if(state == GameState.TUTORIAL){
            renderWorld(delta);
            setupTutorial(delta);
        }else if(state == GameState.PLAY){
            updateWorld(delta);
            renderWorld(delta);
        }else if(state == GameState.GAMEOVER || state == GameState.VICTORY){
            renderWorld(delta);
        }else {
            updateWorld(delta);
            renderWorld(delta);
        }
    }

    private void setupTutorial(float delta) {
        if(isTurialSetup){
            tutorialTween.update(delta);
        }else {
            isTurialSetup = true;
            TweenCallback cb = new TweenCallback() {
                @Override
                public void onEvent(int type, BaseTween<?> source) {
                    Pref.getData().setBoolean(Pref.IS_TUTORIAL_DONE, true);
                    state = GameState.PLAY;
                }
            };
            Tween.call(cb).delay(6f).start(tutorialTween);
        }
    }

    private void renderWorld(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw batch
        game.batch.begin();
        toneTrack.draw(delta, runtime);
        potatoneWorld.draw(delta, runtime);
        toneTrack.drawExplosion(delta, runtime);
        toneTrack.drawDoubleScore(delta, runtime);
        lifeLabel.draw(delta, runtime);
        drawTutorial(delta);
        game.batch.end();
        drawTransition(delta);
    }

    private void drawTutorial(float delta) {
        if(state == GameState.TUTORIAL){
            tutorialLayout.draw(game.batch);
        }
    }

    private void updateWorld(float delta) {
        toneTrack.update(delta, runtime);
        potatoneWorld.update(delta, runtime);
    }

    public void prepareTransition(int r, int g, int b, float duration) {
        transitionColor.set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
        alpha.setValue(1);
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        Tween.to(alpha, -1, duration).target(0)
                .ease(TweenEquations.easeOutQuad).start(manager);
    }

    private void drawTransition(float delta) {
        if (alpha.getValue() > 0) {
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(transitionColor.r, transitionColor.g,
                    transitionColor.b, alpha.getValue());
            shapeRenderer.rect(0, 0, GameInfo.WIDTH, GameInfo.HEIGHT);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
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
     * INPUT
     */
    /**
     * @param x
     * @param y
     * @param pointer
     * @param button
     */
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    /**
     * Called when a tap occured. A tap happens if a touch went down on the screen and was lifted again without moving outside
     * of the tap square. The tap square is a rectangular area around the initial touch position as specified on construction
     * time of the {@link GestureDetector}.
     *
     * @param x
     * @param y
     * @param count  the number of taps.
     * @param button
     */
    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    /**
     * Called when the user dragged a finger over the screen and lifted it. Reports the last known velocity of the finger in
     * pixels per second.
     *
     * @param velocityX velocity on x in seconds
     * @param velocityY velocity on y in seconds
     * @param button
     */
    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        // swipe up
        if(state == GameState.PLAY){
            if(velocityY < 0 ){
                toneTrack.player.moveUp();
            }else { // swipe down
                toneTrack.player.moveDown();
            }
        }
        return false;
    }

    /**
     * Called when the user drags a finger over the screen.
     *
     * @param x
     * @param y
     * @param deltaX the difference in pixels to the last drag event on x.
     * @param deltaY the difference in pixels to the last drag event on y.
     */
    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    /**
     * Called when no longer panning.
     *
     * @param x
     * @param y
     * @param pointer
     * @param button
     */
    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    /**
     * Called when the user performs a pinch zoom gesture. The original distance is the distance in pixels when the gesture
     * started.
     *
     * @param initialDistance distance between fingers when the gesture started.
     * @param distance        current distance between fingers.
     */
    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    /**
     * Called when a user performs a pinch zoom gesture. Reports the initial positions of the two involved fingers and their
     * current positions.
     *
     * @param initialPointer1
     * @param initialPointer2
     * @param pointer1
     * @param pointer2
     */
    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    /**
     * Called when no longer pinching.
     */
    @Override
    public void pinchStop() {

    }

    /**
     * Called when a key was pressed
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyDown(int keycode) {
        if(state == GameState.PLAY){
            switch (keycode){
                case Input.Keys.UP:
                    toneTrack.player.moveUp();
                    break;
                case Input.Keys.DOWN:
                    toneTrack.player.moveDown();
                    break;
                default:
                    break;
            }
        }
        return false;
    }

    /**
     * Called when a key was released
     *
     * @param keycode one of the constants in {@link Input.Keys}
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
        return false;
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
