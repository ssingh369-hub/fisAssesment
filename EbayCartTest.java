package com.qa.su;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class EbayCartTest {
    
    public static void main(String[] args) {
        // Step 1: Set the path for ChromeDriver (for MacOS)
        System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver"); // Update this path if needed
        
        // Step 2: Initialize Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");  // Optional: Open browser in maximized mode

        // Step 3: Launch the browser
        WebDriver driver = new ChromeDriver(options);
        
        try {
            // Step 4: Navigate to eBay
            driver.get("https://www.ebay.com");

            // Step 5: Search for 'book'
            WebElement searchBox = driver.findElement(By.name("_nkw"));
            searchBox.sendKeys("book");
            searchBox.sendKeys(Keys.RETURN);  // Press Enter key to submit search

            // Step 6: Wait for search results to load
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".s-item__link")));

            // Step 7: Click on the first book in the list
            WebElement firstBook = driver.findElement(By.cssSelector(".s-item__link"));
            firstBook.click();

            // Step 8: Wait for the item listing page to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("atcRedesignId_btn")));

            // Step 9: Click on 'Add to Cart'
            WebElement addToCartButton = driver.findElement(By.id("atcRedesignId_btn"));
            addToCartButton.click();

            // Step 10: Wait for the cart to be updated
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gh-cart-i")));

            // Step 11: Verify the cart has been updated
            WebElement cartIcon = driver.findElement(By.id("gh-cart-i"));
            String cartText = cartIcon.getText();

            // Step 12: Assert that the cart now contains 1 item
            if (cartText.equals("1")) {
                System.out.println("Test Passed: The cart has been updated with 1 item.");
            } else {
                System.out.println("Test Failed: The cart text is: " + cartText);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Test failed due to: " + e.getMessage());
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
