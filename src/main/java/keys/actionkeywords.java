package keys;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class actionkeywords {
	public static WebDriver driver;
	public static void openbrowser() {
		ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
		driver=new ChromeDriver(options);
		
	}
public static void navigate() {
	driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
	driver.get("https://login.salesforce.com/");
	driver.manage().window().maximize();
}
public static void username() {
	driver.findElement(By.id("username")).sendKeys("karthikreddy62758@gmail.com");
	
}
public static void password() {
	driver.findElement(By.id("password")).sendKeys("Karthik@1997");
	
}
public static void login() {
	driver.findElement(By.id("Login")).click();
	
}
public static void appluancher() {
	driver.findElement(By.xpath("//button[@class=\"slds-button slds-context-bar__button slds-icon-waffle_container slds-show\"]")).click();
	
}
}
