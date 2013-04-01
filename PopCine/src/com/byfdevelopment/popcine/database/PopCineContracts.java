package com.byfdevelopment.popcine.database;

import android.database.sqlite.SQLiteDatabase;

public class PopCineContracts {
	public static class Movies {
		public static String TABLE_NAME = "Movies";

		public static String ID = "_id";
		public static String NAME = "Name";
		public static String INFO = "Info";
		public static String TYPE = "Type";
		public static String IS_3D = "Is_3D";
		public static String POSTER = "Poster";
		public static String SYNOPSIS = "Synopsis";

		public static String[] COLUMNS = { NAME, INFO, TYPE, IS_3D };

		private static String PRIMARY_KEY = NAME + ", " + INFO + ", " + TYPE + ", " + IS_3D;

		public static void createTable(SQLiteDatabase database) {
			database.execSQL(

			"CREATE TABLE " + TABLE_NAME + " (" +

			NAME + " TEXT NOT NULL," +

			INFO + " TEXT NOT NULL," +

			TYPE + " INTEGER NOT NULL," +

			IS_3D + " INTEGER NOT NULL," +

			SYNOPSIS + " TEXT," +

			"CONSTRAINT " + ID + " PRIMARY KEY(" + PRIMARY_KEY + "));");
		}
	}
}
