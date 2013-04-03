package com.byfdevelopment.popcine.objects;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.drawable.Drawable;

public class MovieObj {
	public String name;
	public String info;
	public Drawable poster;

	List<TheaterObj> theaters;

	Map<String, List<ShowTimeObj>> subtitled;
	Map<String, List<ShowTimeObj>> dubbed;
	Map<String, List<ShowTimeObj>> original;

	/* Constantes */
	public final static int TODAY = 2;
	public final static int TOMORROW = 4;
	public final static int AFTER_TOMORROW = 8;
	public final static int EVERY_DAY = TODAY + TOMORROW + AFTER_TOMORROW;

	public final static int TIME_BEFORE_NOW = 1;
	public final static int TIME_AFTER_NOW = 2;
	public final static int TIME_ALL = TIME_BEFORE_NOW + TIME_AFTER_NOW;

	public MovieObj(String name, String info) {
		this.name = name;
		this.info = info;
		subtitled = new HashMap<String, List<ShowTimeObj>>();
		dubbed = new HashMap<String, List<ShowTimeObj>>();
		original = new HashMap<String, List<ShowTimeObj>>();
	}

	/**
	 * 
	 * days pode ser qualuqer constante EVERY_DAY, TODAY, TOMORROW,
	 * AFTER_TOMORROW
	 * 
	 * @param Os
	 *            dias que voce quer que retorne
	 * @return Retorna se tem filmes legendados nesses dias
	 */
	public boolean hasSubtitled(int days) {
		return false;
	}

	/**
	 * 
	 * days pode ser qualuqer constante EVERY_DAY, TODAY, TOMORROW,
	 * AFTER_TOMORROW
	 * 
	 * @param days
	 * @return
	 */
	public List<ShowTimeObj> getSubtitleds(int days) {
		return getSubtitleds(TIME_ALL);
	}

	/**
	 * 
	 * days pode ser qualuqer constante EVERY_DAY, TODAY, TOMORROW,
	 * AFTER_TOMORROW
	 * 
	 * @param days
	 * @return
	 */
	public List<ShowTimeObj> getSubtitleds(int days, int time) {
		return null;
	}

	public static Comparator<MovieObj> comparator = new Comparator<MovieObj>() {

		@Override
		public int compare(MovieObj lhs, MovieObj rhs) {
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
