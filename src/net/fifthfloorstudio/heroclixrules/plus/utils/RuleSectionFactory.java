package net.fifthfloorstudio.heroclixrules.plus.utils;

import static net.fifthfloorstudio.heroclixrules.plus.RulesApplication.ENGLISH;
import static net.fifthfloorstudio.heroclixrules.plus.RulesApplication.JSON_COST;
import static net.fifthfloorstudio.heroclixrules.plus.RulesApplication.JSON_ID;
import static net.fifthfloorstudio.heroclixrules.plus.RulesApplication.JSON_KEYWORDS;
import static net.fifthfloorstudio.heroclixrules.plus.RulesApplication.JSON_MODIFIER;
import static net.fifthfloorstudio.heroclixrules.plus.RulesApplication.JSON_OWNER;
import static net.fifthfloorstudio.heroclixrules.plus.RulesApplication.JSON_PREREQUISITE;
import static net.fifthfloorstudio.heroclixrules.plus.RulesApplication.JSON_RELIC;
import static net.fifthfloorstudio.heroclixrules.plus.RulesApplication.JSON_SET;
import static net.fifthfloorstudio.heroclixrules.plus.RulesApplication.JSON_TEXT;
import static net.fifthfloorstudio.heroclixrules.plus.RulesApplication.JSON_TYPE;
import static net.fifthfloorstudio.heroclixrules.plus.utils.StringUtils.parseText;
import heroclix.Rules.R;

import net.fifthfloorstudio.heroclixrules.plus.RulesApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;

public class RuleSectionFactory {
	
	public static boolean isHordeToken(String category) {
		return category.equals("horde tokens")
				|| category.equals("horde tokens erreta");
	}

	public static boolean isObject(String category) {
		return category.equals("objects");
	}

	public static boolean isFeat(String category) {
		return category.equals("feats");
	}

	public static boolean isAta(String category) {
		return category.equals("additional team abilities");
	}
	
	public static SpannableStringBuilder createObject(Context context, JSONObject object) {
		try {
			SpannableStringBuilder ruleText = new SpannableStringBuilder();
			if (object.has(JSON_TYPE)) {
				ruleText.append(context.getString(R.string.type));
				ruleText.append(": ");
				ruleText.append(parseText(context, object.optString(JSON_TYPE)));
				ruleText.append("\n");
			}
			if (object.has(JSON_COST)) {
				ruleText.append(context.getString(R.string.cost));
				ruleText.append(": ");
				ruleText.append(parseText(context, object.optString(JSON_COST)));
				ruleText.append("\n");
			}
			if (object.has(JSON_RELIC)) {
				ruleText.append(context.getString(R.string.relic));
				ruleText.append(": ");
				ruleText.append(parseText(context, object.optString(JSON_RELIC)));
				ruleText.append("\n");
			}
			if (object.has(JSON_OWNER)) {
				ruleText.append(context.getString(R.string.owner));
				ruleText.append(": ");
				ruleText.append(parseText(context, object.optString(JSON_OWNER)));
				ruleText.append("\n");
			}
			ruleText.append("\n");
			return ruleText.append(parseText(context, object.getString(getLanguage(context, object))));
		} catch (JSONException e) {
			e.printStackTrace();
			return new SpannableStringBuilder(parseText(context, context.getString(R.string.no_rule)));
		}
	}

	public static SpannableStringBuilder createFeat(Context context, JSONObject feat) {
		try {
			SpannableStringBuilder ruleText = new SpannableStringBuilder();
			ruleText.append(context.getString(R.string.cost));
			ruleText.append(": ");
			ruleText.append(parseText(context, feat.optString(JSON_COST)));
			ruleText.append("\n");

			if (feat.has(JSON_MODIFIER)) {
				ruleText.append(context.getString(R.string.modifier));
				ruleText.append(": ");
				ruleText.append(parseText(context, feat.getString(JSON_MODIFIER)));
				ruleText.append("\n");
			}
			if (feat.has(JSON_PREREQUISITE)) {
				ruleText.append(context.getString(R.string.prerequisite));
				ruleText.append(": ");
				ruleText.append(parseText(context, feat.optString(JSON_PREREQUISITE)));
				ruleText.append("\n");
			}
			ruleText.append("\n");
			return ruleText.append(parseText(context, feat.getString(getLanguage(context, feat))));
		} catch (JSONException e) {
			e.printStackTrace();
			return new SpannableStringBuilder(context.getString(R.string.no_rule));
		}
	}

