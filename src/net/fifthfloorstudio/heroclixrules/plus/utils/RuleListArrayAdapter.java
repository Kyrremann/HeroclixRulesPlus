package net.fifthfloorstudio.heroclixrules.plus.utils;

import net.fifthfloorstudio.heroclixrules.plus.RulesApplication;

import org.json.JSONException;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class RuleListArrayAdapter extends ArrayAdapter<String> implements
		ListAdapter {

	private String[] rules_array;
	private int layout;
	private int item;
	private Context context;
	private RulesApplication application;
	private String category;

	public RuleListArrayAdapter(Context context, int layout, int item, String[] rules_array, String category) {
		super(context, layout, rules_array);
		this.context = context;
		this.layout = layout;
		this.item = item;
		this.rules_array = rules_array;
		this.category = category;

		application = (RulesApplication) context.getApplicationContext();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, layout, null);
			holder.item = (TextView) convertView.findViewById(item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.item.setText(rules_array[position]);
		int id;
		try {
			id = application.getImageIdForRuleIfExists(application.getRuleJSON(
					position, rules_array[position], category),
					rules_array[position], category);
		} catch (JSONException e) {
			e.printStackTrace();
			id = 0;
		}

		if (id != 0) {
			float scale = context.getResources().getDisplayMetrics().density;
			 int dp = (int) (36 * scale + 0.5f); // magic numbers
			Drawable drawable = context.getResources().getDrawable(id);
			if (imageExtraWide(rules_array[position])) {
				drawable.setBounds(0, 0, dp * 2, dp);
			} else {
				drawable.setBounds(0, 0, dp, dp);
			}
			holder.item.setVisibility(View.VISIBLE);
			holder.item.setCompoundDrawables(null, null, drawable, null);
		} else {
			holder.item.setCompoundDrawables(null, null, null, null);
		}

		return convertView;
	}
	
	private boolean imageExtraWide(String title) {
		return title.equals("Swim")
				|| title.equals("Flight");
	}

	private class ViewHolder {
		TextView item;
	}

}
