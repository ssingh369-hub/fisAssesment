import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class EbayTest {

    public static void main(String[] args) {
        
        // Step 1: Open browser
        // Set the path to the chromedriver executable
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver"); // Update path to chromedriver
        
        // Step 2: Initialize the ChromeDriver
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);

        try {
            // Step 3: Navigate to ebay.com
            driver.get("https://www.ebay.com");

            // Step 4: Search for 'book'
            WebElement searchBox = driver.findElement(By.name("_nkw"));
            searchBox.sendKeys("book");
            searchBox.sendKeys(Keys.RETURN);  // Simulate pressing Enter

            // Step 5: Wait for results to load
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".s-item__link")));

            // Step 6: Click on the first book in the list
            WebElement firstBook = driver.findElement(By.cssSelector(".s-item__link"));
            firstBook.click();

            // Step 7: Wait for the item listing page to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("atcRedesignId_btn")));

            // Step 8: In the item listing page, click on 'Add to cart'
            WebElement addToCartButton = driver.findElement(By.id("atcRedesignId_btn"));
            addToCartButton.click();

            // Step 9: Wait for the cart to be updated
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gh-cart-i")));

            // Step 10: Verify the cart has been updated
            WebElement cartIcon = driver.findElement(By.id("gh-cart-i"));
            String cartText = cartIcon.getText();
            
            // Expected cart text should be "1" (if it's the first item added)
            if (cartText.equals("1")) {
                System.out.println("Test Passed: The cart has been updated with 1 item.");
            } else {
                System.out.println("Test Failed: The cart text is: " + cartText);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Test Failed due to exception: " + e.getMessage());
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
