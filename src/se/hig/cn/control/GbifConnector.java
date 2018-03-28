package se.hig.cn.control;

import se.hig.cn.model.Point;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class GbifConnector {
	private String dataKey = "results";
	private URL url;
	private List<Point> pointList = new ArrayList<>();

	public void connect() {
		try {
			url = new URL("http://api.gbif.org/v1/occurrence/search?taxon_key=4409131&limit=200&country=SE");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		try (InputStream is = url.openStream(); JsonReader rdr = Json.createReader(is)) {
			JsonObject obj = rdr.readObject();
			JsonArray results = obj.getJsonArray(dataKey);
			for (JsonObject result : results.getValuesAs(JsonObject.class)) {
				pointList.add(new Point(result.getJsonNumber("decimalLongitude"),
						result.getJsonNumber("decimalLatitude"), result.getString("county")));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public List<Point> getPointList() {
		return pointList;
	}

}
