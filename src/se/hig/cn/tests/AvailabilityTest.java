package se.hig.cn.tests;

import java.sql.SQLException;
import java.net.*;
import java.io.IOException;

public class AvailabilityTest {

	public void beaverCon() throws SQLException, IOException, InterruptedException {
		int successfulcount = 0;
		int failedcount = 0;
		long before;
		long after;

		URL catalog = new URL(
				"http://rigel.hig.se:8080/geoserver/sweden_gruppCN/wms?service=WMS&version=1.0.0&request=GetMap&layers=sweden_gruppCN:animals&styles=&bbox=57.703743,11.940398,59.751583,17.925989&width=175&height=512&srs=EPSG:4326&format=application/openlayers");

		URLConnection uc = catalog.openConnection();
		int responseCode;
		HttpURLConnection httpConnection = (HttpURLConnection) catalog.openConnection();

		before = System.currentTimeMillis();
		for (int i = 0; i < 6000; i++) {
			Thread.sleep(10);

			uc = catalog.openConnection();

			responseCode = httpConnection.getResponseCode();

			if (responseCode > 199 && responseCode < 227) {

				successfulcount++;
			} else if (responseCode > 399 && responseCode < 452) {

				failedcount++;
			}
		}
		after = System.currentTimeMillis();
		System.out.println("Tid för test: " + (after - before));

		System.out.println("Successful: " + successfulcount + "\nFailed: " + failedcount);
	}
}