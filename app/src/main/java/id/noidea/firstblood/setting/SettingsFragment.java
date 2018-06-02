package id.noidea.firstblood.setting;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import id.noidea.firstblood.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.app_preferences);

    }
}
