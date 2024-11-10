package com.sanjaychauhan.flipkartautomation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private Properties properties;

    public ConfigReader() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties")) {
            properties.load(fis); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBaseURL() {
        return properties.getProperty("baseURL");
    }

    public String getBrowser() {
        return properties.getProperty("browser");
    }

    public int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicitWait"));
    }

    public int getExplicitWait() {
        return Integer.parseInt(properties.getProperty("explicitWait"));
    }

    public String getScreenshotPath() {
        return properties.getProperty("screenshotPath");
    }
}
