import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FacebookM11 {
    private static final String HOME_PAGE_URL = "https://www.facebook.com/";
    private static final String CREATE_NEW_ACC_BUTTON = "//*[text() = 'Создать новый аккаунт']";
    private static WebDriver driver;

    @BeforeAll
    public static void classSetup(){
        driver = SharedDriver.getWebDriver();
        driver.get(HOME_PAGE_URL);
    }

    @BeforeEach
    public void testSetUp() throws InterruptedException {
        driver.findElement(By.xpath(CREATE_NEW_ACC_BUTTON)).click();
        assertNotNull(driver.findElement(By.xpath("//*[text()='Регистрация']")));
        Thread.sleep(1000);
    }

    @AfterEach
    public void testTearDown() {
        driver.get(HOME_PAGE_URL);
    }

    @AfterAll
    public static void classTearDown(){
        SharedDriver.closeDriver();
    }

    // Verify all the errors messages for empty fields
    @Test
    public void errorMessagesTest() {

        driver.findElement(By.xpath("//*[@name='websubmit']")).click();

        driver.findElement(By.xpath("//*[@aria-label='Имя']")).click();
        WebElement errorFirstName = driver.findElement(By.xpath("//*[contains(text(), 'зовут')]"));
        assertNotNull(errorFirstName);

        driver.findElement(By.xpath("//*[@aria-label='Фамилия']")).click();
        WebElement errorLastName = driver.findElement(By.xpath("//*[contains(text(), 'зовут')]"));
        assertNotNull(errorLastName);

        driver.findElement(By.xpath("//*[@aria-label='Номер мобильного телефона или эл. адрес']")).click();
        WebElement errorNum = driver.findElement(By.xpath("//*[contains(text(), 'сброса')]"));
        assertNotNull(errorNum);

        driver.findElement(By.xpath("//*[@aria-label='Новый пароль']")).click();
        WebElement errorPass = driver.findElement(By.xpath("//*[contains(text(), 'не менее шести')]"));
        assertNotNull(errorPass);

        driver.findElement(By.xpath("//*[@aria-label='День']")).click();
        WebElement errorBirthDay = driver.findElement(By.xpath("//*[contains(text(), 'дату')]"));
        assertNotNull(errorBirthDay);

        driver.findElement(By.xpath("//*[@aria-label='Месяц']")).click();
        WebElement errorBirthMonth = driver.findElement(By.xpath("//*[contains(text(), 'дату')]"));
        assertNotNull(errorBirthMonth);

        driver.findElement(By.xpath("//*[@aria-label='Год']")).click();
        WebElement errorBirthYear = driver.findElement(By.xpath("//*[contains(text(), 'дату')]"));
        assertNotNull(errorBirthYear);
    }

    // Test Months and Years droplist (no need to test all years, 1905, 1950 and 2020 are enough)
    @ParameterizedTest //Months
    @ValueSource(strings = {"1", "4", "6", "10", "12"})
    public void monthTestParameterized(String monthInput) {

        driver.findElement(By.xpath("//select[@name='birthday_month']")).click();
        driver.findElement(By.xpath("//select[@name='birthday_month']//descendant::option[@value='" + monthInput + "']")).click();
        String monthValue = driver.findElement(By.xpath("//select[@name='birthday_month']")).getAttribute("value");
        assertEquals(monthInput, monthValue);
    }
    @ParameterizedTest //Years
    @ValueSource(strings = {"1905", "1950", "2020"})
    public void yearTestParameterized(String yearInput) {

        driver.findElement(By.xpath("//*[@title='Год']")).click();
        driver.findElement(By.xpath("//*[text()='"+ yearInput +"']")).click();
        String yearValue = driver.findElement(By.xpath("//*[@title='Год']")).getAttribute("value");
        assertEquals(yearInput, yearValue);
    }

    // Find and test Radio buttons using siblings in XPath.
    @ParameterizedTest
    @ValueSource(strings = {"Женщина", "Мужчина", "Другое"})
    public void gendersTest(String gender) {
        WebElement genderButton = driver.findElement(By.xpath("//label[text()='" + gender + "']//following-sibling::*[@type='radio']"));
        genderButton.click();
        String isChecked = driver.findElement(By.xpath("//label[text()='" + gender + "']//following-sibling::*[@type='radio']"))
                .getAttribute("checked");
        assertNotNull(isChecked);
        assertEquals("true", isChecked);
    }

    // Test Terms and DataPolicy links, verify that the new pages are opened after pressing it
    @Test
    public void termsLinkTest(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Регистрация']")));
        String originalTab = driver.getWindowHandle();
        driver.findElement(By.xpath("//a[@id='terms-link']")).click();
        for (String windowHandle : driver.getWindowHandles()) {
            if(!originalTab.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='Пользовательское соглашение']")));
        driver.close();
        driver.switchTo().window(originalTab);
    }

    @Test
    public void dataPolicyLinkTest(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Регистрация']")));
        String originalTab = driver.getWindowHandle();
        driver.findElement(By.xpath("//a[@id='privacy-link']")).click();
        for (String windowHandle : driver.getWindowHandles()) {
            if(!originalTab.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Политика конфиденциальности']")));
        driver.close();
        driver.switchTo().window(originalTab);
    }
}
