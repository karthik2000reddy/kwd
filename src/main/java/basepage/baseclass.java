package basepage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class baseclass 
{
	
	public static WebDriver driver;
	public Properties p;
	
	public WebDriver init_driver(String browser) {
	    if (browser.equalsIgnoreCase("chrome")) {
	        ChromeOptions options = new ChromeOptions();
	        options.addArguments("--disable-notifications");  // Disables notifications
	        driver = new ChromeDriver(options);
	    } else if (browser.equalsIgnoreCase("firefox")) {
	        FirefoxOptions options = new FirefoxOptions();
	        options.addPreference("dom.webnotifications.enabled", false);  // Disables notifications
	        driver = new FirefoxDriver(options);
	    }
	    return driver;
	}

public Properties init_properties() throws IOException
{
	p=new Properties();
	
		FileInputStream ip=new FileInputStream("C:\\Users\\karth\\eclipse-workspace\\keyword\\src\\main\\java\\keyword_\\config.p");
		p.load(ip);
     
	return p;
	
	
	}


public String captureScreen(String tname) throws IOException {

	

	String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

	

	TakesScreenshot takesScreenshot = (TakesScreenshot) driver;

	File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

	

	String targetFilePath = System.getProperty("user.dir") + "\\SS\\fullpage.png" + tname + "_" + timeStamp;

	File targetFile = new File(targetFilePath);

	targetFile.getParentFile().mkdirs();

	FileUtils.copyFile(sourceFile, targetFile);

	

	return targetFilePath;

	

	}


}