/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fifthfloorstudio.heroclixrules.plus;

import net.fifthfloorstudio.heroclixrules.plus.fragments.RuleListFragment;
import net.fifthfloorstudio.heroclixrules.plus.fragments.SectionsRuleFragment;
import net.fifthfloorstudio.heroclixrules.plus.utils.RuleSelectedListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HeroclixRulesPlus extends FragmentActivity implements
		RuleSelectedListener {

	protected DrawerLayout mDrawerLayout;
	protected ListView mDrawerList;
	protected ActionBarDrawerToggle mDrawerToggle;
	protected CharSequence mDrawerTitle;

	protected CharSequence mTitle;
	protected String[] mRulesTitles;
	protected boolean toggle_images = false;
	protected int selectedDrawer;
	private AlertDialog donateDialog;
	private AlertDialog aboutDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();
		mRulesTitles = getResources().getStringArray(R.array.rules_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mRulesTitles));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		createDrawer(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		switch (item.getItemId()) {
		case R.id.menu_about:
			createAbout();
			break;
		case R.id.menu_settings:
			startActivity(createIntent(SettingsActivity.class,
					SettingsActivityHoneyComb.class));
			selectItem(selectedDrawer);
			break;
		case R.id.menu_donate:
			createDonate();
			break;
		case R.id.menu_feedback:
			createFeedback();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	private <T, S> Intent createIntent(Class<T> normalClass,
			Class<S> honeyCombClass) {
		Intent intent = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			intent = new Intent(this, honeyCombClass);
		} else {
			intent = new Intent(this, normalClass);
		}
		return intent;
	}

	/* The click listener for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectedDrawer = position;
			selectItem(position);
		}
	}

	protected void selectItem(int position) {
		// update the main content by replacing fragments
		Fragment fragment = new RuleListFragment();
		Bundle args = new Bundle();
		args.putString(RuleListFragment.ARG_RULE_TITLE, mRulesTitles[position]);
		fragment.setArguments(args);

		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.popBackStack(null,
				FragmentManager.POP_BACK_STACK_INCLUSIVE);
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(mRulesTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		super.setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggle
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onRuleSelectedListener(int position, String[] rules) {
		Fragment fragment = new SectionsRuleFragment();
		Bundle args = new Bundle();
		args.putString(SectionsRuleFragment.ARG_CATEGORY, mTitle.toString());
		args.putInt(SectionsRuleFragment.ARG_RULE_POSITION, position);
		args.putStringArray(SectionsRuleFragment.ARG_RULES, rules);
		fragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).addToBackStack(null)
				.commit();
	}

	protected void createDrawer(Bundle savedInstanceState) {
		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				// getActionBar().setTitle(mTitle);
				// invalidateOptionsMenu(); // creates call to
				// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				// getActionBar().setTitle(mDrawerTitle);
				// invalidateOptionsMenu(); // creates call to
				// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}
	}

	private void createDonate() {
		if (donateDialog == null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Donation");
			builder.setMessage(R.string.donateText);
			builder.setPositiveButton(android.R.string.ok,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent browserIntent = new Intent(
									"android.intent.action.VIEW",
									Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=ATWBY2VZ3SW7Q"));
							startActivity(browserIntent);
						}
					});
			builder.setNegativeButton(R.string.later,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});

			donateDialog = builder.create();
		}
		donateDialog.show();
	}

	public void createAbout() {
		if (aboutDialog == null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.about);
			builder.setMessage(R.string.aboutText);
			builder.setPositiveButton(R.string.rate,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							String url;
							// url =
							// "http://www.amazon.com/gp/mas/dl/android?p=heroclix.Rules";
							url = "market://details?id=heroclix.Rules";

							Intent browserIntent = new Intent(
									"android.intent.action.VIEW", Uri
											.parse(url));

							startActivity(browserIntent);
						}
					});

			builder.setNeutralButton(android.R.string.ok,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			builder.setNegativeButton(R.string.donate,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							createDonate();
						}
					});

			aboutDialog = builder.create();
		}
		aboutDialog.show();
	}

	private void createFeedback() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("plain/text");
		intent.putExtra(Intent.EXTRA_EMAIL,
				new String[] { getString(R.string.e_mail) });
		String version = "3.*";
		try {
			version = getPackageManager().getPackageInfo(
					getString(R.string.package_name), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		intent.putExtra(Intent.EXTRA_SUBJECT,
				getString(R.string.feedback_subject) + " " + version);
		startActivity(Intent.createChooser(intent, ""));
	}
}