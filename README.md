Flipkart Automation Framework
Prepared by: Sanjay Chauhan
Date: 10-11-2024

Project Overview
This project is an automated testing framework for verifying the functionality on Flipkart. The automation covers product searches, comparison, cart actions, and more, using Java with Selenium WebDriver and TestNG. Key features include logging with Log4j, cross-browser compatibility, automated screenshots for failures, and Extent Reports for detailed test reporting.

Key Features
Cross-Browser Testing: Supports Chrome, Firefox, and Edge browsers.
Logging: Tracks each test action in detail.
Screenshots: Captures screenshots upon failures for better debugging.
Extent Reports: Provides visual reports with pass/fail status and step-by-step execution details.
Project Structure
Base Class

Sets up browser drivers and manages test actions like screenshot capture and logging.
Page Objects

HomePage: Handles Flipkart's homepage, login popup, and search actions.
SearchResultsPage: Manages product selection, search result verifications, and comparison.
ProductPage: Adds products to the cart and verifies product details.
CartPage: Verifies cart contents, including product quantity and total amount.
Configuration

ConfigReader: Reads settings like browser type and base URL from a properties file for easy configuration.
Reporting and Logging

Log4j: Logs actions and errors.
Extent Reports: Generates detailed HTML reports showing test results, with screenshots on failure.
Test Execution Logs
Sample test execution logs from the Flipkart automation framework:

LogS:
2024-11-10 16:35:44,545 INFO  [TC_01_flipkart_End_to_End] - Starting test: Open Flipkart and close the login module
2024-11-10 16:35:44,557 INFO  [TC_01_flipkart_End_to_End] - Flipkart homepage opened
2024-11-10 16:35:44,557 INFO  [TC_01_flipkart_End_to_End] - Login module closed successfully
Test Passed: openFlipkartAndCloseLoginModule

2024-11-10 16:35:48,328 INFO  [TC_01_flipkart_End_to_End] - Search results message verified successfully
Test Passed: searchForMobileAndVerifyResults

2024-11-10 16:37:25,516 INFO  [TC_01_flipkart_End_to_End] - Cart total amount verified successfully
Test Passed: verifyCartTotalAmount

2024-11-10 16:37:32,371 INFO  [TC_01_flipkart_End_to_End] - Product removed from cart
Test Passed: removeProductFromCartAndVerify
Extent Report Summary
The Extent Report provides a comprehensive summary of test execution, including detailed pass/fail results and captured screenshots for any test failures.

Summary
The Flipkart Automation Framework is designed to be reliable and easy to maintain, with detailed logging and reporting to support testing, debugging, and reporting. This document provides an overview of the framework’s structure and capabilities for quick understanding and review.
