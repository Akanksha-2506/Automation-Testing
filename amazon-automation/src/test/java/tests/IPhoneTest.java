package tests;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.AmazonPage;
import utils.DriverManager;

public class IPhoneTest {

    private AmazonPage amazonPage;

    @BeforeTest
    public void setUp() {
        DriverManager.initDriver();
        amazonPage = new AmazonPage(DriverManager.getDriver());
    }

    @Test
    public void searchAndAddIPhoneToCart() {
        amazonPage.openAmazon();
        amazonPage.searchFor("iPhone 15");
        amazonPage.clickFirstResult();
        String price = amazonPage.getProductPrice();
        System.out.println("iPhone Price: " + price);
        amazonPage.addToCart();
        System.out.println("iPhone added to cart successfully");
    }

    @AfterTest
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
