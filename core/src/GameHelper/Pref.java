package GameHelper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by LENOVO on 27/01/2018.
 */

public class Pref {

    // constant string
    public static final String PREFERENCE_NAME = "potatone";
    public static final String IS_STORY_READ = "isStoryRead";
    public static final String LEVEL = "level";
    public static final String IS_TUTORIAL_DONE = "isTutorialDone";

    private Preferences prefs = Gdx.app.getPreferences(PREFERENCE_NAME);
    private static Pref preferenceData;

    public static Pref getData(){
        return preferenceData == null ? new Pref() : preferenceData;
    }

    public void setString(String key, String value){
        prefs.putString(key, value);
        prefs.flush();
    }

    public String getString(String key){
        return prefs.getString(key, "");
    }

    public void setBoolean(String key, boolean value){
        prefs.putBoolean(key, value);
        prefs.flush();
    }

    public boolean getBoolean(String key){
        return prefs.getBoolean(key, false);
    }

    public void setInteger(String key, int value){
        prefs.putInteger(key, value);
        prefs.flush();
    }

    public int getInteger(String key){
        return prefs.getInteger(key, 0);
    }
}
