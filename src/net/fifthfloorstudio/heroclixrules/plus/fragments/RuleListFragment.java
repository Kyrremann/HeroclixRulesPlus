package net.fifthfloorstudio.heroclixrules.plus.fragments;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Locale;

import net.fifthfloorstudio.heroclixrules.plus.R;
import net.fifthfloorstudio.heroclixrules.plus.RulesApplication;
import net.fifthfloorstudio.heroclixrules.plus.utils.RuleListArrayAdapter;
import net.fifthfloorstudio.heroclixrules.plus.utils.RuleSelectedListener;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RuleListFragment extends Fragment implements OnItemClickListener {

	public static final String ARG_RULE_TITLE = "rule_title";

	private RuleSelectedListener mCallback;
	private String[] rules_array;
	private RulesApplication application;
	private ListView listView;
	private String category;

	public RuleListFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_rule_list, container,
				false);
		String title = getArguments().getString(ARG_RULE_TITLE);
		getActivity().setTitle(title);

		listView = (ListView) rootView.findViewById(R.id.rules_list);
		category = title.replaceAll(" powers", "").toLowerCase(
				Locale.ENGLISH);
		application = (RulesApplication) getActivity().getApplicationContext();
		rules_array = application.getRuleTitles(category);
		rules_array = moveGeneralItemToTopOfArray(rules_array);
		listView.setAdapter(new RuleListArrayAdapter(getActivity(),
				R.layout.rules_with_image_row, R.id.rule_row, rules_array, category));
		listView.setOnItemClickListener(this);
		return rootView;
	}

	private String[] moveGeneralItemToTopOfArray(String[] array) {
		LinkedList<String> linkedList = new LinkedList<String>();
		linkedList.addAll(Arrays.asList(array));
		if (linkedList.contains("General")) {
			linkedList.remove("General");
			linkedList.addFirst("General");
		}
		
		return linkedList.toArray(new String[array.length]);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mCallback = (RuleSelectedListener) activity;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		mCallback.onRuleSelectedListener(position, rules_array);
	}

	public void notifyListChanged() {
		rules_array = application.getRuleTitles(category);
		rules_array = moveGeneralItemToTopOfArray(rules_array);
		listView.setAdapter(new RuleListArrayAdapter(getActivity(),
				R.layout.rules_with_image_row, R.id.rule_row, rules_array, category));
	}
}
