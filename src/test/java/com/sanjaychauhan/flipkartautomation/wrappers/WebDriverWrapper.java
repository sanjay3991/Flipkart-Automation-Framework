package com.sanjaychauhan.flipkartautomation.wrappers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.sanjaychauhan.flipkartautomation.utils.ConfigReader;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class WebDriverWrapper {

    public WebDriver driver;
    private ConfigReader configReader;

    public WebDriverWrapper(WebDriver driver) {
        this.driver = driver;
        configReader = new ConfigReader();
    }

    public void clickElement(WebElement element) {
        WebElement clickableElement = waitForElementToBeClickable(element);
        if (clickableElement != null) {
            clickableElement.click();
        }
    }
    
    public void hoverAndStayOpen(WebElement elementToHover) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        String javaScript = "var event = new MouseEvent('mouseover', { bubbles: true, cancelable: true, view: window });"
                          + "arguments[0].dispatchEvent(event);";
        jsExecutor.executeScript(javaScript, elementToHover);
    }
    
    public boolean isProductAvailable(List<WebElement> productElements, String targetText) {
        for (WebElement element : productElements) {
            WebElement visibleElement = waitForElementToBeVisible(element);
            if (visibleElement != null && visibleElement.getText().equals(targetText)) {
                return true; 
            }
        }
        return false; 
    }
    
    public void sendText(WebElement element, String text) {
        WebElement visibleElement = waitForElementToBeVisible(element);
        if (visibleElement != null) {
            visibleElement.sendKeys(text);
        }
    }
    
    public WebElement getElementFromList(List<WebElement> elements, int index) {
        if (elements == null || elements.isEmpty()) {
            throw new IllegalArgumentException("The elements list is empty or null.");
        }

        if (index < 0 || index >= elements.size()) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for the list of size " + elements.size());
        }

        return elements.get(index);
    }

    public WebElement waitForElementToBeVisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(configReader.getExplicitWait()));
        try {
            return wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            System.out.println("Element not visible: " + element);
            return null;
        }
    }

    public WebElement waitForElementToBeClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(configReader.getExplicitWait()));
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            System.out.println("Element not clickable: " + element);
            return null;
        }
    }

    public void waitForPageLoad() {
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(configReader.getImplicitWait()));
    }

    public String getElementText(WebElement element) {
        WebElement visibleElement = waitForElementToBeVisible(element);
        return visibleElement != null ? visibleElement.getText() : "";
    }
    
    public String getElementTextUsingJS(WebElement element) {
        WebElement presentElement = waitForElementToBeVisible(element);
        if (presentElement != null) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return (String) js.executeScript("return arguments[0].textContent;", presentElement);
        }
        return "";
    }

    public String getElementAttribute(WebElement element, String attribute) {
        WebElement visibleElement = waitForElementToBeVisible(element);
        return visibleElement != null ? visibleElement.getAttribute(attribute) : "";
    }

    public void selectDropdownByVisibleText(WebElement element, String visibleText) {
        WebElement visibleElement = waitForElementToBeVisible(element);
        if (visibleElement != null) {
            visibleElement.sendKeys(visibleText);
        }
    }

    public List<WebElement> getElements(List<WebElement> elements) {
        return elements;
    }

    public void switchToFrame(WebElement frame) {
        WebElement visibleFrame = waitForElementToBeVisible(frame);
        if (visibleFrame != null) {
            driver.switchTo().frame(visibleFrame);
        }
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public void acceptAlert() {
        try {
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            System.out.println("No alert present.");
        }
    }
    
    public void dismissAlert() {
        try {
            driver.switchTo().alert().dismiss();
        } catch (Exception e) {
            System.out.println("No alert present.");
        }
    }

    public boolean isElementDisplayed(WebElement element) {
        WebElement visibleElement = waitForElementToBeVisible(element);
        return visibleElement != null && visibleElement.isDisplayed();
    }

    public boolean isElementEnabled(WebElement element) {
        WebElement visibleElement = waitForElementToBeVisible(element);
        return visibleElement != null && visibleElement.isEnabled();
    }

    public void scrollToElement(WebElement element) {
        WebElement visibleElement = waitForElementToBeVisible(element);
        if (visibleElement != null) {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", visibleElement);
        }
    }

	public void switchToTabWithElement(WebElement element) {
		List<String> tabs = new ArrayList<>(driver.getWindowHandles());

        for (String tab : tabs) {
            driver.switchTo().window(tab);
            try {
                if (element.isDisplayed()) {
                    System.out.println("Switched to tab with specified element.");
                    return;
                }
            } catch (NoSuchElementException e) {
                
            }
        }
        
        throw new NoSuchElementException("The specified element was not found in any open tab.");
		
	}

	public void switchToTabByIndex(int tabIndex) {
		List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        if (tabIndex >= 0 && tabIndex < tabs.size()) {
            driver.switchTo().window(tabs.get(tabIndex));
            System.out.println("Switched to tab at index: " + tabIndex);
        } else {
            throw new IndexOutOfBoundsException("Tab index " + tabIndex + " is out of bounds. Number of open tabs: " + tabs.size());
        }		
	}
	
	public void clickFirstVisibleElement(WebElement element1, WebElement element2) {
        try {
            if (element1.isDisplayed()) {
                clickElement(element1);
            } else {
            	clickElement(element2);
            } 
        } catch (NoSuchElementException e) {
           System.out.print("No element is present ");
        }
    }
}
