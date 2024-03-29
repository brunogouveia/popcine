package com.byfdevelopment.popcine.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.byfdevelopment.popcine.database.PopCineContracts.Movie;
import com.byfdevelopment.popcine.database.PopCineContracts.ShowTime;
import com.byfdevelopment.popcine.database.PopCineContracts.Theater;
import com.byfdevelopment.popcine.database.PopCineContracts.Theater_Movie;

public class DataBaseHelper extends SQLiteOpenHelper {

	private static String DATABASE_NAME = "popcine.db";
	private static int DATABASE_VERSION = 1;

	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("PRAGMA foreign_keys=ON;");
		Theater.createTable(db);
		Movie.createTable(db);
		Theater_Movie.createTable(db);
		ShowTime.createTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
