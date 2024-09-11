package com.demo.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {

    // public static WebDriver driver;
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    WebDriver driverInstance;
    @BeforeClass(alwaysRun = true)
    @Parameters({"browser"})
    public void setup(@Optional("chrome") String browser) {
        ExtentReportManager.initReports();
        // WebDriver driverInstance;
        if (browser.equalsIgnoreCase("chrome")) {
            driverInstance = new ChromeDriver();
        }
        else{
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        // Additional browser setup can be added here
        driverInstance.manage().window().maximize();
        driver.set(driverInstance);
    }

    @BeforeMethod(alwaysRun = true)
    public void startTest(Method method) {
        ExtentReportManager.createTest(method.getName());
    }

    // @AfterMethod(alwaysRun = true)
    // public void captureScreenshotOnFailure(ITestResult result) {
    //     if (ITestResult.FAILURE == result.getStatus()) {
    //         ExtentReportManager.reportStep("FAIL", "Test failed: " + result.getThrowable().getMessage());
    //     } else if (ITestResult.SUCCESS == result.getStatus()) {
    //         ExtentReportManager.reportStep("PASS", "Test passed");
    //     } else if (ITestResult.SKIP == result.getStatus()) {
    //         ExtentReportManager.reportStep("SKIP", "Test skipped");
    //     }
    // }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        WebDriver driverInstance=driver.get();
        if (driverInstance != null) {
            driverInstance.quit();
        }
        ExtentReportManager.flushReports();
    }
    public static Object[][] dataReader(String sheetName) throws IOException {
        try (FileInputStream fis = new FileInputStream(".\\datasrc\\userlogindata.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet sheet = workbook.getSheet(sheetName);
            int row = sheet.getPhysicalNumberOfRows();
            int column = sheet.getRow(0).getPhysicalNumberOfCells();
            Object[][] data = new Object[row - 1][column];
            for (int i = 0; i < row - 1; i++) {
                for (int j = 0; j < column; j++) {
                    data[i][j] = sheet.getRow(i + 1).getCell(j).getStringCellValue();
                }
            }
            return data;
        } catch (Exception e) {
            System.out.println("Excel File Not Found");
            e.printStackTrace();
            return null;
        }
    }

    public void waitForElement(WebElement ele, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driverInstance, Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.visibilityOf(ele));
        } catch (Exception e) {
            System.out.println("Problem while waiting for the element: " + ele.getText());
            e.printStackTrace();
        }
    }

    public void waitForElementClickable(WebElement ele, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driverInstance, Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.elementToBeClickable(ele));
        } catch (Exception e) {
            System.out.println("Problem while waiting for the element: " + ele.getText());
            e.printStackTrace();
        }
    }
}

/*------------------------------------------------------------------------
 package com.example.base;

import com.example.report.ReportManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public class BaseTest {

    public static WebDriver driver; // Made static so it can be accessed from the ReportManager

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser"})
    public void setup(@Optional("chrome") String browser) {
        ReportManager.initReports();
        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        }
        // Additional browser setup can be added here
        driver.manage().window().maximize();
    }

    @BeforeMethod(alwaysRun = true)
    public void startTest(Method method) {
        ReportManager.createTest(method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void captureScreenshotOnFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            ReportManager.reportStep("FAIL", "Test failed: " + result.getThrowable().getMessage());
        } else if (ITestResult.SUCCESS == result.getStatus()) {
            ReportManager.reportStep("PASS", "Test passed");
        } else if (ITestResult.SKIP == result.getStatus()) {
            ReportManager.reportStep("SKIP", "Test skipped");
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        ReportManager.flushReports();
    }
}
--------------------------------------------------------------------------------------------------------- 
 */
// package com.demo;


// import java.io.FileInputStream;
// import java.io.FileNotFoundException;
// import java.io.IOException;
// import java.lang.reflect.Method;
// import java.net.MalformedURLException;
// import java.net.URL;
// import java.time.Duration;

// import org.apache.poi.xssf.usermodel.XSSFSheet;
// import org.apache.poi.xssf.usermodel.XSSFWorkbook;
// import org.openqa.selenium.MutableCapabilities;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.edge.EdgeDriver;
// import org.openqa.selenium.firefox.FirefoxDriver;
// import org.openqa.selenium.remote.RemoteWebDriver;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.WebDriverWait;
// import org.testng.annotations.AfterClass;
// import org.testng.annotations.AfterMethod;
// import org.testng.annotations.BeforeClass;
// import org.testng.annotations.BeforeMethod;
// import org.testng.annotations.Optional;
// import org.testng.annotations.Parameters;

// import com.aventstack.extentreports.ExtentReports;
// import com.aventstack.extentreports.ExtentTest;
// import com.aventstack.extentreports.reporter.ExtentSparkReporter;

// import io.github.bonigarcia.wdm.WebDriverManager;

