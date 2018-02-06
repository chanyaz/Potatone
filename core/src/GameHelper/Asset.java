package GameHelper;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by LENOVO on 26/01/2018.
 */

public class Asset {

    // Texture button
    public static final String BACK_BUTTON = "button/back.png";
    public static final String EASY_BUTTON = "button/easy.png";
    public static final String HARD_BUTTON = "button/hard.png";
    public static final String MEDIUM_BUTTON = "button/normal.png";
    public static final String PLAY_BUTTON = "button/play_button.PNG";
    public static final String CREDIT_BUTTON = "button/credit_button.PNG";

    // Texture new
    public static final String CREDITS = "new/Credits.PNG";
    public static final String DOUBLE_SCORE = "new/double_score.png";
    public static final String MINUS = "new/Minus.PNG";
    public static final String POTATO_FRAME_BG = "new/Potatone_frame_BG.jpg";
    public static final String GAME_OVER = "new/Screen_Game_over.JPG";
    public static final String VICTORY = "new/Screen_Win.jpg";
    public static final String SOUNDWARNING = "new/Soundwarning.jpg";
    public static final String SPLASH_LOGO = "new/Splash_logo.jpg";
    public static final String STORY1 = "new/story_1.jpg";
    public static final String STORY2 = "new/story_2.jpg";
    public static final String STORY3 = "new/story_3.jpg";
    public static final String TEAM = "new/team.png";
    public static final String TUTORIAL = "new/Tutorial.JPG";

    // Textures objects
    public static final String BAD_TONE1 = "objects/badtone1_sprites.png";
    public static final String BAD_TONE2 = "objects/badtone2_sprites.png";
    public static final String BAD_TONE3 = "objects/badtone3_sprites.png";
    public static final String BAD_TONE4 = "objects/badtone4_sprites.png";
    public static final String BOMB = "objects/bom.png";
    public static final String EXPLOSION = "objects/explosion.png";
    public static final String GOOD_TONE1 = "objects/goodtone1.png";
    public static final String GOOD_TONE2 = "objects/goodtone1.png";
    public static final String GOOD_TONE3 = "objects/goodtone1.png";
    public static final String HOUSE = "objects/house.png";
    public static final String INSECT = "objects/insect_sprite.png";
    public static final String LOVE = "objects/love.png";
    public static final String METRONOME = "objects/metronome.png";
    public static final String POTATO_BUTTON_DOWN = "objects/potato_button_down.png";
    public static final String POTATO_BUTTON_UP = "objects/potato_button_up.png";
    public static final String POTATONE_SPRITES = "objects/potato_sprites.png";
    public static final String STAR = "objects/star_sprite.png";

    // Textures potatoes
    public static final String POTATONE_ONLY = "potatoes/potato_only.PNG";
    public static final String POTATONE_FRAME1 = "potatoes/Potatone_frame_1.PNG";
    public static final String POTATONE_HOME = "potatoes/potatone_splash.jpg";
    public static final String POTATONE_ALPHA = "potatoes/potatone_splash_alpha.png";
    public static final String POTATONE_ENV1 = "potatoes/PTTN_BG_1.jpg";
    public static final String POTATONE_ENV2 = "potatoes/PTTN_BG_2.jpg";
    public static final String POTATONE_ENV3 = "potatoes/PTTN_BG_3.jpg";
    public static final String POTATONE_ENV4 = "potatoes/PTTN_BG_4.jpg";
    public static final String POTATONE_ENV5 = "potatoes/PTTN_BG_5.jpg";

    // Sounds
    public static final String LAYER1 = "ogg/1m01_layer_1.ogg";
    public static final String LAYER2 = "ogg/1m01_layer_2.ogg";
    public static final String LAYER3 = "ogg/1m01_layer_3.ogg";
    public static final String LAYER4 = "ogg/1m01_layer_4.ogg";
    public static final String LAYER5 = "ogg/1m01_layer_5.ogg";
    public static final String SOUND_SPLASH = "sounds/logo_theme_3.ogg";

    // screensounds
    public static final String MAIN_MENU_SOUND = "ogg/1m02_mainmenu.ogg";
    public static final String STORY_INTRO_SOUND = "ogg/1m03_storyintro.ogg";
    public static final String VICTORY_SOUND = "ogg/1m04_victory.ogg";
    public static final String GAME_OVER_SOUND = "ogg/1m05_gameover.ogg";

