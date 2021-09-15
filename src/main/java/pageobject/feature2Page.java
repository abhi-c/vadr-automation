package pageobject;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import framework.AADriver;

import java.lang.reflect.Field;
import org.openqa.selenium.WebElement;
import static org.junit.Assert.assertEquals;
//<IMPORTS>

public class feature2Page {

public static WebDriver driver;
AADriver myDriver;
public static Properties properties;


/*Xpaths*/
public static By btnSubmit = By.xpath("(//input[contains(@value, 'Google')])[2]");
public static By searchBox = By.xpath("//input[@title='Search']");
public static By NameText = By.xpath("//input[@name='name']");

public static By EmailText = By.xpath("//input[@name='email']");

public static By MessageText = By.xpath("//input[@name='message']");

//<XPATHS>

/*Page Constructor*/
public feature2Page(){

}


public feature2Page(WebDriver driver){
this.driver= driver;
myDriver = new AADriver(this.driver);
}

//<METHOD>
}
