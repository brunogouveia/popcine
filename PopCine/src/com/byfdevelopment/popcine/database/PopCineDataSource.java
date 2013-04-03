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
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.method.MovementMethod;
import android.util.Log;

import com.byfdevelopment.popcine.database.PopCineContracts.Movie;
import com.byfdevelopment.popcine.database.PopCineContracts.ShowTime;
import com.byfdevelopment.popcine.database.PopCineContracts.Theater;
import com.byfdevelopment.popcine.database.PopCineContracts.Theater_Movie;
import com.byfdevelopment.popcine.objects.MovieObj;

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
		database.execSQL("PRAGMA foreign_keys=ON;");
	}

	public void close() {
		helper.close();
	}

	public void testMovie() {
		ContentValues values = new ContentValues();
		values.put(Theater.NAME, "Cinépolis");
		values.put(Theater.CITY, "Sao Paulo");
		values.put(Theater.INFO, "Av Brasil");

		try {
			database.insert(Theater.TABLE_NAME, null, values);
		} catch (SQLiteConstraintException e) {
			Log.w("SQLiteConstraintException", "Errinho de leve");
		}

		values = new ContentValues();
		values.put(Theater.NAME, "Cinemark");
		values.put(Theater.CITY, "Campo Grande");
		values.put(Theater.INFO, "Av Teste");
		try {
			database.insert(Theater.TABLE_NAME, null, values);
		} catch (SQLiteConstraintException e) {
			Log.w("SQLiteConstraintException", "Errinho de leve");
		}

		String[] strings = { "Campo Grande", "Cinépolis" };
		System.out.println(strings.length);
		database.delete(Theater.TABLE_NAME, Theater.NAME + " = ? AND " + Theater.CITY + " = ?", strings);

		ContentValues values2 = new ContentValues();
		values2.put(Movie.NAME, "A onda");
		values2.put(Movie.INFO, "Filme sobre ondas");
		values2.put(Movie.SYNOPSIS, "Era uma vez na casa de praia");

		try {
			if (database.insert(Movie.TABLE_NAME, null, values2) == -1)
				Log.w("Movie Inser", "Nao inseriu");
		} catch (SQLiteConstraintException e) {
			Log.w("SQLiteConstraintException", "Errinho de leve");
		}

		values2 = new ContentValues();
		values2.put(Movie.NAME, "A ondassom");
		values2.put(Movie.INFO, "Filme sobre");
		values2.put(Movie.SYNOPSIS, "Era uma vez na casa de praia da ondassom");

		try {
			if (database.insert(Movie.TABLE_NAME, null, values2) == -1)
				Log.w("Movie Inser", "Nao inseriu");
		} catch (SQLiteConstraintException e) {
			Log.w("SQLiteConstraintException", "Errinho de leve");
		}
		String[] strings2 = { "A ondas" };
		database.delete(Movie.TABLE_NAME, Movie.NAME + " = ?", strings2);

		/************************************************/
		values2 = new ContentValues();
		values2.put(Theater_Movie.THEATER_FK, 1);
		values2.put(Theater_Movie.MOVIE_FK, "A onda");
		values2.put(Theater_Movie.IS_3D, true);
		values2.put(Theater_Movie.TYPE, 0);

		try {
			if (database.insert(Theater_Movie.TABLE_NAME, null, values2) == -1)
				Log.w("Movie Inser", "Nao inseriu");
		} catch (SQLiteConstraintException e) {
			Log.w("SQLiteConstraintException", "Errinho de leve");
		}
		String[] strings3 = { "Cinemark" };

		/************************************************/
		values2 = new ContentValues();
		values2.put(ShowTime.FK, 4);
		values2.put(ShowTime.TIME, "12:30");
		values2.put(ShowTime.DATE, "12/11/2013");
		values2.put(ShowTime.TICKET, "ingresso.com");

		try {
			if (database.insert(ShowTime.TABLE_NAME, null, values2) == -1)
				Log.w("Movie Inser", "Nao inseriu");
		} catch (SQLiteConstraintException e) {
			Log.w("SQLiteConstraintException", "Errinho de leve");
		}
		//database.delete(Theater.TABLE_NAME, Theater.NAME + " = ?", strings3);

		Log.w("Teste", "VAMOS TESTAR!");
		printAllTheaters();
		printAllMoves();
		printAllTheater_Moves();
		printAllShowtimes();
	}

	public void printAllTheaters() {
		Cursor cursor = database.rawQuery("SELECT * FROM " + Theater.TABLE_NAME, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Log.w("Theater Id", String.valueOf(cursor.getInt(cursor.getColumnIndex(Theater.ID))));
			Log.w("Theater Name", cursor.getString(cursor.getColumnIndex(Theater.NAME)));
			Log.w("Theater City", cursor.getString(cursor.getColumnIndex(Theater.CITY)));
			Log.w("Theater Info", cursor.getString(cursor.getColumnIndex(Theater.INFO)));
		}
		cursor.close();
	}

	public void printAllMoves() {
		Cursor cursor = database.rawQuery("SELECT * FROM " + Movie.TABLE_NAME, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Log.w("Movie Id", String.valueOf(cursor.getInt(cursor.getColumnIndex(Movie.ID))));
			Log.w("Movie Name", cursor.getString(cursor.getColumnIndex(Movie.NAME)));
			Log.w("Movie info", cursor.getString(cursor.getColumnIndex(Movie.INFO)));
			Log.w("Movie synop", cursor.getString(cursor.getColumnIndex(Movie.SYNOPSIS)));
		}
		cursor.close();
	}

	public void printAllTheater_Moves() {
		Cursor cursor = database.rawQuery("SELECT * FROM " + Theater_Movie.TABLE_NAME, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Log.w("Theater_Movie Id", String.valueOf(cursor.getInt(cursor.getColumnIndex(Theater_Movie.ID))));
			Log.w("Theater_Movie TheaterFk", cursor.getString(cursor.getColumnIndex(Theater_Movie.THEATER_FK)));
			Log.w("Theater_Movie MovieFk", cursor.getString(cursor.getColumnIndex(Theater_Movie.MOVIE_FK)));
			Log.w("Theater_Movie IS_3d", String.valueOf(cursor.getInt(cursor.getColumnIndex(Theater_Movie.IS_3D))));
			Log.w("Theater_Movie Type", String.valueOf(cursor.getInt(cursor.getColumnIndex(Theater_Movie.TYPE))));
		}
		cursor.close();
	}

	public void printAllShowtimes() {
		Cursor cursor = database.rawQuery("SELECT * FROM " + ShowTime.TABLE_NAME, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Log.w("Showtimes Id", String.valueOf(cursor.getInt(cursor.getColumnIndex(ShowTime.ID))));
			Log.w("Showtimes FK", String.valueOf(cursor.getInt(cursor.getColumnIndex(ShowTime.FK))));
			Log.w("Showtimes time", cursor.getString(cursor.getColumnIndex(ShowTime.TIME)));
			Log.w("Showtimes date", cursor.getString(cursor.getColumnIndex(ShowTime.DATE)));
			Log.w("Showtimes ticket", cursor.getString(cursor.getColumnIndex(ShowTime.TICKET)));
		}
		cursor.close();
	}
}
