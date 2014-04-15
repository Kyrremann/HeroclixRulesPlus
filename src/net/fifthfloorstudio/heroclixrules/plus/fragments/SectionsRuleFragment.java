package net.fifthfloorstudio.heroclixrules.plus.fragments;

import static net.fifthfloorstudio.heroclixrules.plus.utils.RuleSectionFactory.createAta;
import static net.fifthfloorstudio.heroclixrules.plus.utils.RuleSectionFactory.createFeat;
import static net.fifthfloorstudio.heroclixrules.plus.utils.RuleSectionFactory.createHordetoken;
import static net.fifthfloorstudio.heroclixrules.plus.utils.RuleSectionFactory.createObject;
import static net.fifthfloorstudio.heroclixrules.plus.utils.RuleSectionFactory.createPowerRule;
import static net.fifthfloorstudio.heroclixrules.plus.utils.RuleSectionFactory.createRule;
import static net.fifthfloorstudio.heroclixrules.plus.utils.RuleSectionFactory.isAta;
import static net.fifthfloorstudio.heroclixrules.plus.utils.RuleSectionFactory.isFeat;
import static net.fifthfloorstudio.heroclixrules.plus.utils.RuleSectionFactory.isHordeToken;
import static net.fifthfloorstudio.heroclixrules.plus.utils.RuleSectionFactory.isObject;
import heroclix.Rules.R;

import java.util.Locale;

import net.fifthfloorstudio.heroclixrules.plus.RulesApplication;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SectionsRuleFragment extends AbstractRuleFragment {

	private static View rootView;
	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;
	private static JSONObject rules;

	private static int rule_position;

	public SectionsRuleFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_rule_section, container,
				false);
		category = getArguments().getString(ARG_CATEGORY)
				.replaceAll("\\spowers", "").toLowerCase(Locale.getDefault());
		rules_array = getArguments().getStringArray(ARG_RULES);
		rule_position = getArguments().getInt(ARG_RULE_POSITION);
		application = (RulesApplication) getActivity().getApplicationContext();
		
		rules = application.getJSONRules(category);

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getChildFragmentManager());

		mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setCurrentItem(rule_position, true);

		return rootView;
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			rule_position = position;
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return rules_array.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return rules_array[position].toUpperCase(Locale.getDefault());
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends AbstractRuleFragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_main_activity_test_dummy, container,
					false);
			int position = getArguments().getInt(ARG_SECTION_NUMBER);
			SpannableStringBuilder builder;
			String title = rules_array[position];
			try {
				
				if (RulesApplication.isPowerRule(category)) {
					builder = createPowerRule(getActivity(),
							rules.getJSONArray(category), position);
				} else {
					JSONObject rule = rules.getJSONObject(title);

					if (isFeat(category)) {
						builder = createFeat(getActivity(), rule);
					} else if (isObject(category)) {
						builder = createObject(getActivity(), rule);
					} else if (isHordeToken(category)) {
						builder = createHordetoken(getActivity(), rule);
					} else if (isAta(category)) {
						builder = createAta(getActivity(), rule);
					} else {
						builder = createRule(getActivity(), rule, title,
								category);
					}
				}
				((TextView) rootView.findViewById(R.id.general_rule))
						.setText(builder);
			} catch (JSONException e) {
				e.printStackTrace();
				((TextView) rootView.findViewById(R.id.general_rule))
						.setText(getString(R.string.no_rule));
			}
			return rootView;
		}
	}
}
