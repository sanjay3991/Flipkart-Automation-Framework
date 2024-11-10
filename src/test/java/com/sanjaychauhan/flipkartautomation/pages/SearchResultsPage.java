package com.sanjaychauhan.flipkartautomation.pages;

import com.sanjaychauhan.flipkartautomation.wrappers.WebDriverWrapper;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class SearchResultsPage {

    private WebDriverWrapper driverWrapper;
    private static final Logger log = LoggerFactory.getLogger(SearchResultsPage.class);
    
    @FindBy(xpath = "//span[@class='BUOuZu']")
    private WebElement searchResultTitle; 
    
    @FindBy(xpath = "//div[@class='Otbq5D']/div[@class='qaR90o']/div/span")
    private List<WebElement> productCheckbox;
    
    @FindBy(xpath = "//div[@class='KzDlHZ']")
    private List<WebElement> productTitle;
    
    @FindBy(xpath = "//div[@class='RWwfw5']")
    private List<WebElement> productsInCart;
    
    @FindBy(xpath = "//span[text() = 'COMPARE']")
    private WebElement compareBtn;
    
    @FindBy(xpath = "//button[@class='QqFHMw vslbG+ In9uk2 JTo6b7']")
    private WebElement addToCartButton1;
    
    @FindBy(xpath = "//button[@class='QqFHMw vslbG+ In9uk2']")
    private WebElement addToCartButton2;
    
    @FindBy(xpath = "//div[@class='Nx9bqj _4b5DiR']")
    private List<WebElement> ProductPrice;
    
    @FindBy(xpath = "(//div[@class='_1Y9Lgu']/span)[2]")
    private WebElement PriceinCart;
    
    @FindBy(xpath = "(//button[@class='LcLcvv'])[2]")
    private WebElement increaseByOne;
    
    @FindBy(xpath = "//div[@class = 'eIDgeN']")
    private WebElement popupMessage;
  
    @FindBy(xpath = "//div[text() = 'Remove']")
    private WebElement RemoveProduct;
  
    @FindBy(xpath = "//div[@class='sBxzFz fF30ZI A0MXnh']")
    private WebElement RemoveProductOptionPopup;
  
    @FindBy(xpath = "//div[@class=\"sBxzFz fF30ZI t9UCZh\"]")
    private WebElement CancelProductOptionPopup;
  
    @FindBy(xpath = "//div[@class='s2gOFd']")
    private WebElement MissingCartItemsMsg;
  
    @FindBy(xpath = "//div[@class='orqM3-']")
    private WebElement LogintoseeMsg;

    public SearchResultsPage(WebDriver driver) {
        this.driverWrapper = new WebDriverWrapper(driver); 
        PageFactory.initElements(driver, this); 
    }
    
    public void openProductPage(int num) {
    	log.info("Open product for description from list num "+num);
    	WebElement ProductTitle = driverWrapper.getElementFromList(productTitle, num);
    	driverWrapper.clickElement(ProductTitle);
    }
    
    public void selectProductForComparison(int num) {
    	log.info("Select product from list num "+num);
    	WebElement checkbox = driverWrapper.getElementFromList(productCheckbox, num);
    	driverWrapper.clickElement(checkbox);
    	
    }
    
    public boolean isProductAddedToCompare(int num) {
    	WebElement ProductTitle = driverWrapper.getElementFromList(productTitle, num);
    	String producttitletext = driverWrapper.getElementText(ProductTitle);
    	driverWrapper.hoverAndStayOpen(compareBtn);
    	return driverWrapper.isProductAvailable(productsInCart, producttitletext);
    	
    	
    }
    
    public String getResultsMessage() {
    	log.info("Message Displayed after Search for Product");
    	return driverWrapper.getElementText(searchResultTitle);
    	
    }
    
    public void addToCart() throws InterruptedException {
    	
		driverWrapper.switchToTabByIndex(1);
		log.info("Adding the product to the cart.");
		Thread.sleep(3000);
		driverWrapper.clickElement(addToCartButton1);
		Thread.sleep(3000);
		driverWrapper.clickElement(addToCartButton2);
		Thread.sleep(3000);
	}

	public double getProductPrice(int num) {
		log.info("product price from list num "+num);
    	driverWrapper.switchToTabByIndex(0);
    	WebElement price = driverWrapper.getElementFromList(ProductPrice, num);
    	String priceText = driverWrapper.getElementText(price);
        String numericPrice = priceText.replaceAll("[^0-9.]", "");

        try {
            return Double.parseDouble(numericPrice);
        } catch (NumberFormatException e) {
            log.error("Invalid price format: " + priceText);
            return 0.0; 
        }
	}
	public double getCartTotal() {
        log.info("Fetching Price from Cart");
        driverWrapper.switchToTabByIndex(1);
    	String priceText = driverWrapper.getElementTextUsingJS(PriceinCart);
        String numericPrice = priceText.replaceAll("[^0-9.]", "");

        try {
            return Double.parseDouble(numericPrice);
        } catch (NumberFormatException e) {
            log.error("Invalid price format: " + priceText);
            return 0.0; 
        }
    }
	
	public void increaseProductQuantity(int num) throws InterruptedException {
		for(int i=0 ; i<num ; i++) {
    		driverWrapper.clickElement(increaseByOne);
    	}
		Thread.sleep(1000);
	}
	
	public String getPopupMessage() {
		return driverWrapper.getElementText(popupMessage);
	}
	
	public void removeProduct() throws InterruptedException {
		driverWrapper.clickElement(RemoveProduct);
	    System.out.println("Verifying if popup displays 'Remove' and 'Cancel' options");
	    boolean isPopupDisplayed = verifyRemoveOrCancelBtnPopup();
	    Assert.assertTrue(isPopupDisplayed, "Popup does not display 'Remove' and 'Cancel' options");
	    System.out.println("Popup displays 'Remove' and 'Cancel' options");
	    driverWrapper.clickElement(RemoveProductOptionPopup);
	    Thread.sleep(3000);
	}
	
	public boolean verifyRemoveOrCancelBtnPopup() {
		String remove = driverWrapper.getElementText(RemoveProductOptionPopup);
		String cancel = driverWrapper.getElementText(CancelProductOptionPopup);
		if(remove.equalsIgnoreCase("Remove") && cancel.equalsIgnoreCase("Cancel")) {
			return true;
		}
		return false;
	}
	
	public boolean isCartEmpty() {
		return !driverWrapper.isElementDisplayed(PriceinCart);
	}
	public String getEmptyCartMessage() {
		return driverWrapper.getElementText(MissingCartItemsMsg)+" "+driverWrapper.getElementText(LogintoseeMsg);
	}
}
