package com.sanjaychauhan.flipkartautomation.base;

import com.sanjaychauhan.flipkartautomation.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class BaseTest {

    public WebDriver driver;
    private ConfigReader configReader;

    @BeforeClass
    public void setup() {
        configReader = new ConfigReader();
        String browser = configReader.getBrowser();
        String baseURL = configReader.getBaseURL();
        int implicitWait = configReader.getImplicitWait();

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;

            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }

     
        driver.get(baseURL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

 
    @AfterMethod
    public void captureScreenshotOnFailure(ITestResult result) {
    	if (ITestResult.FAILURE == result.getStatus()) {
            captureScreenshot(result.getName());  // Capture screenshot on failure
        } else if (ITestResult.SUCCESS == result.getStatus()) {
            System.out.println("Test Passed: " + result.getName());
        } else if (ITestResult.SKIP == result.getStatus()) {
            System.out.println("Test Skipped: " + result.getName());
        }
    }

    public void captureScreenshot(String testName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = System.getProperty("user.dir") + "/src/test/resources/screenshots/" + testName + ".png";
        File destination = new File(screenshotPath);

        try {
            FileHandler.copy(screenshot, destination);
            System.out.println("Screenshot saved at: " + screenshotPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
