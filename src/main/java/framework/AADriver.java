package framework;

import au.com.bytecode.opencsv.CSVWriter;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
//import java.util.Properties;

/**
 * @author Created by Mehraj on 10/26/2017.
 */
public class AADriver {
  private static Properties properties;
  private WebDriver driver;
  private WebDriverFactory contextSteps;
  private JavascriptExecutor jse;
  private WebDriverWait wait;
  private WebElement object;
  public static int TCID;
  public  AADriver(WebDriver driver) {
    this.driver = driver;
    jse=(JavascriptExecutor)driver;
    wait = new WebDriverWait(driver,20);
  }

  public AADriver() {

  }

  //----------------------------- Verify Object present -----------------------------

  /**
   * Verifies existence of the object with attribute Identifier=attibuteValue. Returns true of object exists.
   * @param ObjectName The object name eg: exceptionsPageTitle within <By exceptionsPageTitle = By.xpath("//p[.='Exception Summary']");>
   *
   */
  public boolean verifyObjectPresent(By ObjectName)    {

    try{
      Thread.sleep(2000);
      JavascriptExecutor je = (JavascriptExecutor) driver;
      WebElement element = driver.findElement(ObjectName);
      je.executeScript("arguments[0].scrollIntoView(false);", element);
      object= wait.until(ExpectedConditions.visibilityOfElementLocated(ObjectName));
      if (driver.findElements(ObjectName).size() > 0){return true;}
      else {return false;}
    }catch(Exception e){
      System.out.println(e);
      throw new NoSuchElementException("Error occured in verifyObjectPresent("+ObjectName.toString()+"). Message:" + e);
    }

  }

  /**
   * @author Mehraj
   * Verifies existence of the object with attribute Identifier=attibuteValue. Returns true of object exists.
   * @param buttonName The name of the button
   *
   */
  public void clickButton(String buttonName)    {
    buttonName = buttonName.toLowerCase();
    By button = By.xpath("//button[translate(@value,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz') = '"+buttonName+"'][1]");
    try {
      object = wait.until(ExpectedConditions.elementToBeClickable(button));
      object.click();
    }catch (Exception e){
      System.out.println(e);
      throw new NoSuchElementException("Error occured in clickButton("+button.toString()+"). Message:" + e);
    }

  }

  public boolean verifyButtonPresent(String buttonName){
    buttonName = buttonName.toLowerCase();
    By button = By.xpath("//button[translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz') = '"+buttonName+"'][1]");
    try {
      object = wait.until(ExpectedConditions.elementToBeClickable(button));
      if (driver.findElements(button).size() > 0){return true;}else {return false;}
    }catch (Exception e){
      System.out.println(e);
      throw new NoSuchElementException("Error occured in verifyButtonPresent("+button.toString()+"). Message:" + e);
    }

  }

  /**
   * Verifies existence of the object with attribute Identifier=attibuteValue. Returns true of object exists.
   * @param ObjectName The object name eg: exceptionsPageTitle within <By exceptionsPageTitle = By.xpath("//p[.='Exception Summary']");>
   * @param TimeOut timeout in seconds
   */
  public boolean verifyObjectPresent(By ObjectName, int TimeOut)    {
    try{
      String xpath;
      Thread.sleep(TimeOut * 1000);
      object= wait.until(ExpectedConditions.visibilityOfElementLocated(ObjectName));
      if (driver.findElements(ObjectName).size() > 0){return true;}
      else {return false;}
    }catch(Exception e){
      System.out.println(e);
      throw new NoSuchElementException("Error occured in verifyObjectPresent("+ObjectName.toString()+"). Message:" + e);
    }

  }


  /**
   * Verifies existence of the object with attributes Identifier1=Value1 and Identifier2=Value2. Returns true if object exists.
   * Implemented for Identifier1=class and Identifier2=text/containingText/id/name.
   * Can be implemented for additional identifiers and combinations using the same pattern
   * @param ObjectName  The object type eg: link, button, etc
   * @param Identifier1 The name of the first identifier to be used eg: class, id, etc
   * @param Value1      The value of the first identifier
   * @param Identifier2 The name of the second identifier to be used eg: class, id, etc
   * @param Value2      The value of the second identifier
   * @return True if object is present, False if object is not present
   */
  public boolean verifyObjectPresent(String ObjectName, String Identifier1, String Value1, String Identifier2, String Value2) {
    try {
      String xpath;
      Thread.sleep(2000);
      switch (Identifier1.toLowerCase()) {
        case "class":
          switch (Identifier2.toLowerCase()) {
            case "text":
              xpath = "//*[contains(@class,'" + Value1 + "') and text()='" + Value2 + "']";
              //  return verifyObjectPresent(ObjectName,"xpath",xpath);
              if (driver.findElements(By.xpath(xpath)).size() > 0) {
                return true;
              } else {
                return false;
              }
            case "containingText":
              xpath = "//*[contains(@class,'" + Value1 + "') and @contains(text(),'" + Value2 + "']";
              return verifyObjectPresent(ObjectName, "xpath", xpath);
            case "id":
              xpath = "//*[contains(@class,'" + Value1 + "') and @id='" + Value2 + "']";
              return verifyObjectPresent(ObjectName, "xpath", xpath);
            case "name":
              xpath = "//*[contains(@class,'" + Value1 + "') and @name='" + Value2 + "']";
              return verifyObjectPresent(ObjectName, "xpath", xpath);
            default:
              return false;
          }
        default:
          return false;
      }
    } catch (Exception e) {
      System.out.println(e);
      throw new NoSuchElementException("Error occured in verifyObjectPresent(" + ObjectName + "," + Identifier1 + "," + Value1 + "+," + Identifier2 + "+," + Value2 + "). Message:" + e);
    }
  }