    // sfx
    public static final String SFX_BADNOTE = "ogg/sfx_badnote.ogg";
    public static final String SFX_BOMB = "ogg/sfx_bomb.ogg";
    public static final String SFX_BUTTON = "ogg/sfx_button.ogg";
    public static final String SFX_BUTTON_ALT = "sfx/sfx_buttonalt.ogg";
    public static final String SFX_DEATH = "sfx/sfx_death.ogg";
    public static final String SFX_GOODNOTE = "ogg/sfx_goodnote.ogg";
    public static final String SFX_INSECT = "ogg/sfx_insect.ogg";
    public static final String SFX_STAR = "ogg/sfx_star.ogg";

    public AssetManager manager;

    public void loadAssets(){
        manager = new AssetManager();
        loadSplash();
        loadHomeScreenAssets();
        loadPlayScreenAssets();
        loadNewAssets();
        loadButton();
        loadSounds();
    }

    private void loadButton(){
        manager.load(BACK_BUTTON, Texture.class);
        manager.load(EASY_BUTTON, Texture.class);
        manager.load(HARD_BUTTON, Texture.class);
        manager.load(MEDIUM_BUTTON, Texture.class);
        manager.load(PLAY_BUTTON, Texture.class);
        manager.load(CREDIT_BUTTON, Texture.class);
    }

    private void loadNewAssets() {
        manager.load(DOUBLE_SCORE, Texture.class);
        manager.load(CREDITS, Texture.class);
        manager.load(MINUS, Texture.class);
        manager.load(POTATO_FRAME_BG, Texture.class);
        manager.load(GAME_OVER, Texture.class);
        manager.load(VICTORY, Texture.class);
        manager.load(SOUNDWARNING, Texture.class);
        manager.load(SPLASH_LOGO, Texture.class);
        manager.load(STORY1, Texture.class);
        manager.load(STORY2, Texture.class);
        manager.load(STORY3, Texture.class);
        manager.load(TEAM, Texture.class);
        manager.load(TUTORIAL, Texture.class);
    }

    private void loadSplash() {
        manager.load(POTATONE_ALPHA, Texture.class);
    }

    private void loadHomeScreenAssets(){
        manager.load(POTATONE_ONLY, Texture.class);
        manager.load(POTATONE_HOME, Texture.class);
        manager.load(HOUSE, Texture.class);
        manager.load(POTATO_BUTTON_DOWN, Texture.class);
        manager.load(POTATO_BUTTON_UP, Texture.class);
    }

    private void loadPlayScreenAssets() {

        manager.load(POTATONE_FRAME1, Texture.class);
        manager.load(POTATONE_ENV1, Texture.class);
        manager.load(POTATONE_ENV2, Texture.class);
        manager.load(POTATONE_ENV3, Texture.class);
        manager.load(POTATONE_ENV4, Texture.class);
        manager.load(POTATONE_ENV5, Texture.class);

        manager.load(POTATONE_SPRITES, Texture.class);
        manager.load(LOVE, Texture.class);
        manager.load(GOOD_TONE1, Texture.class);
        manager.load(GOOD_TONE1, Texture.class);
        manager.load(GOOD_TONE1, Texture.class);
        manager.load(BOMB, Texture.class);
        manager.load(EXPLOSION, Texture.class);
        manager.load(METRONOME, Texture.class);
        manager.load(BAD_TONE1, Texture.class);
        manager.load(BAD_TONE2, Texture.class);
        manager.load(BAD_TONE3, Texture.class);
        manager.load(BAD_TONE4, Texture.class);
        manager.load(INSECT, Texture.class);
        manager.load(STAR, Texture.class);
    }

