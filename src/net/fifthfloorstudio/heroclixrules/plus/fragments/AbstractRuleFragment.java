package net.fifthfloorstudio.heroclixrules.plus.fragments;

import java.util.Arrays;
import java.util.LinkedList;

import net.fifthfloorstudio.heroclixrules.plus.RulesApplication;
import android.support.v4.app.Fragment;

public abstract class AbstractRuleFragment extends Fragment {

	public static final String ARG_RULE_TITLE = "rule_title";
	public static final String ARG_RULE_POSITION = "rule_position";
	public static final String ARG_RULES = "rules_array";
	public static final String ARG_CATEGORY = "rule_category";
	
	protected static RulesApplication application;
	protected static String[] rules_array;
	protected static String category;

	protected String[] moveGeneralItemToTopOfArray(String[] array) {
		LinkedList<String> linkedList = new LinkedList<String>();
		linkedList.addAll(Arrays.asList(array));
		if (linkedList.contains("General")) {
			linkedList.remove("General");
			linkedList.addFirst("General");
		}
		
		return linkedList.toArray(new String[array.length]);
	}
}
