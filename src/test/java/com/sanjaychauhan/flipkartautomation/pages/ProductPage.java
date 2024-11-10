package com.sanjaychauhan.flipkartautomation.pages;

import com.sanjaychauhan.flipkartautomation.wrappers.WebDriverWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductPage {

    private WebDriverWrapper driverWrapper;
    private static final Logger log = LoggerFactory.getLogger(ProductPage.class);
    
    
    @FindBy(xpath = "//li[@class='col col-6-12 ']/button")
    private WebElement GoToCartButton;
    
    @FindBy(xpath = "//span[text() = 'Cart']")
    private WebElement CartButton;
    
    @FindBy(xpath = "//a[@class='T2CNXf QqLTQ-']")
    private WebElement CartProductTitle;
    
    @FindBy(xpath = "//button[@class='QqFHMw vslbG+ In9uk2 JTo6b7']")
    private WebElement addToCartButtonSymbol;
    

    public ProductPage(WebDriver driver) {
        this.driverWrapper = new WebDriverWrapper(driver);
        PageFactory.initElements(driver, this);
    }


	public void NavigateBack() throws InterruptedException {
		Thread.sleep(3000);
		driverWrapper.driver.navigate().back();
		Thread.sleep(3000);
		
	}
	
	public boolean isAddedToCart() {
		log.info("Verify Button display GO TO CART or Display Symbol");
		String GotoCart = driverWrapper.getElementText(GoToCartButton);
		boolean addToCartSymbol = driverWrapper.isElementDisplayed(addToCartButtonSymbol);
		if(GotoCart.contains("GO TO CART") || addToCartSymbol) {
			return true;
		}
		return false;
	}
	
	public void goToCart() {
		driverWrapper.clickElement(CartButton);
	}

	public String getProductName() {
		return driverWrapper.getElementText(CartProductTitle);
	}
	
	
	
}

