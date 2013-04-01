package com.byfdevelopment.popcine.objects;

import java.util.ArrayList;
import java.util.List;

public class Theater {

	private String name;
	private String info;

	private List<Movie> movies;

	public Theater(String name, String info) {
		this.name = name;
		this.info = info;
		movies = new ArrayList<Movie>();
	}

	public void add(Movie movie) {
		movies.add(movie);
	}

	public String toString() {
		String string = name + " " + info + "\n";

		for (Movie movie : movies) {
			string = string.concat(movie.toString() + "\n");
		}
		return string;
	}
}
