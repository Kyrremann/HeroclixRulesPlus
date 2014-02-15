package net.fifthfloorstudio.heroclixrules.plus.utils;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class JSONParser {

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

	public static JSONObject getJsonRule(Context context, String json) {
		try {
			return JSONParser.getMeSomeJson(context, json);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}
}
