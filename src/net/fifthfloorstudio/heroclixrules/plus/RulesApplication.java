package net.fifthfloorstudio.heroclixrules.plus;

import java.util.Locale;

import net.fifthfloorstudio.heroclixrules.plus.utils.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;

public class RulesApplication extends Application {

	public final static String NAME = "name";
	public final static String TEXT = "text";
	public final static String IMAGE = "image";

	public final static String ENGLISH = "english";
	public final static String SPANISH = "spanish";
	public final static String ITALIAN = "italian";

	private JSONObject powerRules;
	private JSONObject teamAbilities;

	private String[] powerRulesTitles;
	private String language;

	public RulesApplication() {
		language = ENGLISH;
	}

	public JSONObject getPowerRules() {
		if (powerRules == null) {
			powerRules = JSONParser.getPowers(this);
		}
		return powerRules;
	}

	public String[] getPowerRulesTitles(String category) {
		if (powerRulesTitles == null) { // må ta høyde for kategorier :\
										// alltid hente?
			JSONObject json = getPowerRules();
			try {
				JSONArray array = json.getJSONArray(category);
				powerRulesTitles = new String[array.length()];
				for (int i = 0; i < array.length(); i++) {
					powerRulesTitles[i] = array
							.getJSONObject(i)
							.getJSONObject(language)
							.getString(NAME);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return powerRulesTitles;
	}

	public String getPowerRule(String power, String category) {
		try {
			return getPowerRules().getJSONObject(category)
					.getJSONObject(power.toUpperCase(Locale.ENGLISH))
					.getJSONObject(language).getString(TEXT);
		} catch (JSONException e) {
			e.printStackTrace();
			return "no rule";
		}
	}
}