  /**
   * Verifies existence of the object with attribute Identifier=attibuteValue. Returns true of object exists.
   * @param ObjectName The object type eg: link, button, etc
   * @param Identifier The name of the identifier to be used eg: class, id, etc
   * @param attibuteValue The value of the identifier
   */
  public boolean verifyObjectPresent(String ObjectName, String Identifier,String attibuteValue)    {
    try{
      String xpath;
      Thread.sleep(2000);
      switch (Identifier.toLowerCase()) {
        case "linktext":
          if (driver.findElements(By.linkText(attibuteValue)).size() > 0){return true;}
          else {return false;}
        case "title":
          object= (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@title='"+attibuteValue+"']")));
          if (driver.findElements(By.xpath("//*[@title='"+attibuteValue+"']")).size()>0){return true;}
          else {return false;}
        case "label":   //*[contains(text(),'Control Acc')]
          xpath="//*[contains(text(),'"+attibuteValue+"')]";
          object= (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
          if (driver.findElements(By.xpath(xpath)).size()>0){return true;}
          else {return false;}
        case "class":
          object= (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("."+attibuteValue)));
          if (driver.findElements(By.cssSelector("."+attibuteValue)).size()>0){return true;}
          else {return false;}
        case "text":
          xpath="//*[text()='"+attibuteValue+"']";
          object= (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
          if (driver.findElements(By.xpath(xpath)).size()>0){return true;}
          else {return false;}
        case "xpath":
          object= (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(attibuteValue)));
          Thread.sleep(1000);
          if (driver.findElements(By.xpath(attibuteValue)).size()>0){return true;}
          else {return false;}
        default:
          xpath="//*[@"+Identifier.toLowerCase()+"='"+attibuteValue+"']";
          object= (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
          if (driver.findElements(By.xpath(xpath)).size() > 0){return true;}
          else {return false;}
      }
    }catch(Exception e){
      System.out.println(e);
      throw new NoSuchElementException("Error occured in verifyObjectPresent("+ObjectName+","+attibuteValue+","+Identifier+"). Message:" + e);
    }
  }

  /**
   * Clicks on object with attribute Identifier=attibuteValue
   * @param ObjectName The object type eg: link, button, etc
   * @param identifier The name of the identifier to be used eg: class, id, etc
   * @param attributeValue The value of the identifier
   */
  public void click(String ObjectName, String identifier,String attributeValue){
    identifier=identifier.toLowerCase();
    try{
      switch(identifier) {
        case "link":
          Thread.sleep(1000);
          object= (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(attributeValue)));
          jse.executeScript("arguments[0].click();",driver.findElement(By.linkText(attributeValue)));
          //driver.findElement(By.linkText(attibuteValue)).click();
          break;
        case "id":
          object= (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(attributeValue)));
          jse.executeScript("arguments[0].click();",driver.findElement(By.id(attributeValue)));
          //driver.findElement(By.id(attibuteValue)).click();
          break;
        case "name":
          object= (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(attributeValue)));
          jse.executeScript("arguments[0].click();",driver.findElement(By.name(attributeValue)));
          //driver.findElement(By.name(attibuteValue)).click();
          break;
        case "text":
          String xpath="//*[text()='"+attributeValue+"']";
          object= (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
          jse.executeScript("arguments[0].click();",driver.findElement(By.xpath(xpath)));
          //driver.findElement(By.xpath(xpath)).click();
          break;
        case "class":
          object= (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("."+attributeValue)));
          jse.executeScript("arguments[0].click();",driver.findElement(By.cssSelector("."+attributeValue)));
          //driver.findElement(By.cssSelector("."+attibuteValue)).click();
          break;
        case "xpath":
          xpath=attributeValue;
          object= (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
          jse.executeScript("arguments[0].click();", driver.findElement(By.xpath(xpath)));
          // driver.findElement(By.xpath(xpath)).click();
          break;
        default:
          xpath="//*[@"+identifier+"='"+attributeValue+"']";
          object= (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
          jse.executeScript("arguments[0].click();",driver.findElement(By.xpath(xpath)));
          //driver.findElement(By.xpath(xpath)).click();
          break;
      }
    }catch(Exception e){
      System.out.println(e);
      throw new NoSuchElementException("Error occured in click("+ObjectName+","+identifier+","+attributeValue+"). Message:" + e);
    }
  }

