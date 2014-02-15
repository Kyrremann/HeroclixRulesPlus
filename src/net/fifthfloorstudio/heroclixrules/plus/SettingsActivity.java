package net.fifthfloorstudio.heroclixrules.plus;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.WindowManager.LayoutParams;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends PreferenceActivity implements
		OnPreferenceChangeListener {

	public static final String PREFS_SCREEN = "screen";
	public static final String PREFS_IMAGES = "images";
	public static final String PREFS_LANGUAGE = "language_list";

	@SuppressWarnings("deprecation")
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.pref_general);

		bindPreferenceSummaryToValue(findPreference(PREFS_SCREEN));
		bindPreferenceSummaryToValue(findPreference(PREFS_IMAGES));
		bindPreferenceSummaryToValue(findPreference(PREFS_LANGUAGE));

	}

	/**
	 * Binds a preference's summary to its value. More specifically, when the
	 * preference's value is changed, its summary (line of text below the
	 * preference title) is updated to reflect the value. The summary is also
	 * immediately updated upon calling this method. The exact display format is
	 * dependent on the type of preference.
	 * 
	 * @see #sBindPreferenceSummaryToValueListener
	 */
	private void bindPreferenceSummaryToValue(Preference preference) {
		preference.setOnPreferenceChangeListener(this);

		if (preference instanceof CheckBoxPreference) {
			onPreferenceChange(preference, PreferenceManager
					.getDefaultSharedPreferences(preference.getContext())
					.getBoolean(preference.getKey(), false));
		} else {
			onPreferenceChange(preference, PreferenceManager
					.getDefaultSharedPreferences(preference.getContext())
					.getString(preference.getKey(), ""));
		}
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object value) {
		String stringValue = value.toString();
		if (preference instanceof ListPreference) {
			ListPreference listPreference = (ListPreference) preference;
			int index = listPreference.findIndexOfValue(stringValue);

			preference
					.setSummary(index >= 0 ? listPreference.getEntries()[index]
							: null);

		} else if (preference.getKey().equals(PREFS_SCREEN)) {
			if (Boolean.valueOf(stringValue)) {
				getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
			}
		}

		return true;
	}

}
