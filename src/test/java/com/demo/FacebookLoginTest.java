package com.demo;

import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import com.demo.utils.BaseTest;
import com.demo.utils.ExtentReportManager;

public class FacebookLoginTest extends BaseTest {
    public static String sheetName;
    // @BeforeMethod
    // @Parameters("browserkey")
    // public void beginLaunch(@Optional("chrome") String browserkey) throws MalformedURLException{
    //     setup(browserkey);
    //     System.out.println("******** FB LOGIN CLASS STARTED ON ==> "+browserkey+" ****************");
    // }
    
    @Test(dataProvider = "logData")
    @SheetNameA("facebook")
    public void FBLoginTest(String username, String password){
        try {
            WebDriver driverInstance = driver.get();
            driverInstance.get("https://www.facebook.com");
            Thread.sleep(5000);
            waitForElement(driverInstance.findElement(By.id("email")), 5);
            Assertion a=new Assertion();
            a.assertEquals(driverInstance.getTitle(), "Facebook – log in or sign up", "WRONG Page Title");
            ExtentReportManager.reportStep("PASS",driverInstance.getTitle());
            driverInstance.findElement(By.id("email")).sendKeys(username);
            driverInstance.findElement(By.id("pass")).sendKeys(password);
            driverInstance.findElement(By.name("login")).click();
            System.out.println(driverInstance.getTitle());
            // Thread.sleep(5000);
            a.assertTrue(driverInstance.getTitle().contains("Facebook"), "WRONG Page Title");
            ExtentReportManager.reportStep("PASS",driverInstance.getTitle());
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
    //     System.out.println("******** FB LOGIN CLASS ENDED ***********");
    //     driver.quit();
    // }

}
