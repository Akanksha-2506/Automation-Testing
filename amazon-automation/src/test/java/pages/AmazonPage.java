package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AmazonPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By searchBox = By.id("twotabsearchtextbox");
    private By searchButton = By.id("nav-search-submit-button");
    private By searchResults = By.cssSelector("div[data-component-type='s-search-result']");
    private By addToCartButton = By.id("add-to-cart-button");
    private By priceWhole = By.cssSelector("span.a-price-whole");
    private By priceFraction = By.cssSelector("span.a-price-fraction");
    private By cartConfirmation = By.id("NATC_SMART_WAGON_CONF_MSG_SUCCESS");
    private By closeCartPopup = By.cssSelector("button[data-action='a-popover-close']");

    public AmazonPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void openAmazon() {
        driver.get("https://www.amazon.com");
    }

    public void searchFor(String query) {
        WebElement box = wait.until(ExpectedConditions.elementToBeClickable(searchBox));
        box.clear();
        box.sendKeys(query);
        driver.findElement(searchButton).click();
    }

    public void clickFirstResult() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(searchResults));
        List<WebElement> results = driver.findElements(searchResults);
        for (WebElement result : results) {
            List<WebElement> titles = result.findElements(By.cssSelector("h2 a"));
            if (!titles.isEmpty()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", titles.get(0));
                break;
            }
        }
    }

    public String getProductPrice() {
        try {
            WebElement whole = wait.until(ExpectedConditions.presenceOfElementLocated(priceWhole));
            String wholeText = whole.getText().replaceAll("[^0-9]", "");
            String fractionText = "00";
            List<WebElement> fractions = driver.findElements(priceFraction);
            if (!fractions.isEmpty()) {
                fractionText = fractions.get(0).getText().replaceAll("[^0-9]", "");
            }
            return "$" + wholeText + "." + fractionText;
        } catch (Exception e) {
            return "Price not available";
        }
    }

    public void addToCart() {
        try {
            WebElement cartBtn = wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cartBtn);
            Thread.sleep(2000);
            List<WebElement> popup = driver.findElements(closeCartPopup);
            if (!popup.isEmpty()) {
                popup.get(0).click();
            }
        } catch (Exception e) {
            System.out.println("Add to cart step encountered an issue: " + e.getMessage());
        }
    }
}
