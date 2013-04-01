package com.byfdevelopment.popcine.objects;

import java.util.Comparator;

public class ShowTime {

	private int hour;
	private int minutes;

	public ShowTime(String hour, String minutes) {
		this.hour = Integer.parseInt(hour);
		this.minutes = Integer.parseInt(minutes);
	}

	public String toString() {
		return String.format("%02d:%02d", hour, minutes);
	}

	public static Comparator<ShowTime> comparator = new Comparator<ShowTime>() {

		@Override
		public int compare(ShowTime lhs, ShowTime rhs) {
			// TODO Auto-generated method stub
			if (lhs.hour < rhs.hour) {
				return -1;
			} else if (lhs.hour < rhs.hour) {
				return 1;
			} else {
				if (lhs.minutes < rhs.minutes) {
					return -1;
				} else if (lhs.minutes > rhs.minutes) {
					return 1;
				} else {
					return 0;
				}
			}
		}
	};

}
