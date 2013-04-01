package com.byfdevelopment.popcine.objects;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.graphics.drawable.Drawable;

public class Movie {
	public String name;
	public String info;
	public int type; // 0 - dublado, 1 - Legendado, 2 - Original (filme
						// nacional)
	public boolean is3D;
	public Drawable poster;

	Map<String, String> showTimes;

	public Movie(String name, String info) {
		this.name = name;
		this.info = info;
		if (name.toLowerCase(Locale.US).contains("dublado")) {
			this.name = this.name.replaceAll(" -[A-Za-z0-9 ]*Dublado", "");
			type = 0;
		} else if (name.toLowerCase(Locale.US).contains("legendado")) {
			this.name = this.name.replaceAll(" -[A-Za-z0-9 ]*Legendado", "");
			type = 1;
		} else {
			type = 2;
		}

		is3D = name.toLowerCase(Locale.US).contains("3d");
		showTimes = new HashMap<String, String>();
	}

	public void add(String showTime, String href) {
		showTimes.put(showTime, href);
	}

	public Map<String, String> getShowTimes() {
		return showTimes;
	}

	public String getShowTimesAsString() {
		String string = "";
		Set<String> keys = showTimes.keySet();
		for (String key : keys) {
			string = string.concat(key + " ");
		}
		return string;
	}

	public String toString() {
		String string = "";
		Set<String> keys = showTimes.keySet();
		for (String key : keys) {
			string = string.concat(key + "\n");
		}

		return string;
	}

	public static Comparator<Movie> comparator = new Comparator<Movie>() {

		@Override
		public int compare(Movie lhs, Movie rhs) {
			return lhs.name.compareToIgnoreCase(rhs.name);
		}
	};

	static public String trata(String passa) {
		passa = passa.replaceAll("[ÂÀÁÄÃ]", "A");
		passa = passa.replaceAll("[âãàáä]", "a");
		passa = passa.replaceAll("[ÊÈÉË]", "E");
		passa = passa.replaceAll("[êèéë]", "e");
		passa = passa.replaceAll("ÎÍÌÏ", "I");
		passa = passa.replaceAll("îíìï", "i");
		passa = passa.replaceAll("[ÔÕÒÓÖ]", "O");
		passa = passa.replaceAll("[ôõòóö]", "o");
		passa = passa.replaceAll("[ÛÙÚÜ]", "U");
		passa = passa.replaceAll("[ûúùü]", "u");
		passa = passa.replaceAll("Ç", "C");
		passa = passa.replaceAll("ç", "c");
		passa = passa.replaceAll("[ýÿ]", "y");
		passa = passa.replaceAll("Ý", "Y");
		passa = passa.replaceAll("ñ", "n");
		passa = passa.replaceAll("Ñ", "N");
		return passa;
	}
}
