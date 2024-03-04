import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class M10 {
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

    //On Facebook main screen find XPath and create a method to press the Create New Account button
    @Test
    public void createNewAccButtonTest() throws InterruptedException {
        WebElement newAccButtonElement = driver.findElement(By.xpath("//a[text()='Создать новый аккаунт']"));
        assertNotNull(newAccButtonElement);
        newAccButtonElement.click();
        Thread.sleep(1000);
    }

    //Find Xpathes for each text box component and Sign Up button
    @Test
    public void elementsByXPath() throws InterruptedException {
        WebElement newAccButtonElement = driver.findElement(By.xpath("//a[text()='Создать новый аккаунт']"));
        assertNotNull(newAccButtonElement);
        newAccButtonElement.click();
        Thread.sleep(1000);

        WebElement firstNameFieldElement = driver.findElement(By.xpath("//input[@name='firstname']"));
        assertNotNull(firstNameFieldElement);

        WebElement lastNameFieldElement = driver.findElement(By.xpath("//input[@name='lastname']"));
        assertNotNull(lastNameFieldElement);

        WebElement phoneNumberOrEmailElement = driver.findElement(By.xpath("//input[@name='reg_email__']"));
        assertNotNull(phoneNumberOrEmailElement);

        WebElement passElement = driver.findElement(By.xpath("//input[@name='reg_passwd__']"));
        assertNotNull(passElement);

        WebElement signUpButton = driver.findElement(By.xpath("//button[@name='websubmit']"));
        assertNotNull(signUpButton);
    }

    //JUnit tests for account creation page
    @Test
    public void accCreationPageTest(){

        WebElement firstNameFieldElement = driver.findElement(By.xpath("//input[@name='firstname']"));
        assertNotNull(firstNameFieldElement);
        firstNameFieldElement.sendKeys("Sofie");
        String firstNameValue = firstNameFieldElement.getAttribute("value");
        assertEquals("Sofie", firstNameValue);

        WebElement lastNameFieldElement = driver.findElement(By.xpath("//input[@name='lastname']"));
        assertNotNull(lastNameFieldElement);
        lastNameFieldElement.sendKeys("Tkachuk");
        String lastNameValue = lastNameFieldElement.getAttribute("value");
        assertEquals("Tkachuk", lastNameValue);

        WebElement phoneNumberOrEmailElement = driver.findElement(By.xpath("//input[@name='reg_email__']"));
        assertNotNull(phoneNumberOrEmailElement);
        phoneNumberOrEmailElement.sendKeys("1234@gmail.com");
        String emailValue = phoneNumberOrEmailElement.getAttribute("value");
        assertEquals("1234@gmail.com", emailValue);

//        WebElement phoneNumberOrEmailElement = driver.findElement(By.xpath("//input[@name='reg_email__']"));
//        assertNotNull(phoneNumberOrEmailElement);
//        phoneNumberOrEmailElement.sendKeys("+1647895437");
//        String phoneValue = phoneNumberOrEmailElement.getAttribute("value");
//        assertEquals("+1647895437", phoneValue);

        WebElement passElement = driver.findElement(By.xpath("//input[@name='reg_passwd__']"));
        assertNotNull(passElement);
        passElement.sendKeys("14568pass");
        String passValue = passElement.getAttribute("value");
        assertEquals("14568pass", passValue);

        WebElement signUpButton = driver.findElement(By.xpath("//button[@name='websubmit']"));
        assertNotNull(signUpButton);
        signUpButton.click();
        driver.get(HOME_PAGE_URL);
    }

    //Think about long text testing, special character testing, invalid inputs (email), etc. For now, don`t test the error messages, just validate that the new screen was not opened.
    @Test
    public void longTextTesting() {

        WebElement firstNameFieldElement = driver.findElement(By.xpath("//input[@name='firstname']"));
        assertNotNull(firstNameFieldElement);
        firstNameFieldElement.sendKeys("Sofiesofiesofiesofie");
        String firstNameValue = firstNameFieldElement.getAttribute("value");
        assertEquals("Sofiesofiesofiesofie", firstNameValue);

        WebElement lastNameFieldElement = driver.findElement(By.xpath("//input[@name='lastname']"));
        assertNotNull(lastNameFieldElement);
        lastNameFieldElement.sendKeys("Tkachuksofiesofiesofie");
        String lastNameValue = lastNameFieldElement.getAttribute("value");
        assertEquals("Tkachuksofiesofiesofie", lastNameValue);

        WebElement phoneNumberOrEmailElement = driver.findElement(By.xpath("//input[@name='reg_email__']"));
        assertNotNull(phoneNumberOrEmailElement);
        phoneNumberOrEmailElement.sendKeys("1234sofiesofie@gmail.com");
        String emailValue = phoneNumberOrEmailElement.getAttribute("value");
        assertEquals("1234sofiesofie@gmail.com", emailValue);

        WebElement passElement = driver.findElement(By.xpath("//input[@name='reg_passwd__']"));
        assertNotNull(passElement);
        passElement.sendKeys("14568pass123456passpasspasspass");
        String passValue = passElement.getAttribute("value");
        assertEquals("14568pass123456passpasspasspass", passValue);

        assertEquals(HOME_PAGE_URL, driver.getCurrentUrl());
        driver.get(HOME_PAGE_URL);
    }

    @Test
    public void specialCharactersInvalidInputTesting() throws InterruptedException {
        WebElement newAccButtonElement = driver.findElement(By.xpath("//a[text()='Создать новый аккаунт']"));
        assertNotNull(newAccButtonElement);
        newAccButtonElement.click();
        Thread.sleep(1000);

        WebElement firstNameFieldElement = driver.findElement(By.xpath("//input[@name='firstname']"));
        assertNotNull(firstNameFieldElement);
        firstNameFieldElement.sendKeys("Sofie@$");
        String firstNameValue = firstNameFieldElement.getAttribute("value");
        assertEquals("Sofie@$", firstNameValue);

        WebElement lastNameFieldElement = driver.findElement(By.xpath("//input[@name='lastname']"));
        assertNotNull(lastNameFieldElement);
        lastNameFieldElement.sendKeys("Tkachuk#@");
        String lastNameValue = lastNameFieldElement.getAttribute("value");
        assertEquals("Tkachuk#@", lastNameValue);

        WebElement phoneNumberOrEmailElement = driver.findElement(By.xpath("//input[@name='reg_email__']"));
        assertNotNull(phoneNumberOrEmailElement);
        phoneNumberOrEmailElement.sendKeys("**&sofiesofie@gmail.com");
        String emailValue = phoneNumberOrEmailElement.getAttribute("value");
        assertEquals("**&sofiesofie@gmail.com", emailValue);

        WebElement passElement = driver.findElement(By.xpath("//input[@name='reg_passwd__']"));
        assertNotNull(passElement);
        passElement.sendKeys("1234!#&");
        String passValue = passElement.getAttribute("value");
        assertEquals("1234!#&", passValue);

        assertEquals(HOME_PAGE_URL, driver.getCurrentUrl());
        driver.get(HOME_PAGE_URL);
    }

    //Don't forget to choose the Custom gender field to verify new  text boxes are displayed and test these text boxes as well
    @Test
    public void customGenderField() throws InterruptedException {
        WebElement newAccButtonElement = driver.findElement(By.xpath("//a[text()='Создать новый аккаунт']"));
        assertNotNull(newAccButtonElement);
        newAccButtonElement.click();
        Thread.sleep(1000);

        WebElement customGender = driver.findElement(By.xpath("//input[@value='-1']"));
        assertNotNull(customGender);
        customGender.click();

        WebElement sexField = driver.findElement(By.xpath("//input[@name='custom_gender']"));
        assertNotNull(sexField);
        sexField.sendKeys("They/Them");
        String sexValue = sexField.getAttribute("value");
        assertEquals("They/Them", sexValue);
    }
}
