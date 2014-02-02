package net.fifthfloorstudio.heroclixrules.plus.utils;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class JSONParser {
	
	public static final String JSON_POWERS = "powers.json";
	public static final String JSON_TEAM_ABILITIES = "team_abilities.json";

	private static JSONObject getMeSomeJson(Context context, String file)
			throws IOException, JSONException {
		if (!file.endsWith(".json")) {
			file += ".json";
		}
		InputStream inputStream = context.getAssets().open(file);
		byte[] buffer = new byte[inputStream.available()];
		inputStream.read(buffer);
		inputStream.close();
		return new JSONObject(new String(buffer));
	}

	public static JSONObject getPowers(Context context) {
		try {
			return JSONParser.getMeSomeJson(context, JSON_POWERS);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static JSONObject getTeamAbilities(Context context) {
		try {
			return JSONParser.getMeSomeJson(context, JSON_TEAM_ABILITIES);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}
}
