package net.fifthfloorstudio.heroclixrules.plus;

import static net.fifthfloorstudio.heroclixrules.plus.utils.StringUtils.parseText;

import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SectionsRuleFragment extends Fragment {

	public static final String ARG_RULE_POSITION = "rule_position";
	public static final String ARG_RULES = "rules_array";
	public static final String ARG_CATEGORY = "rule_category";

	private static RulesApplication application;
	private static View rootView;
	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;

	private static String[] rules_array;
	private static int rule_position;
	private static String rule_category;

	public SectionsRuleFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_rule_section, container,
				false);
		rule_category = getArguments().getString(ARG_CATEGORY)
				.replaceAll("\\spowers", "").toLowerCase(Locale.getDefault());
		rules_array = getArguments().getStringArray(ARG_RULES);
		rule_position = getArguments().getInt(ARG_RULE_POSITION);
		application = (RulesApplication) getActivity().getApplicationContext();

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
	public static class DummySectionFragment extends Fragment {
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
			int pos = getArguments().getInt(ARG_SECTION_NUMBER);
			((TextView) rootView.findViewById(R.id.general_rule))
					.setText(parseText(getActivity(), application.getPowerRule(
							rules_array[pos], rule_category)));
			return rootView;
		}
	}
}