  /**
   * This is used for class containing text, but can be extended for other identifiers as well using the same pattern
   * @param ObjectName The object type eg: link, button, etc
   * @param Identifier1 The name of the first identifier to be used eg: class, id, etc
   * @param Value1 The value of the first identifier
   * @param Identifier2 The name of the second identifier to be used eg: class, id, etc
   * @param Value2 The value of the second identifier
   */
  public void click(String ObjectName, String Identifier1, String Value1, String Identifier2, String Value2) {
    try {
      String xpath;
      Thread.sleep(2000);
      switch (Identifier1.toLowerCase()) {
        case "class":
          switch (Identifier2.toLowerCase()) {
            case "text":
              xpath = "//*[contains(@class,'" + Value1 + "') and text()='" + Value2 + "']";
              jse.executeScript("arguments[0].click();", driver.findElement(By.xpath(xpath)));
              break;
          }
          break;
      }
    } catch (Exception e) {
      System.out.println(e);
      throw new NoSuchElementException("Error occured in click(" + ObjectName + "," + Identifier1 + "," + Value1 + "+," + Identifier2 + "+," + Value2 + "). Message:" + e);
    }
  }

  /**
   * Types in editbox  with attribute Identifier=attibuteValue
   * @param text The text to be typed
   * @param identifier The name of the locator to be used eg: class, id, etc
   * @param attibuteValue The value of the identifier
   */
  public void type(String text,String identifier,String attibuteValue)  {
    identifier=identifier.toLowerCase();
    String xpath;
    try{
      switch(identifier) {
        case "class":
          xpath="//*[@class='"+attibuteValue+"']";
          object= (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
          driver.findElement(By.xpath(xpath)).sendKeys(text);
          break;
        case "id" :
          object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(attibuteValue)));
          driver.findElement(By.id(attibuteValue)).sendKeys(text);
          break;
        case "xpath" :
          object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(attibuteValue)));
          clearText("textbox", "xpath", attibuteValue);
          Thread.sleep(1500);
          driver.findElement(By.xpath(attibuteValue)).sendKeys(text);
          break;
        default:
          object= (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@"+identifier+"='"+attibuteValue+"']")));
          driver.findElement(By.xpath("//*[@"+identifier+"='"+attibuteValue+"']")).sendKeys(text);
          break;
      }
    }catch(Exception e){
      System.out.println(e);
      throw new NoSuchElementException("Error occured in type("+text+","+ identifier + ","+attibuteValue+"). Message:" + e);
    }
  }

  /**
   * Verifies if page with given title is opened.
   * @param title Title of the page
   * @return True if page with given title is opened. Else false.
   */
  public boolean  verifyPageOpened(String title)    {
    try{
      if (driver.findElements(By.xpath("//*[@title='"+title+"']")).size()>0) {return true;}
      else {return false;}
    }catch(Exception e){
      System.out.println(e);
      throw new NoSuchElementException("Error occured in verifyPageOpened("+title+"). Message:" + e);
    }
  }

  /**
   * Closes all new tabs opened and switches to the first tab
   */
  public void closeNewTabs() {
    List<String> browserTabs = new ArrayList<String>(driver.getWindowHandles());
    System.out.println("browserTabs.size()="+browserTabs.size());
    for(int i=browserTabs.size()-1;i>0;i--){
      System.out.println("closing tab: "+i);
      driver.switchTo().window(browserTabs.get(i));
      driver.close();
    }
    driver.switchTo().window(browserTabs.get(0));
  }

  /**
   * doubleClick - Double click on object
   * @param ObjectName The object type eg: link, button, etc
   * @param identifier The name of the identifier to be used eg: class, id, etc
   * @param attibuteValue The value of the identifier
   */
  public void doubleClick(String ObjectName, String identifier, String attibuteValue) {
    identifier = identifier.toLowerCase();
    Actions act = new Actions(driver);
    try {
      switch (identifier) {
        case "id":
          object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(attibuteValue)));
          act.doubleClick(object).perform();
//	                    jse.executeScript("arguments[0].click();",driver.findElement(By.id(attibuteValue)));
          // System.out.println("Button exist:"+driver.findElements(By.id(attibuteValue)).size());
          break;
        case "name":
          object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(attibuteValue)));
          act.doubleClick(object).perform();
//	                    jse.executeScript("arguments[0].click();",driver.findElement(By.name(attibuteValue)));
          //System.out.println("Button exist:"+driver.findElements(By.name(attibuteValue)).size());
          break;
        case "text":
          String xpath = "//*[text()='" + attibuteValue + "']";
          object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
          act.doubleClick(object).perform();
//	                    jse.executeScript("arguments[0].click();",driver.findElement(By.xpath(xpath)));
          //System.out.println("Button exist:"+driver.findElements(By.name(attibuteValue)).size());
          break;
        case "class":
          object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("." + attibuteValue)));
          act.doubleClick(object).perform();
