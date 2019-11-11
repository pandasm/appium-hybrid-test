package coretests;

import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class mobileAppTest {
	
	public static AndroidDriver<WebElement> driver;
	public DesiredCapabilities capabilities;
	
	@BeforeSuite
	public void setup() throws Exception{
		
		final String URL_STRING = "http://localhost:4444/wd/hub";
	    URL  url = new URL(URL_STRING);
		
	    capabilities = new DesiredCapabilities();
	    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "oneplus7");
	    //capabilities.setCapability(MobileCapabilityType.APP, "https://github.com/afollestad/material-dialogs/raw/master/sample/sample.apk");
	    capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
	    capabilities.setCapability(MobileCapabilityType.UDID, "9799e5cb");
	    capabilities.setCapability(MobileCapabilityType.APP, "/Users/soumyamukherjee/Documents/Working/Projects/CatseyeQA/mobileautomation/libs/apk/techkare_app.apk");
	    capabilities.setCapability("appPackage", "com.catseye.techkare");
	    capabilities.setCapability("appActivity", "com.catseye.techkare.MainActivity");
	    capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
	    
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
		driver.findElement(By.id("email")).click();
		Thread.sleep(10000);
		driver.getKeyboard().sendKeys("abc@abc.com");
		Thread.sleep(10000);
		driver.findElement(By.id("password")).click();
		driver.getKeyboard().sendKeys("blahblahblah");
		Thread.sleep(10000);
		driver.findElement(By.xpath("//*[@id=\"login\"]/div[3]/button")).click();
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
