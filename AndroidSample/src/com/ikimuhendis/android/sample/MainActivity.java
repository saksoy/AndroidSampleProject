package com.ikimuhendis.android.sample;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.ikimuhendis.android.sample.fragments.BaseFragment;
import com.ikimuhendis.android.sample.fragments.CloudFragment;
import com.ikimuhendis.android.sample.fragments.InfoFragment;
import com.ikimuhendis.android.sample.fragments.SettingsFragment;

public class MainActivity extends SherlockFragmentActivity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private DrawerMenuListAdapter mMenuAdapter;
	private ActionBarDrawerToggle mDrawerToggle;
	private int mSelectedFragment;
	private BaseFragment mBaseFragment;

	private String[] drawerTitles;
	private String[] drawerSubtitles;
	private int[] drawerIcons;

	private static String BUNDLE_SELECTEDFRAGMENT = "BDL_SELFRG";
	private final static int CASE_FRAGMENT_CLOUD = 0;
	private final static int CASE_FRAGMENT_INFO = 1;
	private final static int CASE_FRAGMENT_SETTINGS = 2;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(BUNDLE_SELECTEDFRAGMENT, mSelectedFragment);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}
		}
		return super.onOptionsItemSelected(item);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		drawerTitles = getResources().getStringArray(R.array.drawer_titles);
		drawerSubtitles = getResources().getStringArray(
				R.array.drawer_subtitles);
		drawerIcons = new int[] { R.drawable.drawer_cloud,
				R.drawable.drawer_info, R.drawable.drawer_settings };

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.header_drawer);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		mMenuAdapter = new DrawerMenuListAdapter(MainActivity.this,
				drawerTitles, drawerSubtitles, drawerIcons);
		mDrawerList.setAdapter(mMenuAdapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState != null) {
			mSelectedFragment = savedInstanceState
					.getInt(BUNDLE_SELECTEDFRAGMENT);
			FragmentManager fragmentManager = getSupportFragmentManager();
			if (fragmentManager.findFragmentById(R.id.fragment_main) == null)
				mBaseFragment = selectFragment(mSelectedFragment);
		} else {
			mBaseFragment = new CloudFragment();
			openFragment(mBaseFragment);
		}
	}

	private BaseFragment selectFragment(int position) {
		BaseFragment baseFragment = null;

		switch (position) {

		case CASE_FRAGMENT_CLOUD:
			baseFragment = new CloudFragment();
			break;
		case CASE_FRAGMENT_INFO:
			baseFragment = new InfoFragment();
			break;
		case CASE_FRAGMENT_SETTINGS:
			baseFragment = new SettingsFragment();
			break;
		default:
			break;
		}
		return baseFragment;
	}

	private void openFragment(BaseFragment baseFragment) {
		if (baseFragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();

			fragmentTransaction.replace(R.id.fragment_main, baseFragment);
			fragmentTransaction.addToBackStack(baseFragment.getTitleResourceId()+"");
			fragmentTransaction.commit();
		}
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			mDrawerList.setItemChecked(position, true);
			mBaseFragment = selectFragment(position);
			mSelectedFragment = position;
			if (mBaseFragment != null)
				openFragment(mBaseFragment);
			mDrawerLayout.closeDrawer(mDrawerList);
		}
	}
}
