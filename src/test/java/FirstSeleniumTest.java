import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FirstSeleniumTest {

    private static final String HOME_PAGE_URL = "https://www.facebook.com/";
    private static WebDriver driver;

    @BeforeAll
    public static void classSetup(){
        driver = SharedDriver.getWebDriver();
        driver.get(HOME_PAGE_URL);
    }

    @AfterAll
    public static void classTearDown(){
        SharedDriver.closeDriver();
    }

    @AfterEach
    public void testTearDown(){
        driver.get(HOME_PAGE_URL);
    }

    @Test
    public void homePageURLTest() {

        String actualURL = driver.getCurrentUrl();
        assertEquals(HOME_PAGE_URL, actualURL, "URLs do not match");
    }

    @Test
    public void findEmailFieldTest(){
        //WebElement element = driver.findElement(By.id("email"));
        //WebElement element = driver.findElement(By.name("email"));
        //WebElement element = driver.findElement(By.linkText("Создать Страницу"));
        //WebElement element = driver.findElement(By.partialLinkText("Создать"));
        //WebElement element = driver.findElement(By.cssSelector("#email"));
        //WebElement element = driver.findElement(By.className("inputtext"));
        List<WebElement> element = driver.findElements(By.className("inputtext"));
        System.out.println(element.size());
        assertNotNull(element);
    }

    @Test
    public void findElementsByXpathTest(){
        WebElement emailElement = driver.findElement(By.xpath("//input[@name='email']"));
        assertNotNull(emailElement);
        WebElement passwordElement = driver.findElement(By.xpath("//input[@data-testid='royal_pass']"));
        assertNotNull(passwordElement);
        WebElement loginButtonElement = driver.findElement(By.xpath("//button[@type='submit']"));
        assertNotNull(loginButtonElement);
        WebElement forgotPassElement = driver.findElement(By.xpath("//a[text()='Забыли пароль?']"));
        assertNotNull(forgotPassElement);
        WebElement createNewAccButton = driver.findElement(By.xpath("//*[text()='Создать новый аккаунт']"));
        assertNotNull(createNewAccButton);
    }

    @Test
    public void loginScreenTest(){
        WebElement emailElement = driver.findElement(By.xpath("//input[@name='email']"));
        assertNotNull(emailElement);
        emailElement.sendKeys("12345@gmail.com");
        String emailValue = emailElement.getAttribute("value");
        assertEquals("12345@gmail.com", emailValue);

        WebElement passwordElement = driver.findElement(By.xpath("//input[@data-testid='royal_pass']"));
        assertNotNull(passwordElement);
        passwordElement.sendKeys("45685!");
        String passValue = passwordElement.getAttribute("value");
        assertEquals("45685!", passValue);

        WebElement loginButtonElement = driver.findElement(By.xpath("//button[@type='submit']"));
        assertNotNull(loginButtonElement);
        loginButtonElement.click();
    }

    @Test
    public void signUpTest(){
        driver.findElement(By.xpath("//*[text()='Создать новый аккаунт']")).click();
        assertNotNull(driver.findElement(By.xpath("//*[text()='Регистрация']")));
    }

    @Test
    public void gendersTest() throws InterruptedException {
        String femaleXpath = "//*[@name='sex' and @value=1]";
        String maleXpath = "//*[@name='sex' and @value=2]";

        driver.findElement(By.xpath("//*[text()='Создать новый аккаунт']")).click();
        assertNotNull(driver.findElement(By.xpath("//*[text()='Регистрация']")));

        Thread.sleep(1000);

        //verify female gender is checked
        WebElement femaleButton = driver.findElement(By.xpath(femaleXpath));
        femaleButton.click();
        String isFemaleChecked = driver.findElement((By.xpath(femaleXpath))).getAttribute("checked");
        assertNotNull(isFemaleChecked);
        assertEquals("true", isFemaleChecked);

        //verify male gender is checked
        WebElement maleButton = driver.findElement(By.xpath(maleXpath));
        maleButton.click();
        String isMaleChecked = driver.findElement((By.xpath(maleXpath))).getAttribute("checked");
        assertNotNull(isFemaleChecked);
        assertEquals("true", isMaleChecked);
    }

    @Test
    public void errorMessageTest() throws InterruptedException {

        driver.findElement(By.xpath("//*[text()='Создать новый аккаунт']")).click();
        assertNotNull(driver.findElement(By.xpath("//*[text()='Регистрация']")));
        Thread.sleep(1000);

        driver.findElement(By.xpath("//*[@name='websubmit']")).click();
        driver.findElement(By.xpath("//*[@aria-label='Номер мобильного телефона или эл. адрес']")).click();

        WebElement error = driver.findElement(By.xpath("//*[contains(text(), 'сброса')]"));
        assertNotNull(error);
    }

    @Test
    public void yearTest() throws InterruptedException {
        driver.findElement(By.xpath("//*[text()='Создать новый аккаунт']")).click();
        assertNotNull(driver.findElement(By.xpath("//*[text()='Регистрация']")));
        Thread.sleep(1000);

        driver.findElement(By.xpath("//*[@title='Год']")).click();
        driver.findElement(By.xpath("//*[text()='1995']")).click();
        String yearValue = driver.findElement(By.xpath("//*[@title='Год']")).getAttribute("value");
        assertEquals("1995", yearValue);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1905", "1950", "2024"})
    public void yearTestParameterized(String yearInput) throws InterruptedException {
        driver.findElement(By.xpath("//*[text()='Создать новый аккаунт']")).click();
        assertNotNull(driver.findElement(By.xpath("//*[text()='Регистрация']")));
        Thread.sleep(1000);

        driver.findElement(By.xpath("//*[@title='Год']")).click();
        driver.findElement(By.xpath("//*[text()='"+ yearInput +"']")).click();
        String yearValue = driver.findElement(By.xpath("//*[@title='Год']")).getAttribute("value");
        assertEquals(yearInput, yearValue);
    }

    @Test
    public void actionTest(){
        driver.get("https://www.daviktapes.com/");
        //pause();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Company']")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//a[text()='Company']")));

        WebElement element = driver.findElement(By.xpath("//a[text()='Company']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
    }

    public void pause() {
        try {
            Thread.sleep(5000);
        } catch (Exception err) {
            System.out.println("Something went wrong");
        }
    }

    @Test
    public void waitAndClickTest() {
        driver.get("https://www.daviktapes.com/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Company']"))).click();
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Company']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Company']"))).click();
        pause();
    }
}
