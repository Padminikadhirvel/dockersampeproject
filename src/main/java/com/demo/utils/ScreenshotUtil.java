package com.demo.utils;


import java.util.Base64;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

    public static String captureBase64Screenshot(WebDriver driver) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            return Base64.getEncoder().encodeToString(ts.getScreenshotAs(OutputType.BYTES));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}


// import org.openqa.selenium.OutputType;
// import org.openqa.selenium.TakesScreenshot;
// import org.openqa.selenium.WebDriver;

// public class ScreenshotUtil {
//     public static String captureBase64Screenshot(WebDriver driver) {
//         try {
//             return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
//         } catch (Exception e) {
//             e.printStackTrace();
//             return "";
//         }
//     }
// }