//	                    jse.executeScript("arguments[0].click();",driver.findElement(By.cssSelector("."+attibuteValue)));
          //System.out.println("Button exist:"+driver.findElements(By.name(attibuteValue)).size());
          //driver.findElement(By.cssSelector("."+attibuteValue)).click();
          break;
        case "xpath":
          xpath = attibuteValue;
          object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
          act.doubleClick(object).perform();
//	                    jse.executeScript("arguments[0].click();", driver.findElement(By.xpath(xpath)));
          break;
        default:
          xpath = "//*[@" + identifier + "='" + attibuteValue + "']";
          object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
          act.doubleClick(object).perform();
//	                    jse.executeScript("arguments[0].click();",driver.findElement(By.xpath(xpath)));
          // System.out.println("Button exist:"+driver.findElements(By.xpath(xpath)).size());
          break;
      }
    } catch (Exception e) {
      System.out.println(e);
      throw new NoSuchElementException("Error occured in doubleClick(" + ObjectName + "). Message:" + e);
    }
  }

  /**
   * This is used for class containing text, but can be extended for other identifiers as well.
   * @param ObjectName The object type eg: link, button, etc
   * @param Identifier1 The name of the first identifier to be used eg: class, id, etc
   * @param Value1 The value of the first identifier
   * @param Identifier2 The name of the second identifier to be used eg: class, id, etc
   * @param Value2 The value of the second identifier
   */
  public void doubleClick(String ObjectName, String Identifier1, String Value1, String Identifier2, String Value2) {
    Actions act = new Actions(driver);
    try {
      String xpath;
      Thread.sleep(2000);
      switch (Identifier1.toLowerCase()) {
        case "class":
          switch (Identifier2.toLowerCase()) {
            case "text":
              xpath = "//*[contains(@class,'" + Value1 + "') and text()='" + Value2 + "']";
              object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
              act.doubleClick(object).perform();
//	                          jse.executeScript("arguments[0].click();", driver.findElement(By.xpath(xpath)));
              break;
          }
          break;
      }
    } catch (Exception e) {
      System.out.println(e);
      throw new NoSuchElementException("Error occured in doubleClick(" + ObjectName + "," + Identifier1 + "," + Value1 + "+," + Identifier2 + "+," + Value2 + "). Message:" + e);
    }
  }

  /**
   * Function Name - clearText -  clear text of a text field  accoring to passing parameter.
   * @param ObjectName The object type eg: link, button, etc
   * @param identifier The name of the identifier to be used eg: class, id, etc
   * @param attibuteValue The value of the identifier
   */
  public void clearText(String ObjectName, String identifier, String attibuteValue){
    try{
      switch(identifier.toLowerCase()) {
        case "id":
          object= (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(attibuteValue)));
          object.clear();
          break;
        case "name":
          object= (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(attibuteValue)));
          object.clear();
          break;
        case "text":
          String xpath="//*[text()='"+attibuteValue+"']";
          object= (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
          object.clear();
          break;
        case "class":
          xpath="//*[@class='"+attibuteValue+"']";
          object= (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
          object.clear();
          break;
        case "xpath":
          xpath=attibuteValue;
          object= (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
          object.clear();
          break;
        default:
          xpath="//*[@"+identifier+"='"+attibuteValue+"']";
          object= (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
          object.clear();
          break;
      }
    }catch(Exception e){
      System.out.println(e);
      throw new NoSuchElementException("Error occured in clearText("+ObjectName+"). Message:" + e);
    }
  }

  /**
   * rightClick Function   -- Example: And I right click on object identified by "text" "State Drop Down" then I select item identified by "text" "Arizona"
   * @param Identifier1 Name of the first identifier to be used eg: class, id, etc
   * @param Value1 Value of the first identifier
   * @param Identifier2 Name of the second identifier to be used eg: class, id, etc
   * @param Value2 Value of the second identifier
   * @throws InterruptedException
   */
  public void rightClick(String Identifier1, String Value1, String Identifier2, String Value2) throws InterruptedException {
    Identifier1 = Identifier1.toLowerCase();
    String xpath_rightClickableObj = "";
    String xpath_MoveToElement = "";
    Actions action = new Actions(driver);
    try {
      switch (Identifier1) {
        case "text":
          xpath_rightClickableObj = "//*[text()='" + Value1 + "']";
          object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath_rightClickableObj)));
          action.contextClick(driver.findElements(By.xpath(xpath_rightClickableObj)).get(0)).build().perform();
          Thread.sleep(2000);
          break;
        case "class":
          xpath_rightClickableObj = "//*[@class ='" + Value1 + "']";
          object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath_rightClickableObj)));
          action.contextClick(driver.findElements(By.xpath(xpath_rightClickableObj)).get(0)).build().perform();
          Thread.sleep(2000);
      }
    } catch (Exception e) {
      System.out.println(e);
      throw new NoSuchElementException("Error occured in rightClickable object (" + Identifier1 + "). Message:" + e);
    }
    Identifier2 = Identifier2.toLowerCase();
    try {
      switch (Identifier2) {
        case "text":
          xpath_MoveToElement = "//*[text()='" + Value2 + "']";
          object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath_MoveToElement)));
          break;
        case "class":
          xpath_MoveToElement = "//*[@class ='" + Value2 + "']";
          System.out.println("333 xpath_MoveToElement is " + xpath_MoveToElement);
          object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath_MoveToElement)));
      }
    } catch (Exception e) {
      System.out.println(e);
      throw new NoSuchElementException("Error occured in MoveToElement object (" + Identifier2 + "). Message:" + e);
    }
    try {
      action.moveToElement(driver.findElement(By.xpath(xpath_MoveToElement))).build().perform();
      Thread.sleep(1000);
      action.clickAndHold(driver.findElement(By.xpath(xpath_MoveToElement))).build().perform();
      Thread.sleep(1000);
      action.release(driver.findElement(By.xpath(xpath_MoveToElement))).build().perform();
    } catch (Exception e) {
      System.out.println(e);
      throw new NoSuchElementException("Error occured in MoveToElement object (" + Identifier2 + "). Message:" + e);
    }
  }


  /**
   * @param objIdentifier1 Name of the first object identifier to be used eg: class, id, etc
   * @param objValue1 Value of the first object identifier
   * @param objIdentifier2 Name of the second object identifier to be used eg: class, id, etc
   * @param objValue2 Value of the second object identifier
   * @param mnuIdentifier1 Name of the first menu identifier to be used eg: class, id, etc
   * @param mnuValue1 Value of the first menu identifier
   * @param mnuIdentifier2 Name of the second menu identifier to be used eg: class, id, etc
   * @param mnuValue2 Value of the second menu identifier
   * @param subMnuIdentifier1 Name of the first submenu identifier to be used eg: class, id, etc
   * @param subMnuValue1 Value of the first submenu identifier
   * @param subMnuIdentifier2 Name of the second submenu identifier to be used eg: class, id, etc
   * @param subMnuValue2 Value of the second submenu identifier
   * @throws InterruptedException
   */
  public void rightClick(String objIdentifier1, String objValue1, String objIdentifier2, String objValue2, String mnuIdentifier1, String mnuValue1, String mnuIdentifier2, String mnuValue2, String subMnuIdentifier1, String subMnuValue1, String subMnuIdentifier2, String subMnuValue2) throws InterruptedException {
    String xpath_rightClickableObj = "";
    String xpath_MoveToElement1 = "";
    String xpath_MoveToElement2 = "";
    Actions action = new Actions(driver);
    try {
      switch (objIdentifier1.toLowerCase()) {
        case "class":
          switch (objIdentifier2.toLowerCase()) {
            case "text":
              xpath_rightClickableObj = "//*[contains(@class,'" + objValue1 + "') and contains(text(),'" + objValue2 + "')]";
              object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath_rightClickableObj)));
              action.contextClick(driver.findElements(By.xpath(xpath_rightClickableObj)).get(0)).build().perform();
              Thread.sleep(2000);
          }
      }
    } catch (Exception e) {
      System.out.println(e);
      throw new NoSuchElementException("Error occured in rightClickable object (" + objIdentifier2 + "). Message:" + e);
    }
    // ****************   mnuIdentifier
    try {
      switch (mnuIdentifier1.toLowerCase()) {
        case "class":
          switch (mnuIdentifier2.toLowerCase()) {
            case "text":
              xpath_MoveToElement1 = "//*[contains(@class,'" + mnuValue1 + "') and text()='" + mnuValue2 + "']";
              object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath_MoveToElement1)));
              action.moveToElement(driver.findElement(By.xpath(xpath_MoveToElement1))).build().perform();
              Thread.sleep(1000);
              // break;

            case "containstext":
              xpath_MoveToElement1 = "//*[contains(@class,'" + mnuValue1 + "') and contains(text(),'" + mnuValue2 + "')]";
              object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath_MoveToElement1)));
              action.moveToElement(driver.findElement(By.xpath(xpath_MoveToElement1))).build().perform();
              Thread.sleep(1000);
              // break;
          }
      }
    } catch (Exception e) {
      System.out.println(e);
      throw new NoSuchElementException("Error occured in MoveToElement object (" + mnuIdentifier2 + "). Message:" + e);
    }

    // ****************   SubMnuIdentifier

    try {
      switch (subMnuIdentifier1.toLowerCase()) {
        case "class":
          switch (subMnuIdentifier2.toLowerCase()) {
            case "text":
              xpath_MoveToElement2 = "//*[contains(@class,'" + subMnuValue1 + "') and text()='" + subMnuValue2 + "']";
              object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath_MoveToElement2)));
              //break;
            case "containstext":
              xpath_MoveToElement2 = "//*[contains(@class,'" + subMnuValue1 + "') and contains(text(),'" + subMnuValue2 + "')]";
              object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath_MoveToElement2)));
              //break;
          }
      }
    } catch (Exception e) {
      System.out.println(e);
      throw new NoSuchElementException("Error occured in MoveToElement object (" + subMnuIdentifier2 + "). Message:" + e);
    }
    try {
      System.out.println("345 xpath_MoveToElement is " + xpath_MoveToElement2);
      action.moveToElement(driver.findElement(By.xpath(xpath_MoveToElement2))).build().perform();
      Thread.sleep(1000);
      action.clickAndHold(driver.findElement(By.xpath(xpath_MoveToElement2))).build().perform();
      Thread.sleep(1000);
      action.release(driver.findElement(By.xpath(xpath_MoveToElement2))).build().perform();
    } catch (Exception e) {
      System.out.println(e);
      throw new NoSuchElementException("Error occured in MoveToElement object (" + subMnuIdentifier2 + "). Message:" + e);
    }
  }

  /**
   * Function verifyTextInTextArea - This function will search and verify any mentioned text in a particular tag or node
   * @param xpath
   * @throws InterruptedException
   */
  public void scrollElement(String xpath) {
    try {
      object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
      JavascriptExecutor je = (JavascriptExecutor) driver;
      WebElement element = driver.findElement(By.xpath(xpath));
      je.executeScript("arguments[0].scrollIntoView(true);", element);
      Thread.sleep(1000);
    } catch (Exception e) {
      System.out.println(e);
      throw new NoSuchElementException("Error occured in scrollElement(" + xpath + "). Message:" + e);
    }
  }

  public void mouseHover(String ObjectName, String attributeValue, String identifier) {
    try {
      Actions action = new Actions(driver);
      switch (attributeValue.toLowerCase()) {
        case "xpath":
          object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(identifier)));
          action.moveToElement(driver.findElement(By.xpath(identifier))).build().perform();
          System.out.println("the output is"+driver.findElement(By.xpath(identifier)).getText());
          object.clear();
          break;
      }
    } catch (Exception e) {
      System.out.println(e);
      throw new RuntimeException("Error occured in mousehover. Message:" + e);
    }
  }

  public boolean isDisabledElement(String searchType,String identifier,String identifierValue) throws Throwable{
    this.driver=WebDriverFactory.driver;
    jse=(JavascriptExecutor)driver;
    wait = new WebDriverWait(driver,20);
    identifier=identifier.toLowerCase();
    try {
      switch (identifier) {
        case "text":
          String xpath1="//*[contains(text(),'"+identifierValue+"')]";
          if(driver.findElement(By.xpath(xpath1)).isEnabled()){return true;}
          else {return false;}
        case "xpath":
          if(driver.findElement(By.xpath(identifierValue)).isEnabled()){return true;}
          else {return false;}
        default:
          throw new Exception("isDisabledElement undefined for identifier "+identifier );
      }
    }
    catch (NoSuchElementException e) {
      System.out.println(e);
      throw new NoSuchElementException("Error occured in verifyAbsenceInReportDetails(" + searchType + "). Message:" + e);
    }
  }

  public void handleAlert(String txtMsg, String clickButton) throws InterruptedException {
    try {
      this.driver=WebDriverFactory.driver;
      Alert alert = driver.switchTo().alert();
//	            String alertmsg = driver.switchTo().alert().getText();
      Thread.sleep(3000);
      if (clickButton.equals("OK")) {
        driver.switchTo().alert().accept();
      } else {
        driver.switchTo().alert().dismiss();
      }
    } catch (Exception e) {
      System.out.println(e);
      throw new RuntimeException("Error occured in handleAlert(). Message:" + e);
    }
  }

  /**
   * Overloaded function for 2 parameters for right click
   * @param xpath_rightClickableObj
   * @param xpath_MoveToElement
   * @throws InterruptedException
   */
  public void rightClick(String xpath_rightClickableObj,String xpath_MoveToElement) throws InterruptedException    {
    try {
      this.driver=WebDriverFactory.driver;
      jse=(JavascriptExecutor)driver;
      wait = new WebDriverWait(driver,20);
      object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath_rightClickableObj)));
      Actions action = new Actions(driver);
