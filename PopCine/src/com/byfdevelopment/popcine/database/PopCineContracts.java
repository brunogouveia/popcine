package com.byfdevelopment.popcine.database;

import java.net.IDN;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PopCineContracts {
	public static class Theater {
		public static String TABLE_NAME = "Theater";

		public static String ID = "_id";
		public static String NAME = "Name";
		public static String CITY = "City";
		public static String INFO = "Info";

		public static String[] COLUMNS = { NAME, INFO };

		private static String PRIMARY_KEY = NAME + ", " + CITY;

		public static void createTable(SQLiteDatabase database) {
			database.execSQL(

			"CREATE TABLE " + TABLE_NAME + " (" +

			ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

			NAME + " TEXT NOT NULL," +

			CITY + " TEXT NOT NULL," +

			INFO + " TEXT NOT NULL," +

			"UNIQUE (" + NAME + "," + CITY + ") ON CONFLICT FAIL);");
		}

		private Theater() {
		}
	}

	public static class Movie {
		public static String TABLE_NAME = "Movies";

		/* PK */
		public static String ID = "_id";
		/* Attributes */
		public static String NAME = "_id";
		public static String INFO = "Info";
		public static String POSTER = "Poster";
		public static String SYNOPSIS = "Synopsis";

		public static String[] COLUMNS = { NAME, INFO };

		private static String PRIMARY_KEY = NAME + ", " + INFO;

		public static void createTable(SQLiteDatabase database) {
			database.execSQL(

			"CREATE TABLE " + TABLE_NAME + " (" +

			NAME + " TEXT NOT NULL PRIMARY KEY," +

			INFO + " TEXT NOT NULL," +

			SYNOPSIS + " TEXT" +

			/*"CONSTRAINT " + ID + " PRIMARY KEY(" + NAME +*/ ");");
		}

		private Movie() {
		}
	}

	public static class Theater_Movie {
		public static String TABLE_NAME = "Theater_Movies";

		public static String ID = "_id";
		public static String THEATER_FK = "Theater_FK";
		public static String MOVIE_FK = "Movie_FK";
		public static String TYPE = "Type";
		public static String IS_3D = "Is_3D";

		public static String[] COLUMNS = { THEATER_FK, MOVIE_FK, TYPE, IS_3D };

		public static void createTable(SQLiteDatabase database) {
			database.execSQL(

			"CREATE TABLE " + TABLE_NAME + " (" +

			ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

			THEATER_FK + " INTEGER NOT NULL," +

			MOVIE_FK + " TEXT NOT NULL," +

			TYPE + " INTEGER NOT NULL," +

			IS_3D + " INTEGER NOT NULL," +

			"FOREIGN KEY (" + THEATER_FK + ") REFERENCES " + Theater.TABLE_NAME + "(" + Theater.ID + ") ON UPDATE CASCADE ON DELETE CASCADE," +

			"FOREIGN KEY (" + MOVIE_FK + ") REFERENCES " + Movie.TABLE_NAME + "(" + Movie.NAME + ") ON UPDATE CASCADE ON DELETE CASCADE," +

			"UNIQUE (" + THEATER_FK + "," + MOVIE_FK + "," + TYPE + "," + IS_3D + ") ON CONFLICT FAIL);");
		}

		private Theater_Movie() {
		}
	}

	public static class ShowTime {
		public static String TABLE_NAME = "ShowTime";

		public static String ID = "_id";
		public static String FK = "FK";
		public static String TIME = "Time";
		public static String DATE = "Date";
		public static String TICKET = "Ticket";

		public static String[] COLUMNS = { TIME, DATE, TICKET };

		public static void createTable(SQLiteDatabase database) {
			database.execSQL(

			"CREATE TABLE " + TABLE_NAME + " (" +

			ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

			FK + " INTEGER NOT NULL," +

			DATE + " TEXT NOT NULL," +

			TIME + " TEXT NOT NULL," +

			TICKET + " TEXT NOT NULL," +

			"UNIQUE (" + DATE + "," + TIME + "," + TICKET + ")," +

			"FOREIGN KEY (" + FK + ") REFERENCES " + Theater_Movie.TABLE_NAME + "(" + Theater_Movie.ID + ") ON UPDATE CASCADE ON DELETE CASCADE);");
		}

		private ShowTime() {
		}

	}

}
