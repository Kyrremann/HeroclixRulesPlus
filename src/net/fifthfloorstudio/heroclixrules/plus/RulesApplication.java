package net.fifthfloorstudio.heroclixrules.plus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import net.fifthfloorstudio.heroclixrules.plus.utils.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.text.style.ImageSpan;

public class RulesApplication extends Application {

	public final static String NAME = "name";
	public final static String TEXT = "text";
	public final static String IMAGE = "image";

	public final static String ENGLISH = "english";
	public final static String SPANISH = "spanish";
	public final static String ITALIAN = "italian";

	private JSONObject powerRules;
	private JSONObject teamAbilitiesRules;;

	private HashMap<String, String[]> titles;
	private String language;

	public RulesApplication() {
		language = ENGLISH;
		titles = new HashMap<String, String[]>(4);
	}

	public JSONObject getJSONRules(String category) {
		if (isPowerRule(category)) {
			if (powerRules == null) {
				powerRules = JSONParser.getPowers(this);
			}
			return powerRules;
		} else if (category.equals("team abilities")) {
			if (teamAbilitiesRules == null) {
				teamAbilitiesRules = JSONParser.getTeamAbilities(this);
			}
			return teamAbilitiesRules;
		}

		return null;
	}

	public String[] getPowerRulesTitles(String category) {
		if (titles.get(category) == null) {
			JSONObject json = getJSONRules(category);
			try {
				JSONArray array = json.getJSONArray(category);
				String[] tmpArray = new String[array.length()];
				for (int i = 0; i < array.length(); i++) {
					tmpArray[i] = array.getJSONObject(i)
							.getJSONObject(language).getString(NAME);
				}
				titles.put(category, tmpArray);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return titles.get(category);
	}

	public String getRuleText(int powerId, String title, String category) {
		try {
			JSONObject rule = getRuleJSON(powerId, title, category);
			if (isPowerRule(category)) {
				return rule.getJSONObject(language).getString(TEXT);
			} else {
				return rule.getString(language);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "no rule";
	}

	public JSONObject getRuleJSON(int position, String title, String category)
			throws JSONException {
		if (isPowerRule(category)) {
			return getJSONRules(category).getJSONArray(category).getJSONObject(
					position);
		} else {
			return getJSONRules(category).getJSONObject(title);
		}
	}

	@SuppressWarnings("unchecked")
	private String[] getTitles(String category) {
		if (titles.get(category) == null) {
			JSONObject json = getJSONRules(category);
			Iterator<String> keys = json.keys();
			String[] tmpArray = new String[json.length()];
			for (int i = 0; i < json.length(); i++) {
				tmpArray[i] = keys.next();
			}
			Arrays.sort(tmpArray);
			titles.put(category, tmpArray);
		}

		return titles.get(category);
	}

	public String[] getRuleTitles(String category) {
		if (isPowerRule(category)) {
			return getPowerRulesTitles(category);
		} else {
			return getTitles(category);
		}
	}

	private boolean isPowerRule(String category) {
		return category.equals("speed") || category.equals("attack")
				|| category.equals("defense") || category.equals("damage");
	}

	public ImageSpan getImageSpanForRuleIfExists(int position, String title, String category) {
		try {
			int id = getImageIdForRuleIfExists(
					getRuleJSON(position, title, category), title);
			if (id == 0) {
				return null;
			}
			return new ImageSpan(getApplicationContext(), id);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	public int getImageIdForRuleIfExists(JSONObject rule, String title) {
		try {
			if (rule.has(IMAGE)) {
				String imageName;
				imageName = rule.getString(IMAGE);
				if (imageName.length() == 0) {
					imageName = title.toLowerCase(Locale.getDefault())
							.replaceAll("(\\s|-)", "_");
				}

				int id = getResources().getIdentifier("ta_" + imageName, "drawable",
						getPackageName());
				if (id == 0) {
					id = getResources().getIdentifier("pa_" + imageName, "drawable",
							getPackageName());
				}
				
				return id;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return 0;
	}
}
