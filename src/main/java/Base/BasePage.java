package Base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofMillis(10000));
    }

    // Get element
    public WebElement getElement(By locator) {
        return driver.findElement(locator);
    }

    public String getElementText(By text) {
        return driver.findElement(text).toString();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    //Get List of web elements
    public List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }

    //Wait Wrapper Method
    public void waitVisibility(By elementBy) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elementBy));
    }

    //Click Method
    public void click(By elementBy) {
        waitVisibility(elementBy);
        driver.findElement(elementBy).click();
    }

    //Write Text
    public void writeText(By elementBy, String text) {
        waitVisibility(elementBy);
        driver.findElement(elementBy).sendKeys(text);
    }

    //Read Text
    public String readText(By elementBy) {
        waitVisibility(elementBy);
        return driver.findElement(elementBy).getText();
    }

    //Switching windows - method will work for only one new tab
    public WebDriver switchWindow(WebDriver driver) {
        String parentWindow = driver.getWindowHandle();
        wait.until(numberOfWindowsToBe(2));
// Switch to new window opened
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);

        }
        return driver;

    }


    public WebDriver testWindow() {
        String window = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();
        if (window.equals("newWindow"))
            for (String windowHandle : handles) {
                if (!windowHandle.equals(window)) {
                    driver.switchTo().window(windowHandle);
              /*  driver.close(); //closing child window
                driver.switchTo().window(window); //cntrl to parent window*/

                }
            }
        return driver;
    }

    public String getPageUrl() {
        String currentUrl = null;
        String window = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();
        for (String windowHandle : handles) {
            if (!windowHandle.equals(window)) {
                driver.switchTo().window(windowHandle);
              /*  driver.close(); //closing child window
                driver.switchTo().window(window); //cntrl to parent window*/

                currentUrl = driver.getCurrentUrl();
                driver.close();
                driver.switchTo().window(window);
            }
        }

        return currentUrl;
    }

    //Assert
    public void assertEquals(By elementBy, String expectedText) {
        waitVisibility(elementBy);
        Assert.assertEquals(readText(elementBy), expectedText);
    }

}
