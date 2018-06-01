package id.noidea.firstblood;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ziterz on 11/22/2017.
 */

public class PrefManager {
    private SharedPreferences pref;

    // Shared preferences file name
    private static final String PREF_NAME = "id.noidea.firstblood.launcher";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PrefManager(Context context) {
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.apply();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
