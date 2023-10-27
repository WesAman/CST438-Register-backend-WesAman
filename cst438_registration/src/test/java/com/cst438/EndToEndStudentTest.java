package com.cst438;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class EndToEndStudentTest {

    public static final String CHROME_DRIVER_FILE_LOCATION = "/Users/wesaman/Downloads/chromedriver-mac-arm64/chromedriver";
    public static final String URL = "http://localhost:3000/admin";
    public static final int SLEEP_DURATION = 1000; // 1 second.

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void addStudentTest() throws Exception {
        driver.get(URL);

        try {
            driver.findElement(By.id("addCourse")).click();
            Thread.sleep(SLEEP_DURATION);

            driver.findElement(By.name("name")).sendKeys("John Doe");
            driver.findElement(By.name("email")).sendKeys("john.doe@example.com");

            driver.findElement(By.id("AddStudentButton")).click();

            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[td='John Doe']")));

            // After adding the student, check if the student is present in the list
            WebElement studentRow = driver.findElement(By.xpath("//tr[td='John Doe']"));
            assertNotNull(studentRow);
        } catch (Exception ex) {
            throw ex;
        }
    }


    @Test
    public void updateStudentTest() throws Exception {
        driver.get(URL);
        driver.navigate().refresh();

        try {
            driver.findElement(By.id("EditStudentButton")).click();
            Thread.sleep(SLEEP_DURATION);

         

            WebElement nameField = driver.findElement(By.name("name"));
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].value = '';", nameField);
            nameField.sendKeys("Updated Name");


            WebElement emailField = driver.findElement(By.name("email"));
            jsExecutor.executeScript("arguments[0].value = '';", emailField);
            
         
            emailField.sendKeys("updated.email@example.com");

            driver.findElement(By.id("saveButton")).click();
            Thread.sleep(SLEEP_DURATION);

            // Instead of checking a success message, you can verify the updated values here
            WebElement updatedNameField = driver.findElement(By.name("name"));
            String updatedName = updatedNameField.getAttribute("value");
            assertEquals("Updated Name", updatedName);

            WebElement updatedEmailField = driver.findElement(By.name("email"));
            String updatedEmail = updatedEmailField.getAttribute("value");
            assertEquals("updated.email@example.com", updatedEmail);
        } catch (Exception ex) {
            throw ex;
        } finally {
            // Refresh the page after the test is done
            driver.navigate().refresh();
            // Sleep for a while to ensure the page is refreshed before moving to the next test
            Thread.sleep(SLEEP_DURATION);
        }
    }

    @Test
    public void deleteStudentTest() throws Exception {
        driver.get(URL);
        driver.navigate().refresh();

        try {
            // Find the "Delete" button associated with the student "John Doe"
            WebElement deleteButton = driver.findElement(By.xpath("//tr[td='John Doe']//button[contains(text(), 'Delete')]"));
            deleteButton.click();
            Thread.sleep(SLEEP_DURATION);

            // Handle the built-in browser confirmation dialog by accepting it
            Alert confirmationAlert = driver.switchTo().alert();
            confirmationAlert.accept();

            // Proceed with your assertions and verifications after the confirmation
            Thread.sleep(SLEEP_DURATION);

            // Verify that the student "John Doe" has been deleted
            boolean studentDeleted = driver.findElements(By.xpath("//tr[td='John Doe']")).isEmpty();
            assertTrue(studentDeleted);
        } catch (Exception ex) {
            throw ex;
        } finally {
            // Refresh the page after the test is done
            driver.navigate().refresh();
            // Sleep for a while to ensure the page is refreshed before moving to the next test
            Thread.sleep(SLEEP_DURATION);
        }
    }

   
}
