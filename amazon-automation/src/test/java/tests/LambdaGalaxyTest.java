package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.AmazonPage;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LambdaGalaxyTest {

    private static final String USERNAME = "rajakanksha0625";
    private static final String ACCESS_KEY = "LT_VslTBS1CxMR8mJ07jJd4m6NH3I1pbEUqxS86jUWBJKEthnX";
    private static final String GRID_URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@hub.lambdatest.com/wd/hub";

    private WebDriver driver;

    @BeforeTest
    public void setUp() throws Exception {
        Map<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("platform", "Windows 10");
        ltOptions.put("build", "Amazon Automation Build");
        ltOptions.put("name", "Galaxy Test");
        ltOptions.put("selenium_version", "4.0.0");
        ltOptions.put("w3c", true);

        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setBrowserVersion("latest");
        browserOptions.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL(GRID_URL), browserOptions);
    }

    @Test
    public void galaxyLambdaTest() throws Exception {
        AmazonPage amazonPage = new AmazonPage(driver);
        amazonPage.openAmazon();
        amazonPage.searchFor("Samsung Galaxy S24");
        amazonPage.clickFirstResult();
        String price = amazonPage.getProductPrice();
        System.out.println("Galaxy Price on LambdaTest Cloud: " + price);
        amazonPage.addToCart();
        System.out.println("Galaxy added to cart on LambdaTest Cloud");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
