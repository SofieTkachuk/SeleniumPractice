import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FacebookM11 {
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

    // Verify all the errors messages for empty fields
    @Test
    public void errorMessagesTest() throws InterruptedException {
        driver.findElement(By.xpath("//*[text()='Создать новый аккаунт']")).click();
        assertNotNull(driver.findElement(By.xpath("//*[text()='Регистрация']")));
        Thread.sleep(1000);
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
    @ValueSource(strings = {"янв", "фев", "мар", "апр", "мая", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"})
    public void monthTestParameterized(String monthInput) throws InterruptedException {
        driver.findElement(By.xpath("//*[text()='Создать новый аккаунт']")).click();
        assertNotNull(driver.findElement(By.xpath("//*[text()='Регистрация']")));
        Thread.sleep(1000);

        driver.findElement(By.xpath("//*[@aria-label='Месяц']")).click();
        driver.findElement(By.xpath("//*[text()='"+ monthInput +"']")).click();
        String monthValue = driver.findElement(By.xpath("//*[@aria-label='Месяц']")).getAttribute("value");
        assertEquals(monthInput, monthValue);
    }
    @ParameterizedTest //Years
    @ValueSource(strings = {"1905", "1950", "2020"})
    public void yearTestParameterized(String yearInput) throws InterruptedException {
        driver.findElement(By.xpath("//*[text()='Создать новый аккаунт']")).click();
        assertNotNull(driver.findElement(By.xpath("//*[text()='Регистрация']")));
        Thread.sleep(1000);

        driver.findElement(By.xpath("//*[@title='Год']")).click();
        driver.findElement(By.xpath("//*[text()='"+ yearInput +"']")).click();
        String yearValue = driver.findElement(By.xpath("//*[@title='Год']")).getAttribute("value");
        assertEquals(yearInput, yearValue);
    }
}
