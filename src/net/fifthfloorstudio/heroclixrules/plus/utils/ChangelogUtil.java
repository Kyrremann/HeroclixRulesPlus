package net.fifthfloorstudio.heroclixrules.plus.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import heroclix.Rules.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class ChangelogUtil {



	public static View getChangelogView(Context context) {
		return populateViewWithJSON(context, JSONParser.getJsonChangelog(context));
	}


	@SuppressWarnings("unchecked")
	private static View populateViewWithJSON(Context context, JSONObject changelog) {
		LinearLayout view = new LinearLayout(context);
		int dimen = (int) context.getResources().getDimension(R.dimen.activity_horizontal_margin);
		view.setPadding(dimen, 0, dimen, 0);
		view.setOrientation(LinearLayout.VERTICAL);
		Iterator<String> keys = changelog.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			try {
				addChangesToView(context, key, changelog.getJSONArray(key), view);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return view;
	}

	private static void addChangesToView(Context context, String title, JSONArray changes,
	                                     LinearLayout parent) throws JSONException {
		TextView titleView = new TextView(context);
		titleView.setTextAppearance(context,
				android.R.style.TextAppearance_Large);
		titleView.setText(title);
		parent.addView(titleView);

		String stringChanges = createStringFromChanges(changes);
		TextView changeView = new TextView(context);
		changeView.setTextAppearance(context,
				android.R.style.TextAppearance_Medium);
		changeView.setText(stringChanges);
		parent.addView(changeView);
	}

	private static String createStringFromChanges(JSONArray array)
			throws JSONException {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < array.length(); i++) {
			stringBuilder.append(" • ");
			stringBuilder.append(array.get(i));
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}
}