//	            Assert.assertTrue("Right clickable object not fpond.", driver.findElements(By.xpath(xpath_rightClickableObj)).size() > 0);
      action.contextClick(driver.findElements(By.xpath(xpath_rightClickableObj)).get(0)).build().perform();
      //Thread.sleep(1000);
      object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath_MoveToElement)));
      action.moveToElement(driver.findElement(By.xpath(xpath_MoveToElement))).build().perform();
      Thread.sleep(1000);
      action.clickAndHold(driver.findElement(By.xpath(xpath_MoveToElement))).build().perform();
      Thread.sleep(1000);
      action.release(driver.findElement(By.xpath(xpath_MoveToElement))).build().perform();

    }
    catch(Exception e)
    {
      System.out.println(e);
      throw new RuntimeException("Error occured in rightClick(). Message:" + e);
    }
  }

  /**
   * Click on the specified object.
   * @param ObjectName: Name of the object on which to double click. This should be defined in the page.java class
   * @param pageName: Name of the page (class) in which the object is  defined
   * @throws Exception
   */
  public void clickOnPage(String ObjectName, String pageName) throws Exception {
    pageName = pageName + "Page";
    try{
      Class<?> clazz = Class.forName("pageobject."+pageName);
      Object obj = clazz.getDeclaredConstructor().newInstance();
      Field field = obj.getClass().getField(ObjectName);
      driver.findElement((By) field.get(obj)).click();
      field=null;
      obj=null;
    }catch(Exception e){
      System.out.println("ERROR: clickOnPage("+ObjectName+","+pageName+")");
      throw new Exception("ERROR: clickOnPage("+ObjectName+","+pageName+")" + e);
    }
  }

  /**
   * Type on the specified object (usually a text box).
   * @param ObjectName: Name of the object on which to type. This should be defined in the page.java class
   * @param pageName: Name of the page (class) in which the object is  defined
   * @throws Exception
   */
  public void typeOnPage(String text,String ObjectName, String pageName) throws Exception {
    pageName = pageName + "Page";
    try{
      Class<?> clazz = Class.forName("pageobject."+pageName);
      Object obj = clazz.getDeclaredConstructor().newInstance();
      Field field = obj.getClass().getField(ObjectName);
      driver.findElement((By) field.get(obj)).sendKeys(text);
      field=null;
      obj=null;
    }catch(Exception e){
      System.out.println("ERROR: typeOnPage("+text+","+ObjectName+","+pageName+")");
      throw new Exception("ERROR: typeOnPage("+text+","+ObjectName+","+pageName+")" + e);
    }
  }

  /**
   * Verify whether the specified object exists on page. REturns true if object exists.
   * @param ObjectName: Name of the object on which to double click. This should be defined in the page.java class
   * @param pageName: Name of the page (class) in which the object is  defined
   * @throws Exception
   */
  public boolean verifyObjectPresentOnPage(String ObjectName, String pageName) throws Exception {
    pageName = pageName + "Page";
    try{
      Class<?> clazz = Class.forName("pageobject."+pageName);
      Object obj = clazz.getDeclaredConstructor().newInstance();
      Field field = obj.getClass().getField(ObjectName);
      if (driver.findElements((By) field.get(obj)).size()>0){
        field=null;
        obj=null;
        return true;
      }
      else{
        field=null;
        obj=null;
        return false;
      }
    }catch(Exception e){
      System.out.println("ERROR: verifyObjectPresentOnPage("+ObjectName+","+pageName+")");
      throw new Exception("ERROR: verifyObjectPresentOnPage("+ObjectName+","+pageName+")" + e);
    }
  }

  /**
   * Double click on the specified object.
   * @param ObjectName: Name of the object on which to double click. This should be defined in the page.java class
   * @param pageName: Name of the page (class) in which the object is  defined
   * @throws Exception
   */
  public void doubleClickOnPage(String ObjectName, String pageName) throws Exception {
    pageName = pageName + "Page";
    Actions act = new Actions(driver);
    try{
      Class<?> clazz = Class.forName("pageobject."+pageName);
      Object obj = clazz.getDeclaredConstructor().newInstance();
      Field field = obj.getClass().getField(ObjectName);
//	            System.out.println(field.get(obj));
      object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated((By)field.get(obj)));
      act.doubleClick(object).perform();
      field=null;
      obj=null;
      act=null;
    }catch(Exception e){
      System.out.println("ERROR: doubleClickOnPage("+ObjectName+","+pageName+")");
      throw new Exception("ERROR: doubleClickOnPage("+ObjectName+","+pageName+")" + e);
    }
  }

  /**
   * Clears text in the given obejct (usually a text box)
   * @param ObjectName: Name of the object to be cleared. This should be defined in the page.java class
   * @param pageName: Name of the page (class) in which the object is  defined
   * @throws Exception
   */
  public void clearTextOnPage(String ObjectName, String pageName) throws Exception {
    pageName = pageName + "Page";
    Actions act = new Actions(driver);
    try{
      Class<?> clazz = Class.forName("pageobject."+pageName);
      Object obj = clazz.getDeclaredConstructor().newInstance();
      Field field = obj.getClass().getField(ObjectName);
//	            System.out.println(field.get(obj));
      driver.findElement((By) field.get(obj)).clear();
      field=null;
      obj=null;
      act=null;
    }catch(Exception e){
      System.out.println("ERROR: clearTextOnPage("+ObjectName+","+pageName+")");
      throw new Exception("ERROR: clearTextOnPage("+ObjectName+","+pageName+")" + e);
    }
  }

  /**
   * Hovers mouse over the specified object.
   * @param ObjectName: Name of the object on which to hover. This should be defined in the page.java class
   * @param pageName: Name of the page (class) in which the object is  defined
   * @throws Exception
   */
  public void mouseHoverOnPage(String ObjectName, String pageName) throws Exception {
    pageName = pageName + "Page";
    Actions act = new Actions(driver);
    try{
      Class<?> clazz = Class.forName("pageobject."+pageName);
      Object obj = clazz.getDeclaredConstructor().newInstance();
      Field field = obj.getClass().getField(ObjectName);
//	            System.out.println(field.get(obj));
      object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated((By)field.get(obj)));
      act.moveToElement(driver.findElement((By)field.get(obj))).build().perform();
      field=null;
      obj=null;
      act=null;
    }catch(Exception e){
      System.out.println("ERROR: mouseHoverOnPage("+ObjectName+","+pageName+")");
      throw new Exception("ERROR: mouseHoverOnPage("+ObjectName+","+pageName+")" + e);
    }
  }

  /**
   * Verified whether the given object is disabled. Returns true if disabled.
   * @param ObjectName: Name of the object on which to right click. This should be defined in the page.java class
   * @param pageName: Name of the page (class) in which the object is  defined
   * @throws Exception
   */
  public boolean isDisabledElementOnPage(String ObjectName, String pageName) throws Exception {
    pageName = pageName + "Page";
    try {
      Class<?> clazz = Class.forName("pageobject."+pageName);
      Object obj = clazz.getDeclaredConstructor().newInstance();
      Field field = obj.getClass().getField(ObjectName);
      System.out.println(field.get(obj));
      driver.findElement((By) field.get(obj)).click();

      if (driver.findElement((By) field.get(obj)).isEnabled()){
        field=null;
        obj=null;
        return true;
      }
      else{
        field=null;
        obj=null;
        return false;
      }

    }catch(Exception e){
      System.out.println("ERROR: disabledElementOnPage("+ObjectName+","+pageName+")");
      throw new Exception("ERROR: disabledElementOnPage("+ObjectName+","+pageName+")" + e);
    }
  }

  /**
   * Right clicks on an object and then selects an object from the context menu displayed.
   * @param rtClickObjectName: Name of the object on which to right click. This should be defined in the page.java class
   * @param selectObjectName: Name of the object that should be selected after right click. This should be defined in the page.java class
   * @param pageName: Name of the page (class) in which these objects are defined
   * @throws Exception
   */
  public void rightClickOnPage(String rtClickObjectName, String selectObjectName, String pageName) throws Exception {
    pageName = pageName + "Page";
    Actions act = new Actions(driver);
    try {
      Class<?> clazz = Class.forName("pageobject."+pageName);
      Object obj = clazz.getDeclaredConstructor().newInstance();
      Field field = obj.getClass().getField(rtClickObjectName);
      System.out.println(field.get(obj));
      object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated((By)field.get(obj)));
      act.contextClick(driver.findElements((By)field.get(obj)).get(0)).build().perform();
      field=null;
      Thread.sleep(2000);

      field = obj.getClass().getField(selectObjectName);
      System.out.println(field.get(obj));
      object = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated((By)field.get(obj)));
      act.moveToElement(driver.findElement((By)field.get(obj))).build().perform();
      Thread.sleep(1000);
      act.clickAndHold(driver.findElement((By)field.get(obj))).build().perform();
      Thread.sleep(1000);
      act.release(driver.findElement((By)field.get(obj))).build().perform();
      field=null;
      obj=null;
      object=null;
      act=null;
    } catch (Exception e) {
      System.out.println("ERROR: rightClickOnPage("+rtClickObjectName+","+selectObjectName+","+pageName+")");
      throw new Exception("ERROR: rightClickOnPage("+rtClickObjectName+","+selectObjectName+","+pageName+")" + e);
    }
  }

  /**
   * Select a value from dropdown.
   * @param selection: Value to be selected from the dropdown
   * @param dropdown: Name of the dropdown where the selection is to be made. This should be defined in the page.java class*
   * @param pageName: Name of the page (class) in which the object is  defined
   * @throws Exception
   */
  public void selectDropdownOnPage(String selection, String dropdown, String pageName) throws Exception {
    pageName = pageName + "Page";
    try{
      Class<?> clazz = Class.forName("pageobject."+pageName);
      Object obj = clazz.getDeclaredConstructor().newInstance();
      Field field = obj.getClass().getField(dropdown);
      Select dropdwn=new Select(driver.findElement((By) field.get(obj)));
      dropdwn.selectByVisibleText(selection);

      field=null;
      obj=null;
    }catch(Exception e){
      System.out.println("ERROR: selectDropdown("+selection+","+dropdown+","+pageName+")");
      throw new Exception("ERROR: selectDropdown("+selection+","+dropdown+","+pageName+")" + e);
    }
  }

  public String createCSVResultFile(String ResultFolder) throws IOException {
    try{
      CSVWriter writecsv;
      properties = Settings.getProperties();
      String env = properties.getProperty("Environment");
      String projectname = properties.getProperty("ProjectName");
      String browser = properties.getProperty("Browser");
      DateFormat dateFormat = new SimpleDateFormat("ddMMMyyyy_HH_mm_ss");
      String todaysDateTime= dateFormat.format(new Date());
      String strCSVFilePath = ResultFolder+"\\Result_"+ env+"_"+projectname+"_"+browser+"_"+ todaysDateTime + ".csv"; //File path to be taken as input from user
//	            System.out.println("strCSVFilePath="+strCSVFilePath);
      writecsv = new CSVWriter(new FileWriter(strCSVFilePath));
      return strCSVFilePath;
    }catch(Exception e){
      System.out.println(e);
      throw new RuntimeException("Error occured in createCSVResultFile(). Message:" + e);
      // return false;
    }
  }





}
