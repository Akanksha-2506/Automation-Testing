# Amazon Automation Suite

This project automates product search and cart addition on Amazon using Selenium WebDriver and TestNG. Both test cases run in parallel.

## Prerequisites

Java JDK 11 or above must be installed on your machine.
Maven must be installed.
Google Chrome browser must be installed.

## How to Run

Clone or download this repository.

Open a terminal or command prompt in the project root folder where pom.xml is located.

Run the following command to execute all tests in parallel.

mvn test

## What the Tests Do

Test Case 1 searches for an iPhone on Amazon, opens the first result, prints the price to the console, and adds it to the cart.

Test Case 2 searches for a Samsung Galaxy device on Amazon, opens the first result, prints the price to the console, and adds it to the cart.

Both tests run at the same time using TestNG parallel execution with 2 threads.

## Project Structure

src/test/java/tests contains the test classes IPhoneTest.java and GalaxyTest.java.
src/test/java/pages contains AmazonPage.java which handles all browser interactions.
src/test/java/utils contains DriverManager.java which manages the browser driver per thread.
src/test/resources/testng.xml configures parallel execution.
pom.xml manages all dependencies.

## Expected Console Output

iPhone Price: $799.00
iPhone added to cart successfully
Galaxy Device Price: $699.00
Galaxy device added to cart successfully
