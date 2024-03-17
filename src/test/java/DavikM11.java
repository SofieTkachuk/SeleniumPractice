import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DavikM11 {

    private static WebDriver driver;
    private Actions actions;

    // Navigate to ( you can also use driver.navigate.to("") method) https://www.daviktapes.com/
    @BeforeAll
    public static void classSetup(){
        driver = SharedDriver.getWebDriver();
        driver.get("https://www.daviktapes.com/");
    }

    @AfterAll
    public static void classTearDown(){
        SharedDriver.closeDriver();
    }

    // Using actions class, move the mouse to every top menu option and verify it`s opened in each case.
    // Use Explicit wait to check each menu had enough time to be expanded (use the presenceOfElementLocated method with one of the options element XPath).
    @ParameterizedTest
    @ValueSource(strings = {"Company", "Products", "Industries", "Knowledge Center"})
    public void actionTest(String menu){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement menuElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='"+ menu +"']")));

        actions = new Actions(driver);
        actions.moveToElement(menuElement).perform();

        WebElement submenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='"+ menu +"']//following-sibling::*[@class='sub-menu']")));
    }
}
