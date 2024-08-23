package com.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Optional;

// import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
    public WebDriver driver;

    public void setup(@Optional String browserkey) throws MalformedURLException{
        // String browserType="chrome";
        System.out.println("Received browserkey: " + browserkey);
        // DesiredCapabilities capabilities=new DesiredCapabilities();
        MutableCapabilities capabilities = new MutableCapabilities();
        if(browserkey.equalsIgnoreCase("chrome")){
            capabilities.setCapability("browserName", "chrome");
            // capabilities.setBrowserName("chrome");
            capabilities.setCapability("platformName", "LINUX");
        }else if(browserkey.equalsIgnoreCase("firefox")){
            capabilities.setCapability("browserName", "firefox");
            // capabilities.setBrowserName("firefox");
            capabilities.setCapability("platformName", "LINUX");
        }else{
            capabilities.setCapability("browserName", "edge");
            // capabilities.setBrowserName("edge");
        }
        driver=new RemoteWebDriver(new URL("http://localhost:3000/wd/hub"), capabilities);
    }

    // public void setup(String browsername){
    //     try {
    //         System.setProperty("webdriver.http.factory", "jdk-http-client");
    //     if (browsername.equals("chrome")) {
	// 		WebDriverManager.chromedriver().setup();
	// 		driver = new ChromeDriver();
	// 		// clearChromeCacheAndCookies(driver);
	// 	} else if (browsername.equals("edge")) {
	// 		WebDriverManager.edgedriver().setup();
	// 		driver = new EdgeDriver();
	// 	} else if (browsername.equals("firefox")) {
	// 		WebDriverManager.firefoxdriver().setup();
	// 		driver = new FirefoxDriver();
	// 	}
	// 		driver.manage().window().maximize();
	// 		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 	}
    // }

    public static Object[][] dataReader(String sheetName) throws IOException,InterruptedException,FileNotFoundException{
        try {
        String excelFilePath = "D:\\Docker_Practice\\Docker_SeleniumGrid\\docker_seleniumgrid_withdata\\data\\userlogindata.xlsx";
        FileInputStream fis = new FileInputStream(excelFilePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet(sheetName);
        int row = sheet.getPhysicalNumberOfRows();
        int column = sheet.getRow(0).getPhysicalNumberOfCells();
        Object [][] data = new String[row-1][column];
		for(int i=0;i<row-1;i++) {
			for(int j=0;j<column;j++) {
				data[i][j]=sheet.getRow(i+1).getCell(j).getStringCellValue();
				System.out.println(data[i][j]);
			}
		}
        workbook.close();
        fis.close();
        return data;
        } catch (Exception e) {
            System.out.println(" Excel File Not Found");
                e.printStackTrace();
                return null;
        }
	}
    public void waitForElement(WebElement ele, int timeout) {
		try {
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
			wait.until(ExpectedConditions.visibilityOf(ele));
		} catch (Exception e) {
			System.out.println("Problem while waiting for the element:"+ele.getText());
			e.printStackTrace();
		}
	}
}
