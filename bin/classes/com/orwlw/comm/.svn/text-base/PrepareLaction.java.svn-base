package com.orwlw.comm;

import java.sql.Date;

public class PrepareLaction {

	private static double EARTH_RADIUS = 6378137;// 赤道半径为6378.140千米,平均半径6371.004千米
	private static double mile = 0.621371192;// 1千米=0.621371192mile

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	public static double GetDistance(double lat1, double lng1, double lat2,
			double lng2) {
		double radLat1 = lat1 * Math.PI / 180.0;
		double radLat2 = lat2 * Math.PI / 180.0;
		double a = radLat1 - radLat2;
		double b = (lng1 * Math.PI / 180.0) - (lng2 * Math.PI / 180.0);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * 6378137;
		s = Math.round(s * 10000) / 10000;
		return s;

		// double radLat1 = rad(lat1);
		// double radLat2 = rad(lat2);
		// double a = radLat1 - radLat2;
		// double b = rad(lng1) - rad(lng2);
		// double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
		// Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2),
		// 2)));
		// s = s * EARTH_RADIUS * mile;
		// s = Math.round(s * 10000) / 10000;
		// return s;
	}

	public static Long Getlongtime(Date date1, Date date2) {
		long beginTime = date1.getTime();
		long endTime = date2.getTime();
		// Double double1 = Double.parseDouble((endTime - beginTime) + "");
		return (endTime - beginTime) / 1000;
	}

	public static boolean prepare(double lat1, double lng1, double lat2,
			double lng2, Date date1, Date date2) {
		Double luchengDouble = GetDistance(lat1, lng1, lat2, lng2);
		Long timeLong = Getlongtime(date1, date2);
		Double paceLong = luchengDouble / Double.parseDouble(timeLong + "");
		if (paceLong > 20) {
			return false;
		} else {
			return true;
		}

	}
}
