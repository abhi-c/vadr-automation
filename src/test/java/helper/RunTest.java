package helper;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.ExtentCucumberFormatter;
import com.relevantcodes.extentreports.NetworkMode;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import framework.AADriver;
import framework.WebDriverFactory;
import org.openqa.selenium.WebDriver;

@CucumberOptions(
		features = {"src/test/features"},
		plugin = {"com.cucumber.listener.ExtentCucumberFormatter:output/report.html"},
		tags={"@Test"},
		glue={"glue"}
)

@RunWith(Cucumber.class)
public class RunTest {
	private static WebDriverFactory driverFactory;

	public static String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	public static String ResultFileFolder="Run_"+timeStamp;
	public static String ResultFileName="output/"+ResultFileFolder+"/Report.html";


	@BeforeClass
	public static void setup(){
		File reportFile = new File(ResultFileName);
		ExtentCucumberFormatter.initiateExtentCucumberFormatter(reportFile, NetworkMode.OFFLINE);
		ExtentCucumberFormatter.loadConfig(new File("src/main/resources/extent-config.xml"));
		driverFactory = new WebDriverFactory();
	}

	@AfterClass
	public static void close(){
		try {
			AADriver driver = new AADriver(driverFactory.getDriver());
			WebDriver webDriver = driverFactory.getDriver();
			webDriver.close();
			webDriver.quit();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
