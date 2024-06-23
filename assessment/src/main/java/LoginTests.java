import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTests {
    private WebDriver driver;
    private String baseUrl = "https://www.saucedemo.com/";

    @BeforeClass
    public void setUp() {
        
    	System.setProperty("webdriver.chrome.driver", "C:\\path\\to\\chromedriver.exe"); 
    	// Initialize ChromeDriver instance
        driver = new ChromeDriver();
        // Open the baseUrl
        driver.get(baseUrl);
    }

    @Test
    public void testLoginFormElementsPresence() {
        // Check for username field
        WebElement usernameField = driver.findElement(By.id("user-name"));
        Assert.assertTrue(usernameField.isDisplayed(), "Username field is not present");

        // Check for password field
        WebElement passwordField = driver.findElement(By.id("password"));
        Assert.assertTrue(passwordField.isDisplayed(), "Password field is not present");

        // Check for login button
        WebElement loginButton = driver.findElement(By.id("login-button"));
        Assert.assertTrue(loginButton.isDisplayed(), "Login button is not present");
    }

    @Test
    public void testValidCredentialsLogin() {
        // Enter valid username
        WebElement usernameField = driver.findElement(By.id("user-name"));
        usernameField.clear();
        usernameField.sendKeys("standard_user");

        // Enter valid password
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.clear();
        passwordField.sendKeys("secret_sauce");

        // Click on login button
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        // Verify successful login by checking the presence of the "Swag Labs" text
        WebElement swagLabsDiv = driver.findElement(By.xpath("//div[text()='Swag Labs']"));
        Assert.assertTrue(swagLabsDiv.isDisplayed(), "Swag Labs div is not visible after login");
    }

    @Test
    public void testInvalidCredentialsLogin() {
        // Reload the login page
        driver.get(baseUrl);

        // Enter invalid username
        WebElement usernameField = driver.findElement(By.id("user-name"));
        usernameField.clear();
        usernameField.sendKeys("invalid_user");

        // Enter invalid password
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.clear();
        passwordField.sendKeys("invalid_password");

        // Click on login button
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        // Verify unsuccessful login by checking the error message
        WebElement errorMessageDiv = driver.findElement(By.cssSelector(".error-message-container.error"));
        Assert.assertTrue(errorMessageDiv.isDisplayed(), "Error message div is not visible after login attempt");
        Assert.assertTrue(errorMessageDiv.getText().contains("Epic sadface: Username and password do not match any user in this service"), "Error message text is incorrect");
    }

    @AfterClass
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}
