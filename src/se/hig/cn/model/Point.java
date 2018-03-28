package se.hig.cn.model;

import javax.json.JsonNumber;

public class Point {
	private JsonNumber latitude;
	private JsonNumber longitude;
	private String county;

	public Point(JsonNumber longitude, JsonNumber latitude, String county) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.county = county;
	}

	public JsonNumber getLatitude() {
		return latitude;
	}

	public JsonNumber getLongitude() {
		return longitude;
	}

	public String getCounty() {
		return county;
	}
}