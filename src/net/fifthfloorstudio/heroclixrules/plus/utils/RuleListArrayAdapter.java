package net.fifthfloorstudio.heroclixrules.plus.utils;

import net.fifthfloorstudio.heroclixrules.plus.RulesApplication;

import org.json.JSONException;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class RuleListArrayAdapter extends ArrayAdapter<String> implements
		ListAdapter {

	private String[] rules_array;
	private int title;
	private int layout;
	private int image;
	private Context context;
	private RulesApplication application;
	private String category;

	public RuleListArrayAdapter(Context context, int layout, int title,
			int image, String[] rules_array, String category) {
		super(context, layout, rules_array);
		this.context = context;
		this.layout = layout;
		this.title = title;
		this.image = image;
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
			holder.title = (TextView) convertView.findViewById(title);
			holder.image = (ImageView) convertView.findViewById(image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.title.setText(rules_array[position]);
		int id;
		try {
			id = application.getImageIdForRuleIfExists(application.getRuleJSON(
					position, rules_array[position], category),
					rules_array[position]);
		} catch (JSONException e) {
			e.printStackTrace();
			id = 0;
		}

		if (id != 0) {
			holder.image.setImageResource(id);
		} else {
			holder.image.setVisibility(View.GONE);
		}

		return convertView;
	}

	private class ViewHolder {
		TextView title;
		ImageView image;
	}

}
