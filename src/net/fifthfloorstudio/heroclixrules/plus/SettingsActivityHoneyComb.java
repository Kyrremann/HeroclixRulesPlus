package net.fifthfloorstudio.heroclixrules.plus;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

@SuppressLint("NewApi")
public class SettingsActivityHoneyComb extends SettingsActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
}
