package net.fifthfloorstudio.heroclixrules.plus.utils;

public interface RuleSelectedListener {
	
	public void onRuleSelectedListener(int position, String[] rules, String category);
	
	public void onNestedRuleSelectedListener(int position, String[] rules, String category, String nestedRule);
}
