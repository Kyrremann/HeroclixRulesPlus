package net.fifthfloorstudio.heroclixrules.plus.fragments;

import static net.fifthfloorstudio.heroclixrules.plus.utils.StringUtils.parseText;
import static net.fifthfloorstudio.heroclixrules.plus.RulesApplication.*;

import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import net.fifthfloorstudio.heroclixrules.plus.R;
import net.fifthfloorstudio.heroclixrules.plus.RulesApplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
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
	private static String category;

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
			int position = getArguments().getInt(ARG_SECTION_NUMBER);
			SpannableStringBuilder builder;

			if (isFeat()) {
				builder = createFeat(position);
			} else if (isObject()) {
				builder = createObject(position);
			} else if (isHordeToken()) {
				builder = createHordetoken(position);
			} else if (isAta()) {
				builder = createAta(position);
			} else {
				builder = createRule(position);
			}

			((TextView) rootView.findViewById(R.id.general_rule))
					.setText(builder);
			return rootView;
		}

		private boolean isHordeToken() {
			return category.equals("horde tokens")
					|| category.equals("horde tokens erreta");
		}

		private boolean isObject() {
			return category.equals("objects");
		}

		private boolean isFeat() {
			return category.equals("feats");
		}

		private boolean isAta() {
			return category.equals("additional team abilities");
		}

		private SpannableStringBuilder createHordetoken(int position) {
			try {
				JSONObject object = application.getRuleJSON(position,
						rules_array[position], category);

				SpannableStringBuilder ruleText = new SpannableStringBuilder();
				if (object.has(JSON_SET)) {
					ruleText.append(getString(R.string.set));
					ruleText.append(": ");
					ruleText.append(parseText(getActivity(), object.optString(JSON_SET)));
					ruleText.append("\n");
				}
				if (object.has(JSON_ID)) {
					ruleText.append(getString(R.string.id));
					ruleText.append(": ");
					ruleText.append(parseText(getActivity(), object.optString(JSON_ID)));
					ruleText.append("\n");
				}
				ruleText.append("\n");
				return ruleText.append(parseText(getActivity(), object.getString(ENGLISH)));
			} catch (JSONException e) {
				e.printStackTrace();
				return new SpannableStringBuilder(parseText(getActivity(), getString(R.string.no_rule)));
			}
		}

		private SpannableStringBuilder createRule(int position) {
			SpannableStringBuilder ruleText = new SpannableStringBuilder();
			ImageSpan ruleImage = application.getImageSpanForRuleIfExists(
					position, rules_array[position], category);

			if (ruleImage != null) {
				ruleText.append("  ");
				ruleText.setSpan(ruleImage, 0, 1,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}

			return ruleText.append(parseText(getActivity(), application
					.getRuleText(position, rules_array[position], category)));
		}

		private SpannableStringBuilder createObject(int position) {
			try {
				JSONObject object = application.getRuleJSON(position,
						rules_array[position], category);

				SpannableStringBuilder ruleText = new SpannableStringBuilder();
				if (object.has(JSON_TYPE)) {
					ruleText.append(getString(R.string.type));
					ruleText.append(": ");
					ruleText.append(parseText(getActivity(), object.optString(JSON_TYPE)));
					ruleText.append("\n");
				}
				if (object.has(JSON_COST)) {
					ruleText.append(getString(R.string.cost));
					ruleText.append(": ");
					ruleText.append(parseText(getActivity(), object.optString(JSON_COST)));
					ruleText.append("\n");
				}
				if (object.has(JSON_RELIC)) {
					ruleText.append(getString(R.string.relic));
					ruleText.append(": ");
					ruleText.append(parseText(getActivity(), object.optString(JSON_RELIC)));
					ruleText.append("\n");
				}
				if (object.has(JSON_OWNER)) {
					ruleText.append(getString(R.string.owner));
					ruleText.append(": ");
					ruleText.append(parseText(getActivity(), object.optString(JSON_OWNER)));
					ruleText.append("\n");
				}
				ruleText.append("\n");
				return ruleText.append(parseText(getActivity(), object.getString(ENGLISH)));
			} catch (JSONException e) {
				e.printStackTrace();
				return new SpannableStringBuilder(parseText(getActivity(), getString(R.string.no_rule)));
			}
		}

		private SpannableStringBuilder createFeat(int position) {
			try {
				JSONObject feat = application.getRuleJSON(position,
						rules_array[position], category);

				SpannableStringBuilder ruleText = new SpannableStringBuilder();
				ruleText.append(getString(R.string.cost));
				ruleText.append(": ");
				ruleText.append(parseText(getActivity(), feat.optString(JSON_COST)));
				ruleText.append("\n");

				if (feat.has(JSON_MODIFIER)) {
					ruleText.append(getString(R.string.modifier));
					ruleText.append(": ");
					ruleText.append(parseText(getActivity(), feat.getString(JSON_MODIFIER)));
					ruleText.append("\n");
				}
				if (feat.has(JSON_PREREQUISITE)) {
					ruleText.append(getString(R.string.prerequisite));
					ruleText.append(": ");
					ruleText.append(parseText(getActivity(), feat.optString(JSON_PREREQUISITE)));
					ruleText.append("\n");
				}
				ruleText.append("\n");
				return ruleText.append(parseText(getActivity(), feat.getString(ENGLISH)));
			} catch (JSONException e) {
				e.printStackTrace();
				return new SpannableStringBuilder(getString(R.string.no_rule));
			}
		}

		private SpannableStringBuilder createAta(int position) {
			try {
				JSONObject ata = application.getRuleJSONBasedOnLanguage(position,
						rules_array[position], category);

				SpannableStringBuilder ruleText = new SpannableStringBuilder();
				ruleText.append(getString(R.string.cost));
				ruleText.append(": ");
				ruleText.append(parseText(getActivity(), ata.optString(JSON_COST)));
				ruleText.append("\n");

				if (ata.has(JSON_KEYWORDS)) {
					ruleText.append(getString(R.string.keywords));
					ruleText.append(": ");
					ruleText.append(parseText(getActivity(), ata.getString(JSON_KEYWORDS)));
					ruleText.append("\n");
				}
				ruleText.append("\n");
				return ruleText.append(parseText(getActivity(), ata.getString(JSON_TEXT)));
			} catch (JSONException e) {
				e.printStackTrace();
				return new SpannableStringBuilder(getString(R.string.no_rule));
			}
		}
	}
}
