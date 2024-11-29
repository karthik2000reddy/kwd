package keyword.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import basepage.baseclass;

public class keyword_engine  {

    public  WebDriver driver;
    public Properties p;
    public baseclass r;
    public WebElement element;

    public static Workbook book;
    public static org.apache.poi.ss.usermodel.Sheet sheet;

    public final String SHEET_PATH = "C:\\Users\\karth\\eclipse-workspace\\keyword\\src\\main\\java\\login_excel\\Book1.xlsx";

    public void startExecution(String sheetName) {
        String locatorName = "empty";
        String locatervalue = null;

        FileInputStream file = null;
        try {
            file = new FileInputStream(SHEET_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            book = WorkbookFactory.create(file);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sheet = book.getSheet(sheetName);
        int k = 0;
        int row = sheet.getLastRowNum();

        for (int i = 0; i < row; i++) {
            try {
                String locatorColValue = sheet.getRow(i + 1).getCell(k + 1).toString().trim();
                if (!locatorColValue.equalsIgnoreCase("NA")) {
                    locatorName = locatorColValue.split("=")[0].trim();
                    if(locatorName.equalsIgnoreCase("xpath")) {
                    	locatervalue=locatervalue.split("#")[1];
                    }
                    else {
                        locatervalue = locatorColValue.split("=")[1].trim();
					}
                }

                String action = sheet.getRow(i + 1).getCell(k + 2).toString().trim();
                String value = sheet.getRow(i + 1).getCell(k + 3).toString().trim();

                switch (action) {
                    case "open browser":
                        r = new baseclass();
                        p = r.init_properties();
                        if (value.isEmpty() || value.equals("NA")) {

                            driver = r.init_driver(p.getProperty("browser"));

                        } else {
                            driver = r.init_driver(value);
                            driver.manage().window().maximize();

                        }
                        break;

                    case "enter url":
                        if (value.isEmpty() || value.equals("NA")) {

                            driver.get(p.getProperty("url"));
                        } else {
                            driver.get(value);
                            Thread.sleep(2000);
                        }
                        break;

                    case "quit":
                        driver.quit();
                        break;
                }

                if (locatorName != null) {
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10-second wait

                    switch (locatorName.toLowerCase()) {
                        case "id":
                            element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatervalue)));
                        	//element=driver.findElement(By.id(locatervalue));
                            break;
                        case "classname":
                        	//element=driver.findElement(By.id(locatervalue));

                            element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("." + locatervalue.replace(" ", "."))));
                            break;
                        case "xpath":
                        	element=driver.findElement(By.xpath(locatervalue));
                            
                           // element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatervalue)));
                            break;
                        default:
                            System.out.println("Invalid locator type: " + locatorName);
                            break;
                    }

                    if (element != null) {
                        if (action.equalsIgnoreCase("sendkeys")) {
                            element.clear();  // Clear before sending keys
                            element.sendKeys(value);
                            System.out.println("Entering value: " + value + " into element: " + locatervalue);
                        } else if (action.equalsIgnoreCase("click")) {
                            element.click();
                            System.out.println("Clicking element: " + locatervalue);
                            
                        }
                    } else {
                        System.out.println("Element not found: " + locatervalue);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
  
}
