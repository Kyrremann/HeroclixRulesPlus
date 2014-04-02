package net.fifthfloorstudio.heroclixrules.plus;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import heroclix.Rules.R;

public class Loading extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = null;
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					intent = new Intent(Loading.this, HeroclixRulesPlusHoneyComb.class);
				} else {
					intent = new Intent(Loading.this, HeroclixRulesPlus.class);
				}
				startActivity(intent);
				finish();
			}
		}, 1200);
	}

}