    private void loadSounds() {
        manager.load(LAYER1, Music.class);
        manager.load(LAYER2, Music.class);
        manager.load(LAYER3, Music.class);
        manager.load(LAYER4, Music.class);
        manager.load(LAYER5, Music.class);
        manager.load(SOUND_SPLASH, Music.class);

        manager.load(MAIN_MENU_SOUND, Music.class);
        manager.load(STORY_INTRO_SOUND, Music.class);
        manager.load(VICTORY_SOUND, Music.class);
        manager.load(GAME_OVER_SOUND, Music.class);

        manager.load(SFX_BADNOTE, Music.class);
        manager.load(SFX_BOMB, Music.class);
        manager.load(SFX_BUTTON, Music.class);
        manager.load(SFX_BUTTON_ALT, Music.class);
        manager.load(SFX_DEATH, Music.class);
        manager.load(SFX_GOODNOTE, Music.class);
        manager.load(SFX_INSECT, Music.class);
        manager.load(SFX_STAR, Music.class);
    }

    public boolean isAllLoaded(){
        return manager.isLoaded(LOVE)
                && manager.isLoaded(POTATONE_SPRITES)
                && manager.isLoaded(GOOD_TONE1)
                && manager.isLoaded(GOOD_TONE2)
                && manager.isLoaded(GOOD_TONE3)
                && manager.isLoaded(BOMB)
                && manager.isLoaded(EXPLOSION)
                && manager.isLoaded(METRONOME)
                && manager.isLoaded(BAD_TONE1)
                && manager.isLoaded(BAD_TONE2)
                && manager.isLoaded(BAD_TONE3)
                && manager.isLoaded(BAD_TONE4)
                && manager.isLoaded(INSECT)
                && manager.isLoaded(STAR)
                && manager.isLoaded(POTATONE_ALPHA)
                && manager.isLoaded(POTATONE_ONLY)
                && manager.isLoaded(POTATONE_HOME)
                && manager.isLoaded(POTATONE_FRAME1)
                && manager.isLoaded(POTATONE_ENV1)
                && manager.isLoaded(POTATONE_ENV2)
                && manager.isLoaded(POTATONE_ENV3)
                && manager.isLoaded(POTATONE_ENV4)
                && manager.isLoaded(POTATONE_ENV5)
                && manager.isLoaded(HOUSE)
                && manager.isLoaded(POTATO_BUTTON_UP)
                && manager.isLoaded(POTATO_BUTTON_DOWN)
                && manager.isLoaded(DOUBLE_SCORE)
                && manager.isLoaded(POTATO_FRAME_BG)
                && manager.isLoaded(GAME_OVER)
                && manager.isLoaded(VICTORY)
                && manager.isLoaded(SOUNDWARNING)
                && manager.isLoaded(SPLASH_LOGO)
                && manager.isLoaded(STORY1)
                && manager.isLoaded(STORY2)
                && manager.isLoaded(STORY3)
                && manager.isLoaded(TEAM)
                && manager.isLoaded(TUTORIAL)
                && manager.isLoaded(LAYER1)
                && manager.isLoaded(LAYER2)
                && manager.isLoaded(LAYER3)
                && manager.isLoaded(LAYER4)
                && manager.isLoaded(LAYER5)
                && manager.isLoaded(MINUS)
                && manager.isLoaded(CREDITS)
                && manager.isLoaded(EASY_BUTTON)
                && manager.isLoaded(MEDIUM_BUTTON)
                && manager.isLoaded(HARD_BUTTON)
                && manager.isLoaded(BACK_BUTTON)
                && manager.isLoaded(CREDIT_BUTTON)
                && manager.isLoaded(PLAY_BUTTON)
                && manager.isLoaded(MAIN_MENU_SOUND)
                && manager.isLoaded(STORY_INTRO_SOUND)
                && manager.isLoaded(VICTORY_SOUND)
                && manager.isLoaded(GAME_OVER_SOUND)
                && manager.isLoaded(SFX_BADNOTE)
                && manager.isLoaded(SFX_BOMB)
                && manager.isLoaded(SFX_BUTTON)
                && manager.isLoaded(SFX_BUTTON_ALT)
                && manager.isLoaded(SFX_DEATH)
                && manager.isLoaded(SFX_GOODNOTE)
                && manager.isLoaded(SFX_INSECT)
                && manager.isLoaded(SFX_STAR)
                && manager.isLoaded(SOUND_SPLASH);
    }

    public Texture getTexture(String fileName){
        return manager.get(fileName, Texture.class);
    }

    public Music getSound(String fileName){
        return manager.get(fileName, Music.class);
    }

    public void dispose() {
        manager.dispose();
    }
}
