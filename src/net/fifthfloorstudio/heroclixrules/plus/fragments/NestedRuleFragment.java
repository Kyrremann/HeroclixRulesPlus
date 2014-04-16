package net.fifthfloorstudio.heroclixrules.plus.fragments;

import heroclix.Rules.R;

import java.util.Locale;

import net.fifthfloorstudio.heroclixrules.plus.RulesApplication;
import net.fifthfloorstudio.heroclixrules.plus.utils.RuleListArrayAdapter;
import net.fifthfloorstudio.heroclixrules.plus.utils.RuleSelectedListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class NestedRuleFragment extends AbstractRuleFragment implements OnItemClickListener {
	
	private RuleSelectedListener mCallback;
	private ListView listView;
	private String nestedRule;

	public NestedRuleFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_rule_list,
				container, false);
		category = getArguments().getString(ARG_CATEGORY).toLowerCase(Locale.ENGLISH);
		nestedRule = getArguments().getString(ARG_RULE_TITLE);
		getActivity().setTitle(nestedRule);
		listView = (ListView) rootView.findViewById(R.id.rules_list);
		
		application = (RulesApplication) getActivity().getApplicationContext();
		rules_array = application.getNestedRuleTitles(category, nestedRule);
		rules_array = moveGeneralItemToTopOfArray(rules_array);
		listView.setAdapter(new RuleListArrayAdapter(getActivity(),
				R.layout.rules_with_image_row, R.id.rule_row, rules_array,
				category));
		listView.setOnItemClickListener(this);
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
		mCallback.onNestedRuleSelectedListener(position, rules_array, category, nestedRule);
	}

	public void notifyListChanged() {
		rules_array = application.getNestedRuleTitles(category, nestedRule);
		rules_array = moveGeneralItemToTopOfArray(rules_array);
		listView.setAdapter(new RuleListArrayAdapter(getActivity(),
				R.layout.rules_with_image_row, R.id.rule_row, rules_array, category));
	}
}
