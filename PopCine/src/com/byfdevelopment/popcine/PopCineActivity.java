package com.byfdevelopment.popcine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;

import com.byfdevelopment.popcine.database.PopCineDataSource;
import com.byfdevelopment.popcine.objects.MovieObj;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivity;

public class PopCineActivity extends SlidingActivity implements OnItemClickListener {

	private String dublado = "D\nu\nb\nl\na\nd\no";
	private String legendado = "L\ne\ng\ne\nn\nd\na\nd\no";
	private String original = "O\nr\ni\ng\ni\nn\na\nl";

	private List<Map<String, Object>> moviesList = new ArrayList<Map<String, Object>>();
	private ListView view;
	private SimpleAdapter adapter;

	TextView cityname;
	List<MovieObj> movies = new ArrayList<MovieObj>();

	PopCineDataSource database;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_popcine);
		setBehindContentView(R.layout.activity_popcine_back);

		SlidingMenu menu = getSlidingMenu();
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);

		view = (ListView) findViewById(R.id.list);

		/*
		 * String[] de = { "imagem", "name", "show_times", "tipo", "is3d" };
		 * int[] para = { R.id.cartaz, R.id.nome_filme, R.id.horarios,
		 * R.id.tipo, R.id.is3dmovie }; adapter = new SimpleAdapter(this,
		 * moviesList, R.layout.itemlist, de, para); adapter.setViewBinder(new
		 * MovieViewBinder()); view.setAdapter(adapter);
		 */
		/*
		 * view.setOnItemClickListener(this); view.setDivider(null);
		 * view.setBackgroundColor(0xcccccccc);
		 * 
		 * ActionBar actionBar = getActionBar();
		 * actionBar.setCustomView(R.layout.topbar); // load your layout
		 * actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
		 * ActionBar.DISPLAY_SHOW_CUSTOM);
		 * 
		 * cityname = (TextView) findViewById(R.id.cityname);
		 */

		// startService(new Intent(this, PopCineService.class));

		database = new PopCineDataSource(this);
	}

	@Override
	protected void onResume() {
		database.open();
		database.testMovie();
		// updateListView();
		super.onResume();
	}

	@Override
	protected void onPause() {
		database.close();
		super.onPause();
	}

	/**
	 * Novo view binder pra setar o cartaz com drawable e informar se a sessão é
	 * 3D ou nops
	 * 
	 */
	private class MovieViewBinder implements ViewBinder {
		@Override
		public boolean setViewValue(View view, Object data, String textRepresentation) {
			if (view.getId() == R.id.cartaz) {
				ImageView iv = (ImageView) view;
				iv.setImageDrawable((Drawable) data);
				return true;
			}
			if (view.getId() == R.id.is3dmovie) {
				TextView tv = (TextView) view;
				if (!data.equals("3d")) {
					tv.setVisibility(View.GONE);
				} else {
					tv.setVisibility(View.VISIBLE);
				}
			}
			return false;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Map<String, Object> map = moviesList.get(position);
		String destino = (String) map.get("name");
		String mensagem = "Filme selecionado: " + destino;
		Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
		startActivity(new Intent(this, MovieActivity.class));
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

	private List<Map<String, Object>> listMovies() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.menu_refresh) {
			updateListView();
		}
		return super.onOptionsItemSelected(item);
	}

	private void updateListView() {
		if (moviesList == null) {
			moviesList = new ArrayList<Map<String, Object>>();
		}
		moviesList.clear();
		moviesList.addAll(listMovies());
		adapter.notifyDataSetChanged();
	}
}
