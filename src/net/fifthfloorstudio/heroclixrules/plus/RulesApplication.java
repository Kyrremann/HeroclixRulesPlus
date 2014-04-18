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

	private static final String JSON_POWERS = "powers.json";
	private static final String JSON_TEAM_ABILITIES = "team_abilities.json";
	private static final String JSON_GENERAL_RULES = "general.json";
	private static final String JSON_ABILITIES = "abilities.json";
	private static final String JSON_FEATS = "feats.json";
	private static final String JSON_MAPS = "maps.json";
	private static final String JSON_MAPS_ERRATA = "maps_errata.json";
	private static final String JSON_ABILITIES_ERRATA = "abilities_errata.json";
	private static final String JSON_RESOURCES_ERRATA = "resources_errata.json";
	private static final String JSON_POWERS_ERRATA = "powers_errata.json";
	private static final String JSON_OBJECTS = "objects.json";
	private static final String JSON_OBJECTS_ERRATA = "objects_errata.json";
	private static final String JSON_ATA_ERRATA = "ata_errata.json";
	private static final String JSON_GLOSSARY = "glossary.json";
	private static final String JSON_TEAM_ABILITIES_ERRATA = "team_abilities_errata.json";
	private static final String JSON_HORDE_TOKENS_ERRATA = "horde_tokens_errata.json";
	private static final String JSON_BFC = "bfc.json";
	private static final String JSON_ATA = "ata.json";
	private static final String JSON_CORE_RULES = "core_rules.json";
	private static final String JSON_RESOURCES = "resources.json";
	private static final String JSON_TACTICS = "tactics.json";

	public final static String JSON_NAME = "name";
	public final static String JSON_TEXT = "text";
	public final static String JSON_IMAGE = "image";

	public static final String JSON_COST = "cost";
	public static final String JSON_PREREQUISITE = "prerequisite";
	public static final String JSON_MODIFIER = "modifier";
	public static final String JSON_TYPE = "type";
	public static final String JSON_RELIC = "relic";
	public static final String JSON_OWNER = "owner";
	public static final String JSON_SET = "set";
	public static final String JSON_ID = "id";
	public static final String JSON_KEYWORDS = "keywords";
	public static final String JSON_IS_NESTED = "isNested";
	public static final String JSON_RULES = "rules";

	public final static String ENGLISH = "english";
	public final static String SPANISH = "spanish";
	public final static String ITALIAN = "italian";

	private JSONObject powerRules;
	private JSONObject ataRules;
	private JSONObject teamAbilitiesRules;
	private JSONObject generalRules;
	private JSONObject abilitiesRules;
	private JSONObject mapsRules;
	private JSONObject featsRules;
	private JSONObject objectsRules;
	private JSONObject powersErrataRules;
	private JSONObject resourcesErrataRules;
	private JSONObject abilitiesErrataRules;
	private JSONObject mapsErrataRules;
	private JSONObject objectsErrataRules;
	private JSONObject ataErrataRules;
	private JSONObject glossaryRules;
	private JSONObject coreRules;
	private JSONObject teamAbilitiesErrataRules;
	private JSONObject hordeTokensErrataRules;
	private JSONObject bfcRules;
	private JSONObject resourcesRules;
	private JSONObject tacticsRules;

	private HashMap<String, String[]> categoryTitles;
	private String language;

	public RulesApplication() {
		language = ENGLISH;
		categoryTitles = new HashMap<String, String[]>(4);
	}
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language.toLowerCase(Locale.getDefault());
		resetJsonObjects();
	}

	private void resetJsonObjects() {
		powerRules = null;
		teamAbilitiesRules = null;
		generalRules = null;
		abilitiesRules = null;
		mapsRules = null;
		featsRules = null;
		objectsRules = null;
		mapsErrataRules = null;
		objectsErrataRules = null;
		ataErrataRules = null;
		glossaryRules = null;
		bfcRules = null;
		coreRules = null;
		teamAbilitiesErrataRules = null;
		hordeTokensErrataRules = null;
		powersErrataRules = null;
		resourcesErrataRules = null;
		abilitiesErrataRules = null;
		tacticsRules = null;
		categoryTitles.clear();
	}

	// TODO Could probably change all this to one large map,
	// so I don't have to have all these objects
	// The method should just search in the asset folder for a file,
	// and then load it if it found it
	public JSONObject getJSONRules(String category) {
		if (isPowerRule(category)) {
			if (powerRules == null) {
				powerRules = JSONParser.getJsonRule(this, JSON_POWERS);
			}
			return powerRules;
		} else if (category.equals("additional team abilities")) {
			if (ataRules == null) {
				ataRules = JSONParser.getJsonRule(this, JSON_ATA);
			}
			return ataRules;
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
		} else if (category.equals("objects errata")) {
			if (objectsErrataRules == null) {
				objectsErrataRules = JSONParser.getJsonRule(this,
						JSON_OBJECTS_ERRATA);
			}
			return objectsErrataRules;
		} else if (category.equals("powers errata")) {
			if (powersErrataRules == null) {
				powersErrataRules = JSONParser.getJsonRule(this,
						JSON_POWERS_ERRATA);
			}
			return powersErrataRules;
		} else if (category.equals("resources errata")) {
			if (resourcesErrataRules == null) {
				resourcesErrataRules = JSONParser.getJsonRule(this,
						JSON_RESOURCES_ERRATA);
			}
			return resourcesErrataRules;
		} else if (category.equals("abilities errata")) {
			if (abilitiesErrataRules == null) {
				abilitiesErrataRules = JSONParser.getJsonRule(this,
						JSON_ABILITIES_ERRATA);
			}
			return abilitiesErrataRules;
		} else if (category.equals("maps errata")) {
			if (mapsErrataRules == null) {
				mapsErrataRules = JSONParser
						.getJsonRule(this, JSON_MAPS_ERRATA);
			}
			return mapsErrataRules;
		} else if (category.equals("ata errata")) {
			if (ataErrataRules == null) {
				ataErrataRules = JSONParser.getJsonRule(this, JSON_ATA_ERRATA);
			}
			return ataErrataRules;
		} else if (category.equals("glossary")) {
			if (glossaryRules == null) {
				glossaryRules = JSONParser.getJsonRule(this, JSON_GLOSSARY);
			}
			return glossaryRules;
		} else if (category.equals("team abilities errata")) {
			if (teamAbilitiesErrataRules == null) {
				teamAbilitiesErrataRules = JSONParser.getJsonRule(this,
						JSON_TEAM_ABILITIES_ERRATA);
			}
			return teamAbilitiesErrataRules;
		} else if (category.equals("horde tokens errata")) {
			if (hordeTokensErrataRules == null) {
				hordeTokensErrataRules = JSONParser.getJsonRule(this,
						JSON_HORDE_TOKENS_ERRATA);
			}
			return hordeTokensErrataRules;
		} else if (category.equals("battlefield conditions")) {
			if (bfcRules == null) {
				bfcRules = JSONParser.getJsonRule(this, JSON_BFC);
			}
			return bfcRules;
		} else if (category.equals("resources")) {
			if (resourcesRules == null) {
				resourcesRules = JSONParser.getJsonRule(this, JSON_RESOURCES);
			}
			return resourcesRules;
		} else if (category.equals("core rules")) {
			if (coreRules == null) {
				coreRules = JSONParser.getJsonRule(this, JSON_CORE_RULES);
			}
			return coreRules;
		} else if (category.equals("tactics")) {
			if (tacticsRules == null) {
				tacticsRules = JSONParser.getJsonRule(this, JSON_TACTICS);
			}
			return tacticsRules;
		}

		throw new UnsupportedOperationException(
				"Can't find rules with the name '" + category + "'");
	}

	public JSONObject getNestedJSONRules(String category, String nestedRule) {
		try {
			JSONObject rules = getJSONRules(category);
			return rules.getJSONObject(nestedRule.toUpperCase(Locale.ENGLISH));
		} catch (JSONException e) {
			e.printStackTrace();
			return new JSONObject();
			// TODO Maybe all this 'return new' should rather throw an exception?
		}
	}

	public String[] getPowerRulesTitles(String category) {
		if (categoryTitles.get(category) == null) {
			JSONObject json = getJSONRules(category);
			try {
				JSONArray array = json.getJSONArray(category);
				String[] tmpArray = new String[array.length()];
				for (int i = 0; i < array.length(); i++) {
					tmpArray[i] = getPowerRuleNameBasedOnLanguage(array
							.getJSONObject(i));
				}
				categoryTitles.put(category, tmpArray);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return categoryTitles.get(category);
	}

	public String getRuleText(int powerId, String title, String category) {
		try {
			JSONObject rule = getRuleJSON(powerId, title, category);
			if (isPowerRule(category)) {
				return getPowerRuleBasedOnLanguage(rule);
			} else {
				return getRuleBasedOnLanguage(rule);
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

	/*
	 * Does not support power rule
	 */
	public JSONObject getRuleJSONBasedOnLanguage(String title, String category)
			throws JSONException {
		JSONObject rule = getJSONRules(category).getJSONObject(title);
		if (rule.has(language)) {
			return rule.getJSONObject(language);
		} else {
			return rule.getJSONObject(ENGLISH);
		}
	}

	public JSONObject getRuleJSONBasedOnLanguage(JSONObject rule)
			throws JSONException {
		if (rule.has(language)) {
			return rule.getJSONObject(language);
		} else {
			return rule.getJSONObject(ENGLISH);
		}
	}

	private String[] getNormalRulesTitles(String category) {
		if (categoryTitles.get(category) == null) {
			JSONObject json = getJSONRules(category);
			JSONObjectToArray(json, category);
		}

		return categoryTitles.get(category);
	}

	@SuppressWarnings("unchecked")
	private void JSONObjectToArray(JSONObject json, String category) {
		Iterator<String> keys = json.keys();
		String[] tmpArray = new String[json.length()];
		for (int i = 0; i < json.length(); i++) {
			tmpArray[i] = keys.next();
		}
		Arrays.sort(tmpArray);
		categoryTitles.put(category, tmpArray);
	}

	public String[] getTitlesOfRules(String category) {
		if (isPowerRule(category)) {
			return getPowerRulesTitles(category);
		} else {
			return getNormalRulesTitles(category);
		}
	}

	public String[] getNestedRuleTitles(String category, String nestedRule) {
		try {
			JSONObjectToArray(
					getJSONRules(category).getJSONObject(
							nestedRule.toUpperCase()).getJSONObject(JSON_RULES),
					nestedRule.toUpperCase());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return categoryTitles.get(nestedRule);
	}

	private String getPowerRuleNameBasedOnLanguage(JSONObject rule)
			throws JSONException {
		if (rule.has(language)) {
			return rule.getJSONObject(language).getString(JSON_NAME);
		} else {
			return rule.getJSONObject(ENGLISH).getString(JSON_NAME);
		}
	}

	private String getPowerRuleBasedOnLanguage(JSONObject rule)
			throws JSONException {
		if (rule.has(language)) {
			return rule.getJSONObject(language).getString(JSON_TEXT);
		} else {
			return rule.getJSONObject(ENGLISH).getString(JSON_TEXT);
		}
	}

	public String getRuleBasedOnLanguage(JSONObject rule) throws JSONException {
		if (rule.has(language)) {
			return rule.getString(language);
		} else {
			return rule.getString(ENGLISH);
		}
	}

	public static boolean isPowerRule(String category) {
		return category.equals("speed") || category.equals("attack")
				|| category.equals("defense") || category.equals("damage");
	}

	private boolean isTeamAbility(String category) {
		return category.equals("team abilities")
				|| category.equals("team abilities errata");
	}

	public ImageSpan getImageSpanForRuleIfExists(int position, String title,
			String category) {
		try {
			int id = getImageIdForRuleIfExists(
					getRuleJSON(position, title, category), title, category);
			if (id == 0) {
				return null;
			}
			return new ImageSpan(getApplicationContext(), id);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	public int getImageIdForRuleIfExists(JSONObject rule, String title,
			String category) {
		try {
			if (rule.has(JSON_IMAGE)) {
				String imageName;
				imageName = rule.getString(JSON_IMAGE);
				if (imageName.length() == 0) {
					imageName = title.toLowerCase(Locale.getDefault())
							.replaceAll("(\\s|-)", "_");
				}

				String prefix = "";
				if (isPowerRule(category) || category.equals("powers errata")) {
					prefix = "pa_";
				} else if (isTeamAbility(category)) {
					prefix = "ta_";
				}

				return getResources().getIdentifier(prefix + imageName,
						"drawable", getPackageName());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public int getImageIdForPowerRule(String imageName) {
		return getResources().getIdentifier("pa_" + imageName, "drawable",
				getPackageName());
	}

	public boolean isRuleNested(String nestedRule, String category) {
		try {
		JSONObject rule = getNestedJSONRules(category, nestedRule);
			return rule.has(JSON_IS_NESTED) && rule.getBoolean(JSON_IS_NESTED);
		} catch (JSONException e) {
			return false;
		}
	}
}
