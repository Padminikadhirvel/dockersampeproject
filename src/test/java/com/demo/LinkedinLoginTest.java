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

public class LinkedinLoginTest extends BaseTest{
    public static String sheetName;
    @BeforeMethod
    @Parameters("browserkey")
    public void beginLaunch(@Optional("chrome") String browserkey) throws MalformedURLException{
        setup(browserkey);
        System.out.println("******** LINKEDIN LOGIN CLASS STARTED ON ==> "+browserkey+" ****************");
    }
    
    @Test(dataProvider = "logData")
    @SheetNameA("LinkedIn")
    public void LinkLoginTest(String username, String password){
        try {
            driver.get("https://www.linkedin.com");
            waitForElement(driver.findElement(By.xpath("//div/a[2]")),5);
            Assertion a=new Assertion();
            a.assertEquals(driver.getTitle(), "LinkedIn: Log In or Sign Up", "WRONG Page Title");
            driver.findElement(By.xpath("//div/a[2]")).click();
            waitForElement(driver.findElement(By.xpath("//h1[contains(text(), 'Sign in')]")), 5);
            driver.findElement(By.id("username")).sendKeys(username);
            driver.findElement(By.id("password")).sendKeys(password);
            driver.findElement(By.xpath("//button[contains(text(), 'Sign in')]")).click();
            System.out.println(driver.getTitle());
            // Thread.sleep(5000);
            a.assertTrue(driver.getTitle().contains("Feed | LinkedIn"), "WRONG Page Title");
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
        System.out.println("******** LINKEDIN LOGIN CLASS ENDED ***********");
        driver.quit();
    }

}
