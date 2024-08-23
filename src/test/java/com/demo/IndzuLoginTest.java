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

public class IndzuLoginTest extends BaseTest {
    public static String sheetName;
    @BeforeMethod
    @Parameters("browserkey")
    public void beginLaunch(@Optional("chrome") String browserkey) throws MalformedURLException{
        setup(browserkey);
        System.out.println("******** INDZU LOGIN CLASS STARTED ON ==> "+browserkey+" ****************");
    }
    
    @Test(dataProvider = "logData")
    @SheetNameA("Indzu")
    public void IndzuportalLoginTest(String username, String password1){
        try {
            driver.get("https://account.indzu.com");
            // Thread.sleep(6000);
            waitForElement(driver.findElement(By.xpath("//div/h5[contains(text(),'Login')]")),10);
            System.out.println(driver.getTitle());
            Assertion a=new Assertion();
            a.assertEquals(driver.getTitle(), "Indzu account");
            driver.findElement(By.xpath("//input[@id='email']")).sendKeys(username);
            driver.findElement(By.xpath("//button[@type='submit']")).click();
            // Thread.sleep(3000);
            waitForElement(driver.findElement(By.xpath("//input[@id='password']")),10);
            driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password1);
            driver.findElement(By.xpath("//button[@type='submit']")).click();
            // waitForElement(driver.findElement(By.xpath("//div[@class='Toastify__toast-container Toastify__toast-container--bottom-right']/div/div[1]")),10);
            System.out.println(driver.getTitle());
            // Thread.sleep(3000);
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
        System.out.println("******** INDZU LOGIN CLASS ENDED ***********");
        driver.quit();
    }

}
