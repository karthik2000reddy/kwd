package keys;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class driverclass {

    public static void main(String[] args) throws IOException {

        // Define the report file location
        String reportPath = "ExtentReport.html";

        // Initialize Extent Reports
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Add general information to the report (Optional)
        extent.setSystemInfo("Salesforce", "QA");
        extent.setSystemInfo("Tester", "Karthik");

        // Create a test logger
        ExtentTest test = extent.createTest("Keyword Driven Framework Test");

        String spath = "C:\\Users\\karth\\OneDrive\\Documents\\keywords.xlsx";
        ExcelUtlis.setexcelfile(spath, "Sheet1");

        for (int irow = 1; irow <= 6; irow++) {

            String sActionkeywords = ExcelUtlis.getcellData(irow, 0);

            try {
                if (sActionkeywords.equals("openbrowser")) {
                    actionkeywords.openbrowser();
                    test.pass("Browser opened successfully.");
                } else if (sActionkeywords.equals("navigate")) {
                    actionkeywords.navigate();
                    test.pass("Navigated to the URL successfully.");
                } else if (sActionkeywords.equals("username")) {
                    actionkeywords.username();
                    test.pass("Entered username successfully.");
                } else if (sActionkeywords.equals("password")) {
                    actionkeywords.password();
                    test.pass("Entered password successfully.");
                } else if (sActionkeywords.equals("login")) {
                    actionkeywords.login();
                    test.pass("Login was successful.");
                    System.out.println("Login got successful.");
                } else if (sActionkeywords.equals("appluancher")) {
                    actionkeywords.appluancher();
                    test.pass("Clicked on App Launcher successfully.");
                    System.out.println("Clicked on App Launcher.");
                }
            } catch (Exception e) {
                test.fail("Error occurred while performing action: " + sActionkeywords);
                test.fail(e.getMessage());
            }
        }

        // Flush the report to write all data
        extent.flush();

        // Open the report file automatically
        try {
            File reportFile = new File(reportPath);
            if (reportFile.exists()) {
                Desktop.getDesktop().browse(reportFile.toURI());
                System.out.println("Extent Report opened successfully.");
            } else {
                System.out.println("Report file not found.");
            }
        } catch (Exception e) {
            System.out.println("Error while opening the report: " + e.getMessage());
        }

        System.out.println("Execution completed. Check the ExtentReport.html for details.");
    }
}
