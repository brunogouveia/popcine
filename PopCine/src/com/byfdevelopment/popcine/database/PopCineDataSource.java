package com.byfdevelopment.popcine.database;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.byfdevelopment.popcine.database.PopCineContracts.Movies;
import com.byfdevelopment.popcine.objects.Movie;

public class PopCineDataSource {
	DataBaseHelper helper;
	SQLiteDatabase database;
	Context context;

	public PopCineDataSource(Context context) {
		this.context = context;
		helper = new DataBaseHelper(context);
	}

	public void open() throws SQLException {
		database = helper.getWritableDatabase();
	}

	public void close() {
		helper.close();
	}

	public void insertMovie(Movie movie) {
		ContentValues values = new ContentValues();
		values.put(Movies.NAME, movie.name);
		values.put(Movies.INFO, movie.info);
		values.put(Movies.TYPE, movie.type);
		values.put(Movies.IS_3D, movie.is3D);

		Log.w("Database", movie.name + " Inserido");

		if (movie.poster != null) {
			BitmapDrawable s = (BitmapDrawable) movie.poster;
			Bitmap bit = s.getBitmap();
			ByteArrayOutputStream arraystream = new ByteArrayOutputStream();
			bit.compress(Bitmap.CompressFormat.JPEG, 100, arraystream);
			try {
				FileOutputStream outputfile = new FileOutputStream(new File(context.getExternalFilesDir(null), movie.name.replaceAll("[:/]", "_") + ".jpg"));
				outputfile.write(arraystream.toByteArray());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String selection = String.format("%s == ? AND %s == ? AND %s == ? AND %s == ?", Movies.NAME, Movies.INFO, Movies.TYPE, Movies.IS_3D);
		String[] selectionArgs = { movie.name, movie.info, Integer.valueOf(movie.type).toString(), Integer.valueOf((movie.is3D) ? 1 : 0).toString() };
		Cursor cursor = database.query(Movies.TABLE_NAME, Movies.COLUMNS, selection, selectionArgs, null, null, null);

		if (cursor.getCount() == 0) {
			database.insert(Movies.TABLE_NAME, null, values);
		}
		cursor.close();

	}

	public List<Movie> getMovies() {
		List<Movie> list = new ArrayList<Movie>();

		// Cursor cursor = database.rawQuery("SELECT * FROM " +
		// Movies.TABLE_NAME, null);
		Cursor cursor = database.query(Movies.TABLE_NAME, Movies.COLUMNS, null, null, null, null, Movies.NAME + " ASC");

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex(Movies.NAME));
			String info = cursor.getString(cursor.getColumnIndex(Movies.INFO));
			int type = cursor.getInt(cursor.getColumnIndex(Movies.TYPE));
			int is3D = cursor.getInt(cursor.getColumnIndex(Movies.IS_3D));

			Movie movie = new Movie(name, info);
			movie.type = type;
			movie.is3D = (is3D == 1) ? true : false;

			Log.w("Database", movie.name + " retornado");

			movie.poster = null;
			try {
				FileInputStream inputStream = new FileInputStream(new File(context.getExternalFilesDir(null), movie.name.replaceAll("[:/]", "_") + ".jpg"));
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
				BitmapDrawable poster = new BitmapDrawable(context.getResources(), bitmap);
				movie.poster = poster;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			list.add(movie);
		}
		cursor.close();
		return list;
	}
}
