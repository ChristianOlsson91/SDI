package se.hig.cn.control;

import se.hig.cn.model.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class CTestDriver {

	Connection con;

	private static void doSshTunnel(String strSshUser, String strSshPassword, String strSshHost, int nSshPort,
			String strRemoteHost, int nLocalPort, int nRemotePort) throws JSchException {
		final JSch jsch = new JSch();
		Session session = jsch.getSession(strSshUser, strSshHost, 22);
		session.setPassword(strSshPassword);

		final Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);

		session.connect();
		session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
	}

	public void connectToDatabase() {
		try {
			String strSshUser = "tfk12coo"; // SSH loging username
			String strSshPassword = "GIS4ever"; // SSH login password
			String strSshHost = "rigel.hig.se"; // hostname or ip or SSH server
			int nSshPort = 22; // remote SSH host port number
			String strRemoteHost = "127.0.0.1"; // hostname or ip of your database server
			int nLocalPort = 3366; // local port number use to bind SSH tunnel
			int nRemotePort = 5433; // remote port number of your database
			String strDbUser = "tfk12coo"; // database loging username
			String strDbPassword = "GIS4ever"; // database login password

			CTestDriver.doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort,
					nRemotePort);

			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:" + nLocalPort + "/Grupp_cn",
					strDbUser, strDbPassword);

			Statement statement = con.createStatement();

			GbifConnector gbif = new GbifConnector();
			gbif.connect();

			URL url = new URL(
					"http://api.gbif.org/v1/occurrence/search?taxon_key=4409131&limit=200&country=SE&WFS=GetFeature");
			BufferedReader buffer = new BufferedReader(new InputStreamReader(url.openStream()));
			File file = new File("H:/workspace/SDIProject/GetFeatureFile.gml");
			FileWriter writer = new FileWriter(file);
			file.createNewFile();

			String input = "";

			while ((input = buffer.readLine()) != null)
				writer.write(input);

			writer.close();
			buffer.close();

		/*	int id = 11;
			for (int i = 0; i < gbif.getPointList().size(); i++) {
				statement.executeUpdate("INSERT INTO animals (id, longitud, latitud) VALUES ('" + id + "','"
						+ gbif.getPointList().get(i).getLongitude() + "','" + gbif.getPointList().get(i).getLatitude()
						+ "')");
				id++;
			}*/

			//statement.executeUpdate("UPDATE animals set coordinates= ST_SetSRID(ST_MakePoint(latitud,longitud),4326)");

			ResultSet result = statement.executeQuery("SELECT * FROM animals");

			while (result.next()) {
				System.out.format("Post %3d: (%12.4f,%12.4f) %s%n", result.getInt(1), result.getDouble(2),
						result.getDouble(3), result.getString(4));
			}

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
}
