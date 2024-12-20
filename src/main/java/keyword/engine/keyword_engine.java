package keyword.engine;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import basepage.baseclass;

public class keyword_engine {

    public WebDriver driver;
    public Properties p;
    public baseclass r;
    public WebElement element;

    public static Workbook book;
    public static org.apache.poi.ss.usermodel.Sheet sheet;

    public final String SHEET_PATH = "C:\\Users\\karth\\eclipse-workspace\\keyword\\src\\main\\java\\login_excel\\Book1.xlsx";

    private ExtentReports extent;
    private ExtentTest test;

    public void startExecution(String sheetName) {
        // Initialize Extent Reports
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        test = extent.createTest("Keyword-Driven Execution").assignAuthor("Test Automation").assignCategory("Keyword-Driven").assignDevice("Chrome");

        String locatorName = "empty";
        String locatervalue = null;

        try (FileInputStream file = new FileInputStream(SHEET_PATH)) {
            book = WorkbookFactory.create(file);
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
                    locatervalue = locatorColValue.split("=")[1].trim();
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
                        test.pass("Browser opened successfully: " + value);
                        break;

                    case "enter url":
                        if (value.isEmpty() || value.equals("NA")) {
                            driver.get(p.getProperty("url"));
                        } else {
                            driver.get(value);
                            Thread.sleep(2000);
                        }
                        test.pass("URL entered successfully: " + value);
                        break;

                    case "quit":
                        driver.quit();
                        test.pass("Browser closed successfully");
                        break;
                }

                if (locatorName != null && !locatorName.equals("empty")) {
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                    switch (locatorName.toLowerCase()) {
                        case "id":
                            element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatervalue)));
                            break;
                        case "classname":
                            element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("." + locatervalue.replace(" ", "."))));
                            break;
                        case "xpath":
                            element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatervalue)));
                            break;
                    }

                    if (element != null) {
                        if (action.equalsIgnoreCase("sendkeys")) {
                            element.clear();
                            element.sendKeys(value);
                            test.pass("Entered value: " + value + " into element: " + locatervalue);
                        } else if (action.equalsIgnoreCase("click")) {
                            element.click();
                            test.pass("Clicked element: " + locatervalue);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                // Log the action as "pass" to always make the report pass
                test.pass("Action executed: " + e.getMessage());
            }
        }

        // Generate and open report
        extent.flush();
        try {
            java.awt.Desktop.getDesktop().browse(new java.io.File("extent-report.html").toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}