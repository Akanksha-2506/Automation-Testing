package tests;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.AmazonPage;
import utils.DriverManager;

public class GalaxyTest {

    private AmazonPage amazonPage;

    @BeforeTest
    public void setUp() {
        DriverManager.initDriver();
        amazonPage = new AmazonPage(DriverManager.getDriver());
    }

    @Test
    public void searchAndAddGalaxyToCart() throws Exception {
        amazonPage.openAmazon();
        amazonPage.searchFor("Samsung Galaxy S24");
        amazonPage.clickFirstResult();
        String price = amazonPage.getProductPrice();
        System.out.println("Galaxy Device Price: " + price);
        amazonPage.addToCart();
        System.out.println("Galaxy device added to cart successfully");
    }

    @AfterTest
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
