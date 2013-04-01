package com.byfdevelopment.popcine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MovieActivity extends FragmentActivity {

	private static SimpleAdapter adapter;

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.activity_movie);

		String[] de = { "nome", "endereco", "horarios" };
		int[] para = { R.id.nome_cinema, R.id.endereco, R.id.horarios_filme };
		adapter = new SimpleAdapter(this, listTheaters(), R.layout.theaters_list_item, de, para);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			// NavUtils.navigateUpFromSameTask(this);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private List<Map<String, Object>> listTheaters() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		HashMap<String, Object> item = new HashMap<String, Object>();
		item.put("nome", "Cinemark Campo Grande");
		item.put("endereco", "Av. Afonso Pena, 4909");
		item.put("horarios", "12:10‎  ‎13:10‎  ‎14:30‎  ‎15:30‎  ‎16:40‎  ‎17:40‎  ‎19:00‎  ‎20:00‎  ‎21:20‎  ‎22:10");
		list.add(item);

		item = new HashMap<String, Object>();
		item.put("nome", "Cinépolis Norte Sul");
		item.put("endereco", "Avenida Presidente Ernesto Geisel, 2300");
		item.put("horarios", "12:10‎  ‎13:10‎  ‎14:30‎  ‎15:30‎  ‎16:40‎  ‎17:40‎  ‎19:00‎  ‎20:00‎  ‎21:20‎  ‎22:10");
		list.add(item);

		item = new HashMap<String, Object>();
		item.put("nome", "Cinépolis Norte Sul");
		item.put("endereco", "Avenida Presidente Ernesto Geisel, 2300");
		item.put("horarios", "12:10‎  ‎13:10‎  ‎14:30‎  ‎15:30‎  ‎16:40‎  ‎17:40‎  ‎19:00‎  ‎20:00‎  ‎21:20‎  ‎22:10");
		list.add(item);

		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_movie, menu);
		return true;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase();
			case 1:
				return getString(R.string.title_section2).toUpperCase();
			case 2:
				return getString(R.string.title_section3).toUpperCase();
			}
			return null;
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
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			// Create a new TextView and set its text to the fragment's section
			// number argument value.

			if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
				View view = inflater.inflate(R.layout.theaters_list, container, false);
				ListView listTheaters = (ListView) view.findViewById(R.id.list_theaters);

				listTheaters.setAdapter(adapter);
				listTheaters.setDivider(null);
				listTheaters.setBackgroundColor(0xcccccccc);

				return view;
			} else {
				return inflater.inflate(R.layout.activity_popcine, container, false);
			}
		}

	}

}
