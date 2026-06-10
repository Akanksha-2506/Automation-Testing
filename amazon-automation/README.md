# Amazon Automation Suite

This project automates product search and cart addition on Amazon using Selenium WebDriver and TestNG. Both test cases run in parallel locally and on LambdaTest cloud.

## Prerequisites

Java JDK 11 or above must be installed on your machine.
Maven must be installed.
Google Chrome browser must be installed.

## How to Run Locally

Clone or download this repository.

Open a terminal or command prompt in the project root folder where pom.xml is located.

Run the following command to execute all tests in parallel.

mvn test

or explicitly with the local profile

mvn test -P local

## How to Run on LambdaTest Cloud

Sign up for a free account at https://www.lambdatest.com

Go to https://accounts.lambdatest.com/detail/profile and copy your Username and Access Key.

Open the file src/test/java/tests/LambdaTestRunner.java and replace the following two lines with your actual credentials.

YOUR_LAMBDATEST_USERNAME with your LambdaTest username
YOUR_LAMBDATEST_ACCESS_KEY with your LambdaTest access key

Run the tests on LambdaTest cloud using this command.

mvn test -P lambdatest

You can view live test execution at https://automation.lambdatest.com/timeline

## What the Tests Do

Test Case 1 searches for an iPhone on Amazon, opens the first result, prints the price to the console, and adds it to the cart.

Test Case 2 searches for a Samsung Galaxy device on Amazon, opens the first result, prints the price to the console, and adds it to the cart.

Both tests run at the same time using TestNG parallel execution with 2 threads.

## Project Structure

src/test/java/tests contains IPhoneTest.java, GalaxyTest.java for local runs and LambdaTestRunner.java for cloud runs.
src/test/java/pages contains AmazonPage.java which handles all browser interactions.
src/test/java/utils contains DriverManager.java which manages the browser driver per thread.
src/test/resources/testng.xml configures local parallel execution.
src/test/resources/lambdatest.xml configures LambdaTest cloud parallel execution.
pom.xml manages all dependencies and profiles.

## Expected Console Output

iPhone Price: $799.00
iPhone added to cart successfully
Galaxy Device Price: $699.00
Galaxy device added to cart successfully