	public static SpannableStringBuilder createAta(Context context, JSONObject ruleObject) {
		try {
			JSONObject ata = ruleObject.getJSONObject(getLanguage(context, ruleObject));
			SpannableStringBuilder ruleText = new SpannableStringBuilder();
			ruleText.append(context.getString(R.string.cost));
			ruleText.append(": ");
			ruleText.append(parseText(context, ata.optString(JSON_COST)));
			ruleText.append("\n");

			if (ata.has(JSON_KEYWORDS)) {
				ruleText.append(context.getString(R.string.keywords));
				ruleText.append(": ");
				ruleText.append(parseText(context, ata.getString(JSON_KEYWORDS)));
				ruleText.append("\n");
			}
			ruleText.append("\n");
			return ruleText.append(parseText(context, ata.getString(JSON_TEXT)));
		} catch (JSONException e) {
			e.printStackTrace();
			return new SpannableStringBuilder(context.getString(R.string.no_rule));
		}
	}
	
	public static SpannableStringBuilder createHordetoken(Context context, JSONObject horde) {
		try {
			SpannableStringBuilder ruleText = new SpannableStringBuilder();
			if (horde.has(JSON_SET)) {
				ruleText.append(context.getString(R.string.set));
				ruleText.append(": ");
				ruleText.append(parseText(context, horde.optString(JSON_SET)));
				ruleText.append("\n");
			}
			if (horde.has(JSON_ID)) {
				ruleText.append(context.getString(R.string.id));
				ruleText.append(": ");
				ruleText.append(parseText(context, horde.optString(JSON_ID)));
				ruleText.append("\n");
			}
			ruleText.append("\n");
			return ruleText.append(parseText(context, horde.getString(getLanguage(context, horde))));
		} catch (JSONException e) {
			e.printStackTrace();
			return new SpannableStringBuilder(parseText(context, context.getString(R.string.no_rule)));
		}
	}
	
	public static SpannableStringBuilder createPowerRule(Context context, JSONArray rules, int position) {
		try {
			JSONObject rule = rules.getJSONObject(position);
			SpannableStringBuilder ruleText = new SpannableStringBuilder();
			
			ImageSpan ruleImage = getImageSpanPowerRule(
					context,
					rule.getJSONObject(RulesApplication.ENGLISH).getString(
							RulesApplication.JSON_NAME));

			if (ruleImage != null) {
				ruleText.append("  ");
				ruleText.setSpan(ruleImage, 0, 1,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			
			return ruleText.append(parseText(context, rule.getJSONObject(getLanguage(context, rule)).getString(JSON_TEXT)));
		} catch (JSONException e) {
			e.printStackTrace();
			return new SpannableStringBuilder(parseText(context, context.getString(R.string.no_rule)));
		}
	}

	public static SpannableStringBuilder createRule(Context context, JSONObject rule, 
			String title, String category) {
		try {
			SpannableStringBuilder ruleText = new SpannableStringBuilder();
			ImageSpan ruleImage = getImageSpanForRuleIfExists(context, rule, title, category);

			if (ruleImage != null) {
				ruleText.append("  ");
				ruleText.setSpan(ruleImage, 0, 1,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}

			return ruleText.append(parseText(context, rule.getString(getLanguage(context, rule))));
		} catch (JSONException e) {
			e.printStackTrace(); // TODO This block should be outside!
			return new SpannableStringBuilder(parseText(context, context.getString(R.string.no_rule)));
		}
	}
	
	private static ImageSpan getImageSpanForRuleIfExists(Context context,
			JSONObject rule, String title, String category) {
		int id = ((RulesApplication) context.getApplicationContext())
				.getImageIdForRuleIfExists(rule, title, category);
		if (id == 0) {
			return null;
		}
		return new ImageSpan(context, id);
	}
	
	private static ImageSpan getImageSpanPowerRule(Context context,
			String imageName) {
		int id = ((RulesApplication) context.getApplicationContext())
				.getImageIdForPowerRule(imageName);
		if (id == 0) {
			return null;
		}
		return new ImageSpan(context, id);
	}

	private static String getLanguage(Context context, JSONObject rule) {
		String language = ((RulesApplication) context.getApplicationContext()).getLanguage();
		if (rule.has(language)) {
			return language;
		} else {
			return ENGLISH;
		}
	}
}
