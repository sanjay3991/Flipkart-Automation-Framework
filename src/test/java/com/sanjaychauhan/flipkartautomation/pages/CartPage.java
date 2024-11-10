package com.sanjaychauhan.flipkartautomation.pages;

import com.sanjaychauhan.flipkartautomation.wrappers.WebDriverWrapper;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CartPage {

    private WebDriverWrapper driverWrapper;
    private static final Logger log = LoggerFactory.getLogger(CartPage.class);
    

    public CartPage(WebDriver driver) {
        this.driverWrapper = new WebDriverWrapper(driver); 
    }

	
	
    
}
