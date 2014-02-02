package net.fifthfloorstudio.heroclixrules.plus;

import net.fifthfloorstudio.heroclixrules.plus.inteface.RuleSelectedListener;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RuleListFragment extends Fragment implements OnItemClickListener {

	public static final String ARG_RULE_TITLE = "rule_title";

	private RuleSelectedListener mCallback;
	private String[] rules_array;
	private RulesApplication application;

	public RuleListFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_planet, container,
				false);
		String title = getArguments().getString(ARG_RULE_TITLE);
		getActivity().setTitle(title);
		ListView list = (ListView) rootView.findViewById(R.id.rules_list);
		title = title.replaceAll(" ", "_");
		int ruleId = getResources().getIdentifier(title, "array",
				getActivity().getPackageName());
		// rules_array = getResources().getStringArray(ruleId);
		application = (RulesApplication) getActivity().getApplicationContext();
		rules_array = application.getPowerRulesTitles("speed");
		list.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1,
				rules_array));
		list.setOnItemClickListener(this);
		return rootView;
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
}