// public class BaseTest {
//     protected WebDriver driver;
//     protected ExtentReports extent;
//     protected ThreadLocal<ExtentTest> test=new ThreadLocal<>();
//     // String machine="rem_machine";
//     String machine="loc_machine";
    
//     @BeforeClass
//     public synchronized void setupExtentReport(){
//         if(extent==null){
//             ExtentSparkReporter spark= new ExtentSparkReporter(".\\src\\report\\SparkReport1.html");
//             extent= new ExtentReports();
//             extent.attachReporter(spark);
//         }
//     }
//     @BeforeMethod
//     @Parameters("browserkey")
//     public synchronized void startTest(@Optional("chrome") String browserkey, Method method) throws MalformedURLException{
//         // test= extent.createTest(method.getName());
//         setTest(extent.createTest(method.getName()));
//         setup(browserkey);
//         System.out.println("******** "+method.getName()+"  started on ==> "+browserkey+" ****************");
//         getTest().info(" Starting  "+method.getName());
//     }
        
//     public void setup(String browserkey) throws MalformedURLException{
//         // String browserType="chrome";
//         System.out.println("Received browserkey: " + browserkey);
//         if(machine.equals("rem_machine")){
//             System.setProperty("webdriver.http.factory", "jdk-http-client");
//             // DesiredCapabilities capabilities=new DesiredCapabilities();
//             MutableCapabilities capabilities = new MutableCapabilities();
//             if(browserkey.equalsIgnoreCase("chrome")){
//                 capabilities.setCapability("browserName", "chrome");
//                 // capabilities.setBrowserName("chrome");
//                 // capabilities.setCapability("platformName", "LINUX");
//             }else if(browserkey.equalsIgnoreCase("firefox")){
//                 capabilities.setCapability("browserName", "firefox");
//                 // capabilities.setBrowserName("firefox");
//                 // capabilities.setCapability("platformName", "LINUX");
//             }else{
//                 capabilities.setCapability("browserName", "edge");
//                 // capabilities.setBrowserName("edge");
//             }
//             driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
//         }
//         if(machine.equals("loc_machine")){
//             try {
//                 System.setProperty("webdriver.http.factory", "jdk-http-client");
//             if (browserkey.equals("chrome")) {
//                 WebDriverManager.chromedriver().setup();
//                 driver = new ChromeDriver();
//                 // clearChromeCacheAndCookies(driver);
//             }
//             if (browserkey.equals("edge")) {
//                 WebDriverManager.edgedriver().setup();
//                 driver = new EdgeDriver();
//             } 
//             if (browserkey.equals("firefox")) {
//                 WebDriverManager.firefoxdriver().setup();
//                 driver = new FirefoxDriver(); 
//             }
//                 driver.manage().window().maximize();
//                 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
//             } catch (Exception e) {
//                 e.printStackTrace();
//             }

//         }
//     }

    
//     public static Object[][] dataReader(String sheetName) throws IOException,InterruptedException,FileNotFoundException{
//         try {
//         String excelFilePath = ".\\datasrc\\userlogindata.xlsx";
//         FileInputStream fis = new FileInputStream(excelFilePath);
//         XSSFWorkbook workbook = new XSSFWorkbook(fis);
//         XSSFSheet sheet = workbook.getSheet(sheetName);
//         int row = sheet.getPhysicalNumberOfRows();
//         int column = sheet.getRow(0).getPhysicalNumberOfCells();
//         Object [][] data = new String[row-1][column];
// 		for(int i=0;i<row-1;i++) {
// 			for(int j=0;j<column;j++) {
// 				data[i][j]=sheet.getRow(i+1).getCell(j).getStringCellValue();
// 				System.out.println(data[i][j]);
// 			}
// 		}
//         workbook.close();
//         fis.close();
//         return data;
//         } catch (Exception e) {
//             System.out.println(" Excel File Not Found");
//                 e.printStackTrace();
//                 return null;
//         }
// 	}
//     public void waitForElement(WebElement ele, int timeout) {
// 		try {
// 			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
// 			wait.until(ExpectedConditions.visibilityOf(ele));
// 		} catch (Exception e) {
// 			System.out.println("Problem while waiting for the element:"+ele.getText());
// 			e.printStackTrace();
// 		}
// 	}

//     public void waitForElementclickable(WebElement ele, int timeout) {
// 		try {
// 			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
//             wait.until(ExpectedConditions.elementToBeClickable(ele));
// 		} catch (Exception e) {
// 			System.out.println("Problem while waiting for the element:"+ele.getText());
// 			e.printStackTrace();
// 		}
// 	}

//     @AfterMethod
//     public void closeDriver(Method method){
//         System.out.println("********  "+method.getName()+" Ended ***********");
//         driver.quit();
//     }
//     @AfterClass
//     public void tearDownExtentReport(){
//         if(extent!=null){
//             extent.flush();
//         }
//     }
//     protected void setTest(ExtentTest test){
//         this.test.set(test);
//     }
//     protected ExtentTest getTest(){
//         return this.test.get();
//     }
// }
