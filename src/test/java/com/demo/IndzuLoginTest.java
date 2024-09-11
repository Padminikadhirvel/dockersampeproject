package com.demo;

import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import com.demo.utils.BaseTest;
import com.demo.utils.ExtentReportManager;

public class IndzuLoginTest extends BaseTest {
    public static String sheetName;
    // @BeforeMethod
    // @Parameters("browserkey")
    // public void beginLaunch(@Optional("chrome") String browserkey) throws MalformedURLException{
    //     setup(browserkey);
    //     System.out.println("******** INDZU LOGIN CLASS STARTED ON ==> "+browserkey+" ****************");
    // }
    
    @Test(dataProvider = "logData")
    @SheetNameA("Indzu")
    public void IndzuportalLoginTest(String username, String password1){
        try {
            WebDriver driverInstance = driver.get();
            driverInstance.get("https://account.indzu.com/login");
            Thread.sleep(6000);
            waitForElement(driverInstance.findElement(By.xpath("//div/h5[contains(text(),'Login')]")),10);
            System.out.println(driverInstance.getTitle());
            Assertion a=new Assertion();
            a.assertEquals(driverInstance.getTitle(), "Indzu account");
            ExtentReportManager.reportStep("PASS",driverInstance.getTitle());
            driverInstance.findElement(By.xpath("//input[@id='email']")).sendKeys(username);
            Thread.sleep(6000);
            WebElement submit=driverInstance.findElement(By.xpath("//button[@type='submit']"));
            waitForElementClickable(submit, 5);
            submit.click();
            // Thread.sleep(3000);
            waitForElement(driverInstance.findElement(By.id("password")),20);
            driverInstance.findElement(By.id("password")).sendKeys(password1);
            waitForElementClickable(submit, 5);
            submit.click();
            // driver.findElement(By.xpath("//button[@type='submit']")).click();
            WebElement toastmsg=driverInstance.findElement(By.xpath("//div[@class='Toastify__toast-container Toastify__toast-container--bottom-right']/div/div[1]"));
            // waitForElement(driver.findElement(By.xpath("//div[@class='Toastify__toast-container Toastify__toast-container--bottom-right']/div/div[1]")),10);
            waitForElement(toastmsg, 10);
            System.out.println(toastmsg.getText());
            ExtentReportManager.reportStep("FAIL",driverInstance.getTitle());
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

    // @AfterMethod
    // public void closeDriver(){
    //     if(driver != null){
    //         System.out.println("******** INDZU LOGIN CLASS ENDED ***********");
    //         driver.quit();
    //     }
        
    // }

}
