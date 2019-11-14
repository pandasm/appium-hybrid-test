package coretests;

import org.testng.annotations.Test;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import pageobject.*;

public class mobileAppTest {
	
	static AndroidDriver<WebElement> driver;
	DesiredCapabilities capabilities;
	locator loc = new locator();
	Properties prop;
	InputStream stream;
	
	@BeforeSuite
	public void setup() throws Exception{
		
		prop = new Properties();
		String propname = "./config/application.properties";
		stream = getClass().getClassLoader().getResourceAsStream(propname);
		
		prop.load(stream);
		
		final String URL_STRING = "http://" + prop.getProperty("servername") + ":" + prop.getProperty("port") +"/wd/hub";
	    URL  url = new URL(URL_STRING);
		
	    capabilities = new DesiredCapabilities();
	    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, prop.getProperty("devicename"));
	    capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
	    capabilities.setCapability(MobileCapabilityType.UDID, prop.getProperty("udid"));
	    capabilities.setCapability(MobileCapabilityType.APP, prop.getProperty("apppath"));
	    capabilities.setCapability("appPackage", prop.getProperty("apppackage"));
	    capabilities.setCapability("appActivity", prop.getProperty("appactivity"));
	    
	    driver = new AndroidDriver<WebElement>(url, capabilities);
	    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	    driver.resetApp();
		
	}
	
	@Test
	public void mobiletest() throws Exception{
		
		switchToWebView(driver);
		
		//Thread.sleep should not be used - this is just a sample
		Thread.sleep(20000);
		System.out.println("Setting email");
		driver.findElement(By.id(loc.email_username)).click();
		Thread.sleep(10000);
		driver.getKeyboard().sendKeys(prop.getProperty("appusername"));
		Thread.sleep(10000);
		driver.findElement(By.id(loc.email_password)).click();
		driver.getKeyboard().sendKeys(prop.getProperty("apppassword"));
		Thread.sleep(10000);
		driver.findElement(By.xpath(loc.login_button)).click();
		Thread.sleep(10000);
	}
	
	@AfterSuite
	public void after(){
		driver.quit();
	}
	
	public void switchToWebView(AndroidDriver driver) {
		Set<String> availableContexts = driver.getContextHandles();
		System.out.println("Total No of Context Found After we reach to WebView = " + availableContexts.size());
		for (String context : availableContexts) {
			System.out.println("Context Name is " + context);
			if (context.contains("WEBVIEW")) {
				System.out.println("Setting Web View");
				driver.context(context);
				break;
			}
		}
	}
	

}
