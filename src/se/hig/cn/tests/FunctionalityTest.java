package se.hig.cn.tests;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FunctionalityTest {

	public FunctionalityTest() throws IOException {
		JFrame frame = new JFrame();
		URL catalog = new URL(
				"http://rigel.hig.se:8080/geoserver/sweden_gruppCN/wms?STYLES=&LAYERS=sweden_gruppCN%3Aanimals&FORMAT=image%2Fpng&SERVICE=WMS&VERSION=&REQUEST=GetMap&SRS=EPSG%3A4326&BBOX=56.681806701172,8.9476025,60.773519298828,20.9187845&WIDTH=175&HEIGHT=512");

		BufferedImage bf = ImageIO.read(catalog);
		JLabel label = new JLabel(new ImageIcon(bf));
		frame.add(label);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(800, 500, 500, 500);
		frame.setVisible(true);
	}
}