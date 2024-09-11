package com.demo.utils;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    // protected static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    public static void initReports() {
        ExtentSparkReporter spark = new ExtentSparkReporter(".\\src\\report\\report4.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    public static void createTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        extentTest.set(test);
    }

    public static void reportStep(String status, String desc) {
        try {
            ExtentTest test = extentTest.get();
            if (test == null) {
                throw new NullPointerException("ExtentTest instance is not initialized. Ensure createTest() is called before this method.");
            }

            switch (status.toUpperCase()) {
                case "PASS":
                    test.log(Status.PASS, desc);
                    break;
                case "FAIL":
                    WebDriver driver=BaseTest.driver.get();
                    String screenshot = ScreenshotUtil.captureBase64Screenshot(driver);
                    if (!screenshot.isEmpty()) {
                        test.log(Status.FAIL, desc, MediaEntityBuilder.createScreenCaptureFromBase64String(screenshot).build());
                    } else {
                        test.log(Status.FAIL, desc + " (Screenshot not available)");
                    }
                    break;
                case "INFO":
                    test.log(Status.INFO, desc);
                    break;
                case "SKIP":
                    test.log(Status.SKIP, desc);
                    break;
                default:
                    test.log(Status.WARNING, "Unknown status: " + status);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
//     private static String logScreenshot() {
//     try {
//         // WebDriver driver = driverThreadLocal.get();
//         WebDriver driver = BaseTest.driver; // Get the WebDriver instance from the BaseTest class
//         if (driver != null) {
//             return ScreenshotUtil.captureBase64Screenshot(driver);
//         } else {
//             System.out.println("WebDriver is not initialized.");
//             return "";
//         }
//     } catch (Exception e) {
//         e.printStackTrace();
//         return "";
//     }
// }
    // private static String logScreenshot() {
    //     // Implement screenshot capture logic
    //     // Return base64 string of the screenshot
    //     return ""; // Return empty string for now
    // }
}


// import java.util.HashMap;
// import java.util.Map;

// import com.aventstack.extentreports.ExtentReports;
// import com.aventstack.extentreports.ExtentTest;
// import com.aventstack.extentreports.MediaEntityBuilder;
// import com.aventstack.extentreports.Status;
// import com.aventstack.extentreports.reporter.ExtentSparkReporter;

// public class ExtentReportManager {
//     private static ExtentReports extent;
//     private static Map<String, ExtentTest> testMap = new HashMap<>();

//     public ExtentReportManager() {
//         ExtentSparkReporter spark = new ExtentSparkReporter("target/ExtentSparkReport.html");
//         extent = new ExtentReports();
//         extent.attachReporter(spark);
//     }

//     public void createTest(String methodName) {
//         ExtentTest test = extent.createTest(methodName);
//         testMap.put(methodName, test);
//     }

//     public void logPass(String methodName, String desc) {
//         ExtentTest test = testMap.get(methodName);
//         if (test != null) {
//             test.log(Status.PASS, desc);
//         }
//     }

//     public void logFail(String methodName, String desc, String base64Screenshot) {
//         ExtentTest test = testMap.get(methodName);
//         if (test != null) {
//             if (base64Screenshot != null) {
//                 test.log(Status.FAIL, desc, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
//             } else {
//                 test.log(Status.FAIL, desc + " (Screenshot not available)");
//             }
//         }
//     }

//     public void logInfo(String methodName, String desc) {
//         ExtentTest test = testMap.get(methodName);
//         if (test != null) {
//             test.log(Status.INFO, desc);
//         }
//     }

//     public void logSkip(String methodName, String desc) {
//         ExtentTest test = testMap.get(methodName);
//         if (test != null) {
//             test.log(Status.SKIP, desc);
//         }
//     }

//     public void flushReports() {
//         extent.flush();
//     }
// }




// import com.aventstack.extentreports.ExtentReports;
// import com.aventstack.extentreports.ExtentTest;
// import com.aventstack.extentreports.MediaEntityBuilder;
// import com.aventstack.extentreports.Status;
// import com.aventstack.extentreports.reporter.ExtentSparkReporter;
// public class ExtentReportManager {
//     private static ExtentReports extent;
//     private static ExtentTest test;

//     public ExtentReportManager() {
//         ExtentSparkReporter spark = new ExtentSparkReporter("target/ExtentSparkReport.html");
//         extent = new ExtentReports();
//         extent.attachReporter(spark);
//     }

//     public void createTest(String testName) {
//         test = extent.createTest(testName);
//     }

//     public void logPass(String desc) {
//         test.log(Status.PASS, desc);
//     }

//     public void logFail(String desc, String base64Screenshot) {
//         if (base64Screenshot != null) {
//             test.log(Status.FAIL, desc, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
//         } else {
//             test.log(Status.FAIL, desc + " (Screenshot not available)");
//         }
//     }

//     public void logInfo(String desc) {
//         test.log(Status.INFO, desc);
//     }

//     public void logSkip(String desc) {
//         test.log(Status.SKIP, desc);
//     }

//     public void flushReports() {
//         extent.flush();
//     }
// }
