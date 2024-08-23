package com.demo;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

public class FacebookLoginTest extends BaseTest {
    public static String sheetName;
    @BeforeMethod
    @Parameters("browserkey")
    public void beginLaunch(@Optional("chrome") String browserkey) throws MalformedURLException{
        setup(browserkey);
        System.out.println("******** FB LOGIN CLASS STARTED ON ==> "+browserkey+" ****************");
    }
    
    @Test(dataProvider = "logData")
    @SheetNameA("facebook")
    public void FBLoginTest(String username, String password){
        try {
            driver.get("https://www.facebook.com");
            waitForElement(driver.findElement(By.id("email")), 5);
            Assertion a=new Assertion();
            a.assertEquals(driver.getTitle(), "Facebook – log in or sign up", "WRONG Page Title");

            driver.findElement(By.id("email")).sendKeys(username);
            driver.findElement(By.id("pass")).sendKeys(password);
            driver.findElement(By.name("login")).click();
            System.out.println(driver.getTitle());
            // Thread.sleep(5000);
            a.assertTrue(driver.getTitle().contains("Facebook"), "WRONG Page Title");
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

    @AfterMethod
    public void closeDriver(){
        System.out.println("******** FB LOGIN CLASS ENDED ***********");
        driver.quit();
    }

}
