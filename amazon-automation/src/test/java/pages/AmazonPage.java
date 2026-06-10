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
    private WebDriverWait longWait;

    public AmazonPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.longWait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    public void openAmazon() throws InterruptedException {
        driver.get("https://www.amazon.com");
        Thread.sleep(3000);

        String pageSource = driver.getPageSource().toLowerCase();
        if (pageSource.contains("captcha") || pageSource.contains("robot") || pageSource.contains("automated")) {
            System.out.println("CAPTCHA or bot detection page encountered - retrying...");
            Thread.sleep(3000);
            driver.navigate().refresh();
            Thread.sleep(3000);
        }

        try {
            List<WebElement> dismissButtons = driver.findElements(
                By.cssSelector("input[data-action-type='DISMISS'], .a-button-close, button[data-action='a-popover-close']")
            );
            if (!dismissButtons.isEmpty()) {
                dismissButtons.get(0).click();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("No popup to dismiss");
        }

        longWait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        try {
            longWait.until(ExpectedConditions.elementToBeClickable(By.id("twotabsearchtextbox")));
        } catch (Exception e) {
            System.out.println("Search box not immediately visible, trying alternate selector...");
            longWait.until(ExpectedConditions.elementToBeClickable(By.name("field-keywords")));
        }
    }

    public void searchFor(String query) throws InterruptedException {
        WebElement box;
        try {
            box = wait.until(ExpectedConditions.elementToBeClickable(By.id("twotabsearchtextbox")));
        } catch (Exception e) {
            box = wait.until(ExpectedConditions.elementToBeClickable(By.name("field-keywords")));
        }
        box.clear();
        box.sendKeys(query);
        Thread.sleep(500);

        try {
            driver.findElement(By.id("nav-search-submit-button")).click();
        } catch (Exception e) {
            box.submit();
        }
    }

    public void clickFirstResult() throws InterruptedException {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
            By.cssSelector("div[data-component-type='s-search-result']")
        ));
        Thread.sleep(1000);
        List<WebElement> results = driver.findElements(
            By.cssSelector("div[data-component-type='s-search-result']")
        );
        for (WebElement result : results) {
            List<WebElement> titles = result.findElements(By.cssSelector("h2 a"));
            if (!titles.isEmpty()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", titles.get(0));
                break;
            }
        }
        Thread.sleep(2000);
    }

    public String getProductPrice() {
        try {
            WebElement whole = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("span.a-price-whole"))
            );
            String wholeText = whole.getText().replaceAll("[^0-9]", "");
            String fractionText = "00";
            List<WebElement> fractions = driver.findElements(By.cssSelector("span.a-price-fraction"));
            if (!fractions.isEmpty()) {
                fractionText = fractions.get(0).getText().replaceAll("[^0-9]", "");
            }
            return "$" + wholeText + "." + fractionText;
        } catch (Exception e) {
            return "Price not available";
        }
    }

    public void addToCart() throws InterruptedException {
        Thread.sleep(2000);

        List<WebElement> directAddToCart = driver.findElements(By.id("add-to-cart-button"));
        if (!directAddToCart.isEmpty() && directAddToCart.get(0).isDisplayed()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", directAddToCart.get(0));
            System.out.println("Clicked direct Add to Cart button");
            Thread.sleep(2000);
            return;
        }

        List<WebElement> buyingOptions = driver.findElements(
            By.cssSelector("a[title='See All Buying Options'], a[href*='offer-listing'], a[href*='buying-options']")
        );
        if (!buyingOptions.isEmpty()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buyingOptions.get(0));
            Thread.sleep(2000);
            List<WebElement> addBtn = driver.findElements(
                By.cssSelector("input[name='submit.addToCart'], #add-to-cart-button")
            );
            if (!addBtn.isEmpty()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn.get(0));
                System.out.println("Clicked Add to Cart from buying options");
                Thread.sleep(2000);
                return;
            }
        }

        List<WebElement> altButtons = driver.findElements(
            By.cssSelector("button[name='submit.addToCart'], [data-action='add-to-cart'], .a-button-input")
        );
        if (!altButtons.isEmpty()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", altButtons.get(0));
            System.out.println("Clicked alternate cart button");
            Thread.sleep(2000);
            return;
        }

        System.out.println("No Add to Cart button found - Amazon may require login or variant selection");
    }
}
