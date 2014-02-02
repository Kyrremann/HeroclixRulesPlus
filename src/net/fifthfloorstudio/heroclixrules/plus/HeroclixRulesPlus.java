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

import net.fifthfloorstudio.heroclixrules.plus.inteface.RuleSelectedListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
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
import android.view.WindowManager.LayoutParams;
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

	public static final String PREFS_NAME = "settings";
	public static final String PREFS_TOGGLE_SCREEN = "toggle_screen";
	public static final String PREFS_TOGGLE_IMAGES = "toggle_images";

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

		SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME,
				MODE_PRIVATE);

		toggle_images = sharedPreferences
				.getBoolean(PREFS_TOGGLE_IMAGES, false);
		boolean toggle_screen = sharedPreferences.getBoolean(
				PREFS_TOGGLE_SCREEN, false);
		toggleScreenOn(toggle_screen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME,
				MODE_PRIVATE);

		menu.findItem(R.id.menu_images).setChecked(toggle_images);
		menu.findItem(R.id.menu_toggle_screen).setChecked(
				sharedPreferences.getBoolean(PREFS_TOGGLE_SCREEN, false));
		return true;
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
			break;
		case R.id.menu_toggle_screen:
			toggleScreenOn(!item.isChecked());
			break;
		case R.id.menu_images:
			toggle_images = !item.isChecked();
			Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
			editor.putBoolean(PREFS_TOGGLE_IMAGES, toggle_images);
			editor.commit();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	protected void toggleScreenOn(boolean isChecked) {
		Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
		editor.putBoolean(PREFS_TOGGLE_SCREEN, isChecked);
		editor.commit();

		if (isChecked) {
			getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
	}

	/* The click listener for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
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
		// getActionBar().setTitle(mTitle);
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
		// Pass any configuration change to the drawer toggls
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
}