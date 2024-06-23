import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTests {
    private WebDriver driver; // WebDriver instance to control the browser
    private String baseUrl = "https://www.saucedemo.com/"; // Base URL of the application
    private WebDriverWait wait; // WebDriverWait instance for waiting for elements

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/Toka/Downloads/chromedriver.exe"); // Set ChromeDriver path
        driver = new ChromeDriver(); // Initialize ChromeDriver
        driver.manage().window().maximize(); // Maximize the browser window
        driver.get(baseUrl); // Open the base URL
        wait = new WebDriverWait(driver, 10); // Initialize WebDriverWait with a timeout of 10 seconds
    }

    @Test(priority = 1)
    public void testLoginFormElementsPresence() {
        // Assert that the username field is present
        Assert.assertTrue(isElementPresent(By.id("user-name")), "Username field is not present");

        // Assert that the password field is present
        Assert.assertTrue(isElementPresent(By.id("password")), "Password field is not present");

        // Assert that the login button is present
        Assert.assertTrue(isElementPresent(By.id("login-button")), "Login button is not present");
    }

    @Test(priority = 2)
    public void testValidCredentialsLogin() {
        // Login with valid credentials
        login("standard_user", "secret_sauce");

        // Wait until the inventory container is visible
        WebElement inventoryContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_container")));

        // Assert that the inventory container is displayed after login
        Assert.assertTrue(inventoryContainer.isDisplayed(), "Inventory container is not visible after login");

        // Find the Swag Labs header element
        WebElement swagLabsHeader = driver.findElement(By.className("title"));

        // Assert that the text "Swag Labs" is present in the Swag Labs header
        Assert.assertTrue(swagLabsHeader.getText().contains("Swag Labs"), "'Swag Labs' text is not visible after login");
    }

    @Test(priority = 3)
    public void testInvalidCredentialsLogin() {
        // Login with invalid credentials
        login("invalid_user", "invalid_password");

        // Wait until the error message container is visible
        WebElement errorContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".error-message-container.error")));

        // Assert that the error message container is displayed
        Assert.assertTrue(errorContainer.isDisplayed(), "Error message container is not displayed");

        // Assert that the error message text contains the expected message for invalid credentials
        Assert.assertTrue(errorContainer.getText().contains("Epic sadface: Username and password do not match any user"), "Error message text is incorrect");
    }

    @Test(priority = 4)
    public void testEmptyUsernameLogin() {
        // Login with empty username
        login("", "secret_sauce");

        // Wait until the error message container is visible
        WebElement errorContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".error-message-container.error")));

        // Assert that the error message container is displayed for empty username
        Assert.assertTrue(errorContainer.isDisplayed(), "Error message container is not displayed for empty username");

        // Assert that the error message text contains the expected message for empty username
        Assert.assertTrue(errorContainer.getText().contains("Epic sadface: Username is required"), "Error message text for empty username is incorrect");
    }

    @Test(priority = 5)
    public void testEmptyPasswordLogin() {
        // Login with empty password
        login("standard_user", "");

        // Wait until the error message container is visible
        WebElement errorContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".error-message-container.error")));

        // Assert that the error message container is displayed for empty password
        Assert.assertTrue(errorContainer.isDisplayed(), "Error message container is not displayed for empty password");

        // Assert that the error message text contains the expected message for empty password
        Assert.assertTrue(errorContainer.getText().contains("Epic sadface: Password is required"), "Error message text for empty password is incorrect");
    }

    @AfterClass
    public void tearDown() {
        // Quit the WebDriver instance after all tests are completed
        if (driver != null) {
            driver.quit();
        }
    }

    // Method to perform login action with provided username and password
    private void login(String username, String password) {
        // Wait until the username field is visible
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));

        // Wait until the password field is visible
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));

        // Wait until the login button is clickable
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));

        // Enter the username into the username field
        usernameField.sendKeys(username);

        // Enter the password into the password field
        passwordField.sendKeys(password);

        // Click on the login button
        loginButton.click();
    }

    // Method to check if an element is present on the page
    private boolean isElementPresent(By locator) {
        try {
            // Try to find the element
            driver.findElement(locator);
            return true; // Return true if element is found
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false; // Return false if element is not found
        }
    }
}
