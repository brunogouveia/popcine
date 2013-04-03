package com.byfdevelopment.popcine.objects;

import java.util.Comparator;

public class ShowTimeObj {

	/* Escolha a melhor maneira de colocar esses atributos aqui... */
	private int hour;
	private int minutes;
	private String date;
	private boolean is3D;

	/* No construtor voce pode escolher a melhor maneira de fazer isso */
	public ShowTimeObj(String hour, String minutes, String date) {
		this.hour = Integer.parseInt(hour);
		this.minutes = Integer.parseInt(minutes);
		this.date = date;
	}

}
