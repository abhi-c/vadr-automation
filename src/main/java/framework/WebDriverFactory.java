package framework;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import framework.Settings;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {
	public static FrameworkParameters parameters = FrameworkParameters.getInstance();
	private static Properties properties;
	public static WebDriver driver;
	private static boolean initilized = false;
	
	
	public WebDriverFactory(){
		if (!initilized) {
			SharedDriver();
			initilized = true;
		}
	}
	
	public WebDriver getDriver(){
		return driver;
	}

	private static void SharedDriver() {
		// TODO Auto-generated method stub
		setRelativePath();
		properties = Settings.getProperties();
		String driverDir = properties.getProperty("Browser");
		if (driverDir.equalsIgnoreCase("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("headless", "disabled-gpu");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(options);

//			System.setProperty("webdriver.chrome.driver", FrameworkParameters.getInstance().getRelativePath()+"\\drivers\\chromedriver.exe");
//			driver = new ChromeDriver();
		}
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	private static void setRelativePath() {
		// TODO Auto-generated method stub
		String relativePath = new File(System.getProperty("user.dir")).getAbsolutePath();
		if (relativePath.contains("allocator")) {
			relativePath = new File(System.getProperty("user.dir")).getParent();
		}
		parameters.setRelativePath(relativePath);
	}

}
