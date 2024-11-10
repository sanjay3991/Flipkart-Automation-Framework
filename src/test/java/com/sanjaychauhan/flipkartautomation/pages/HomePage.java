package com.sanjaychauhan.flipkartautomation.pages;

import com.sanjaychauhan.flipkartautomation.wrappers.WebDriverWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomePage {

    private WebDriverWrapper driverWrapper;
    private static final Logger log = LoggerFactory.getLogger(HomePage.class);

    @FindBy(xpath = "//div[@class='JFPqaw']/span") 
    private WebElement loginCloseButton;

    @FindBy(xpath = "//input[contains(@placeholder , 'Search for Products')]") 
    private WebElement searchBar;

    @FindBy(xpath = "//input[contains(@placeholder , 'Search for Products')]/../preceding-sibling::button") 
    private WebElement searchButton;

    public HomePage(WebDriver driver) {
        this.driverWrapper = new WebDriverWrapper(driver); 
        PageFactory.initElements(driver, this); 
    }

    
    public void closeLoginModule() {
        log.info("Closing login module pop-up.");
        driverWrapper.clickElement(loginCloseButton); 
    }

 
    public void searchFor(String keyword) {
        log.info("Searching for keyword: " + keyword);
        driverWrapper.sendText(searchBar, keyword); 
        driverWrapper.clickElement(searchButton); 
        
    }
}
