package com.sanjaychauhan.flipkartautomation.tests;


import com.sanjaychauhan.flipkartautomation.base.BaseTest;
import com.sanjaychauhan.flipkartautomation.pages.HomePage;
import com.sanjaychauhan.flipkartautomation.pages.SearchResultsPage;
import com.sanjaychauhan.flipkartautomation.pages.ProductPage;
import com.sanjaychauhan.flipkartautomation.pages.CartPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Listeners(com.sanjaychauhan.listeners.ExtentReportListener.class)
public class TC_01_flipkart_End_to_End extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(TC_01_flipkart_End_to_End.class);
    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    private ProductPage productPage;
    private CartPage cartPage;

    @Test(description = "Open Flipkart and close the login module", priority = 1)
    public void openFlipkartAndCloseLoginModule() {
        log.info("Starting test: Open Flipkart and close the login module");
        homePage = new HomePage(driver);
        log.info("Flipkart homepage opened");
        homePage.closeLoginModule();
        log.info("Login module closed successfully");
    }

    @Test(description = "Search for 'mobile' and verify results message", 
          priority = 2, dependsOnMethods = "openFlipkartAndCloseLoginModule",enabled=true)
    public void searchForMobileAndVerifyResults() {
        log.info("Starting test: Search for 'mobile' and verify results message");
        log.info("Click on Global Search Module and Search for Mobile");
        homePage.searchFor("mobile");
        searchResultsPage = new SearchResultsPage(driver);
        String actualMessage = searchResultsPage.getResultsMessage();
        
        Assert.assertTrue(actualMessage.startsWith("Showing 1 – 24 of"), "Prefix mismatch!");
        Assert.assertTrue(actualMessage.endsWith("results for \"mobile\""), "Suffix mismatch!");

        log.info("Search results message verified successfully");
    }

    @Test(description = "Add 10th and 11th phones to compare tray and verify", 
          priority = 3, dependsOnMethods = "searchForMobileAndVerifyResults",enabled=true)
    public void addPhonesToCompareAndVerify() {
        log.info("Starting test: Add 10th and 11th phones to compare tray and verify");
        searchResultsPage.selectProductForComparison(10);
        log.info("10th phone selected for comparison");
        searchResultsPage.selectProductForComparison(11);
        log.info("11th phone selected for comparison");
        Assert.assertTrue(searchResultsPage.isProductAddedToCompare(10), "10th phone not added to compare tray");
        Assert.assertTrue(searchResultsPage.isProductAddedToCompare(11), "11th phone not added to compare tray");
        log.info("Comparison tray verified for 10th and 11th phones successfully");
    }

    @Test(description = "Open 10th phone's product page and add to cart", 
          priority = 4, dependsOnMethods = "addPhonesToCompareAndVerify",enabled=true)
    public void openProductPageAndAddToCart() throws InterruptedException {
        log.info("Starting test: Open 10th phone's product page and add to cart");
        searchResultsPage.openProductPage(10);
        log.info("Navigated to 10th product's page");
        searchResultsPage.addToCart();
        productPage = new ProductPage(driver);
        productPage.NavigateBack();
        Assert.assertTrue(productPage.isAddedToCart(), "Add to cart failed");
        log.info("Product successfully added to cart");
    }

    @Test(description = "Verify total amount in cart matches product page", 
          priority = 5, dependsOnMethods = "openProductPageAndAddToCart",enabled=true)
    public void verifyCartTotalAmount() {
        log.info("Starting test: Verify total amount in cart matches product page");
        productPage.goToCart();
        double productPrice = searchResultsPage.getProductPrice(10);
        cartPage = new CartPage(driver);
        double cartTotal = searchResultsPage.getCartTotal();
        log.info("Expected :"+productPrice+" Actual :"+cartTotal);
        Assert.assertEquals(cartTotal, productPrice, "Cart total amount does not match product price");
        log.info("Cart total amount verified successfully");
    }

    @Test(description = "Increase product quantity in cart and verify popup message", 
          priority = 6, dependsOnMethods = "verifyCartTotalAmount",enabled=true)
    public void increaseProductQuantityAndVerifyPopup() throws InterruptedException {
        log.info("Starting test: Increase product quantity in cart and verify popup message");
        searchResultsPage.increaseProductQuantity(1);
        String expectedMessage = "You've changed '" + productPage.getProductName() + "' QUANTITY to '2'";
        System.out.println("Expected Product Title :"+expectedMessage);
        System.out.println("Actual  Product  Title :"+searchResultsPage.getPopupMessage());
        Assert.assertEquals(searchResultsPage.getPopupMessage(), expectedMessage, "Popup message mismatch");
        log.info("Quantity increase popup message verified successfully");
    }

    @Test(description = "Remove product from cart and verify messages", 
          priority = 7, dependsOnMethods = "increaseProductQuantityAndVerifyPopup",enabled=true)
    public void removeProductFromCartAndVerify() throws InterruptedException {
        log.info("Starting test: Remove product from cart and verify messages");
        searchResultsPage.removeProduct();
        log.info("Product removed from cart");
        Assert.assertTrue(searchResultsPage.isCartEmpty(), "Cart is not empty after removing product");
        String expectedEmptyMessage = "Missing Cart items? Login to see the items you added previously";
        log.info("Expected :"+expectedEmptyMessage);
        log.info("Actual   :"+searchResultsPage.getEmptyCartMessage());
        Assert.assertEquals(searchResultsPage.getEmptyCartMessage(), expectedEmptyMessage, "Empty cart message mismatch");
        log.info("Empty cart message verified successfully");
    }
}
