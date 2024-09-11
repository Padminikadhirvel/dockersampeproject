package com.demo;

import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import com.demo.utils.BaseTest;
import com.demo.utils.ExtentReportManager;

public class LinkedinLoginTest extends BaseTest{
    public static String sheetName;
    // @BeforeMethod
    // @Parameters("browserkey")
    // public void beginLaunch(@Optional("chrome") String browserkey) throws MalformedURLException{
    //     setup(browserkey);
    //     System.out.println("******** LINKEDIN LOGIN CLASS STARTED ON ==> "+browserkey+" ****************");
    // }
    
    @Test(dataProvider = "logData")
    @SheetNameA("LinkedIn")
    public void LinkLoginTest(String username, String password){
        try {
            WebDriver driverInstance = driver.get();
            driverInstance.get("https://www.linkedin.com");
            Thread.sleep(5000);
            waitForElement(driverInstance.findElement(By.xpath("//div/a[2]")),5);
            Assertion a=new Assertion();
            a.assertEquals(driverInstance.getTitle(), "LinkedIn: Log In or Sign Up", "WRONG Page Title");
             ExtentReportManager.reportStep("PASS",driverInstance.getTitle());
             driverInstance.findElement(By.xpath("//div/a[2]")).click();
            waitForElement(driverInstance.findElement(By.xpath("//h1[contains(text(), 'Sign in')]")), 5);
            driverInstance.findElement(By.id("username")).sendKeys(username);
            driverInstance.findElement(By.id("password")).sendKeys(password);
            driverInstance.findElement(By.xpath("//button[contains(text(), 'Sign in')]")).click();
            System.out.println(driverInstance.getTitle());
            ExtentReportManager.reportStep("FAIL",driverInstance.getTitle());
            a.assertTrue(driverInstance.getTitle().contains("Feed | LinkedIn"), "WRONG Page Title");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @DataProvider(name = "logData")
	public Object[][] getExcelData(Method method) throws Exception{
        String sheetName=null;
        if (method.isAnnotationPresent(SheetNameA.class)) {
            SheetNameA annotationA = method.getAnnotation(SheetNameA.class);
            sheetName = annotationA.value();
        } else if (method.isAnnotationPresent(SheetNameB.class)) {
            SheetNameB annotationB = method.getAnnotation(SheetNameB.class);
            sheetName = annotationB.value();
        }
		Object[][] data=dataReader(sheetName);
		return data;
    }

    // @AfterMethod
    // public void closeDriver(){
    //     System.out.println("******** LINKEDIN LOGIN CLASS ENDED ***********");
    //     driver.quit();
    // }

}
