package se.hig.cn.control;

import java.io.IOException;
import java.sql.SQLException;

import se.hig.cn.tests.AvailabilityTest;
import se.hig.cn.tests.FunctionalityTest;

public class Main {

	public static void main(String[] args) throws SQLException, IOException, InterruptedException {
		//CTestDriver driver = new CTestDriver();
		//driver.connectToDatabase();

		AvailabilityTest atest = new AvailabilityTest();
		atest.beaverCon();

	//	FunctionalityTest ftest = new FunctionalityTest();
	}
}