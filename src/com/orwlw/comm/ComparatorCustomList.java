package com.orwlw.comm;

import java.util.Comparator;
import java.util.Map;
import java.lang.*;

public class ComparatorCustomList implements Comparator {

	public int compare(Object arg0, Object arg1) {
		Map<String, Object> custom1 = (Map<String, Object>) arg0;
		Map<String, Object> custom2 = (Map<String, Object>) arg1;

		try {
			if (Double.parseDouble(custom1.get("Distance") + "") < Double
					.parseDouble(custom2.get("Distance") + "")) {
				return -1;
			} else if (Double.parseDouble(custom1.get("Distance") + "") == Double
					.parseDouble(custom2.get("Distance") + "")) {
				return 0;
			} else {
				return 1;
			}
		} catch (Exception e) {
			return 0;
		}

	}
}
