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

	public static final String JSON_POWERS = "powers.json";
	public static final String JSON_TEAM_ABILITIES = "team_abilities.json";
	public static final String JSON_GENERAL_RULES = "general.json";
	public static final String JSON_ABILITIES = "abilities.json";
	private static final String JSON_FEATS = "feats.json";
	private static final String JSON_MAPS = "maps.json";
	private static final String JSON_MAPS_ERRETA = "maps_erreta.json";
	private static final String JSON_ABILITIES_ERRETA = "abilities_erreta.json";
	private static final String JSON_RESOURCES_ERRETA = "resources_erreta.json";
	private static final String JSON_POWERS_ERRETA = "powers_erreta.json";
	private static final String JSON_OBJECTS = "objects.json";
	private static final String JSON_OBJECTS_ERRETA = "objects_erreta.json";
	private static final String JSON_ATA_ERRETA = "ata_erreta.json";
	private static final String JSON_GLOSSARY = "glossary.json";
	private static final String JSON_TEAM_ABILITIES_ERRETA = "team_abilities_erreta.json";

	public final static String JSON_NAME = "name";
	public final static String JSON_TEXT = "text";
	public final static String JSON_IMAGE = "image";

	public static final String JSON_COST = "cost";
	public static final String JSON_PREREQUISITE = "prerequisite";
	public static final String JSON_MODIFIER = "modifier";
	public static final String JSON_TYPE = "type";
	public static final String JSON_RELIC = "relic";
	public static final String JSON_OWNER = "owner";

	public final static String ENGLISH = "english";
	public final static String SPANISH = "spanish";
	public final static String ITALIAN = "italian";

	private JSONObject powerRules, teamAbilitiesRules, generalRules,
			abilitiesRules;

	private HashMap<String, String[]> titles;
	private String language;
	private JSONObject mapsRules;
	private JSONObject featsRules;
	private JSONObject objectsRules;
	private JSONObject powersErretaRules;
	private JSONObject resourcesErretaRules;
	private JSONObject abilitiesErretaRules;
	private JSONObject mapsErretaRules;
	private JSONObject objectsErretaRules;
	private JSONObject ataErretaRules;
	private JSONObject glossaryRules;
	private JSONObject teamAbilitiesErretaRules;

	public RulesApplication() {
		language = ENGLISH;
		titles = new HashMap<String, String[]>(4);
	}

	public JSONObject getJSONRules(String category) {
		if (isPowerRule(category)) {
			if (powerRules == null) {
				powerRules = JSONParser.getJsonRule(this, JSON_POWERS);
			}
			return powerRules;
		} else if (category.equals("team abilities")) {
			if (teamAbilitiesRules == null) {
				teamAbilitiesRules = JSONParser.getJsonRule(this,
						JSON_TEAM_ABILITIES);
			}
			return teamAbilitiesRules;
		} else if (category.equals("general")) {
			if (generalRules == null) {
				generalRules = JSONParser.getJsonRule(this, JSON_GENERAL_RULES);
			}
			return generalRules;
		} else if (category.equals("abilities")) {
			if (abilitiesRules == null) {
				abilitiesRules = JSONParser.getJsonRule(this, JSON_ABILITIES);
			}
			return abilitiesRules;
		} else if (category.equals("maps")) {
			if (mapsRules == null) {
				mapsRules = JSONParser.getJsonRule(this, JSON_MAPS);
			}
			return mapsRules;
		} else if (category.equals("feats")) {
			if (featsRules == null) {
				featsRules = JSONParser.getJsonRule(this, JSON_FEATS);
			}
			return featsRules;
		} else if (category.equals("objects")) {
			if (objectsRules == null) {
				objectsRules = JSONParser.getJsonRule(this, JSON_OBJECTS);
			}
			return objectsRules;
		} else if (category.equals("objects erreta")) {
			if (objectsErretaRules == null) {
				objectsErretaRules = JSONParser.getJsonRule(this,
						JSON_OBJECTS_ERRETA);
			}
			return objectsErretaRules;
		} else if (category.equals("powers erreta")) {
			if (powersErretaRules == null) {
				powersErretaRules = JSONParser.getJsonRule(this,
						JSON_POWERS_ERRETA);
			}
			return powersErretaRules;
		} else if (category.equals("resources erreta")) {
			if (resourcesErretaRules == null) {
				resourcesErretaRules = JSONParser.getJsonRule(this,
						JSON_RESOURCES_ERRETA);
			}
			return resourcesErretaRules;
		} else if (category.equals("abilities erreta")) {
			if (abilitiesErretaRules == null) {
				abilitiesErretaRules = JSONParser.getJsonRule(this,
						JSON_ABILITIES_ERRETA);
			}
			return abilitiesErretaRules;
		} else if (category.equals("maps erreta")) {
			if (mapsErretaRules == null) {
				mapsErretaRules = JSONParser
						.getJsonRule(this, JSON_MAPS_ERRETA);
			}
			return mapsErretaRules;
		} else if (category.equals("ata erreta")) {
			if (ataErretaRules == null) {
				ataErretaRules = JSONParser
						.getJsonRule(this, JSON_ATA_ERRETA);
			}
			return ataErretaRules;
		} else if (category.equals("glossary")) {
			if (glossaryRules == null) {
				glossaryRules = JSONParser
						.getJsonRule(this, JSON_GLOSSARY);
			}
			return glossaryRules;
		} else if (category.equals("team abilities erreta")) {
			if (teamAbilitiesErretaRules == null) {
				teamAbilitiesErretaRules = JSONParser
						.getJsonRule(this, JSON_TEAM_ABILITIES_ERRETA);
			}
			return teamAbilitiesErretaRules;
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
							.getJSONObject(language).getString(JSON_NAME);
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
				return rule.getJSONObject(language).getString(JSON_TEXT);
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

	public ImageSpan getImageSpanForRuleIfExists(int position, String title,
			String category) {
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
			if (rule.has(JSON_IMAGE)) {
				String imageName;
				imageName = rule.getString(JSON_IMAGE);
				if (imageName.length() == 0) {
					imageName = title.toLowerCase(Locale.getDefault())
							.replaceAll("(\\s|-)", "_");
				}

				int id = getResources().getIdentifier("ta_" + imageName,
						"drawable", getPackageName());
				if (id == 0) {
					id = getResources().getIdentifier("pa_" + imageName,
							"drawable", getPackageName());
				}

				return id;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return 0;
	}
}
