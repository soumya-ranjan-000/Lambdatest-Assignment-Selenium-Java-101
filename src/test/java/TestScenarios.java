import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class TestScenarios {
    SoftAssert soft = new SoftAssert();
    WebDriverWait wait = null;
    private ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    @BeforeMethod
    @Parameters({"browserName", "browserVersion", "os", "playground_url"})
    public void setupBrowser(String browserName, String browserVersion, String os, String url) {
        SetUpTest setup = new SetUpTest();
        WebDriver driver = setup.setupDriver(browserName, browserVersion, os);;
        driverThreadLocal.set(driver);
        wait = new WebDriverWait(driverThreadLocal.get(), Duration.ofSeconds(15));
        driverThreadLocal.get().get(url);
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete';"));
    }

    @Test()
    public void Scenario1(){
        WebDriver driver=driverThreadLocal.get();
        driver.findElement(By.linkText("Simple Form Demo")).click();
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete';"));
        Assert.assertTrue(driver.getCurrentUrl().contains("simple-form-demo"));
        String msg="Welcome to LambdaTest";
        driver.findElement(By.id("user-message")).sendKeys(msg);
        driver.findElement(By.cssSelector("button#showInput")).click();
        String yourMessage=driver.findElement(By.xpath("//label[text()='Your Message: ']/parent::div//p")).getText();
        Assert.assertEquals(yourMessage,msg);
    }

    @Test
    public void Scenario2() {
        WebDriver driver=driverThreadLocal.get();
        driver.findElement(By.linkText("Drag & Drop Sliders")).click();
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete';"));
        WebElement slider=driver.findElement(By.xpath("//h4[text()=' Default value 15']/parent::div//input[@type='range' ]"));
        for (int i = 16; i <= 95 ; i++) {
            slider.sendKeys(Keys.ARROW_RIGHT);
        }
        Assert.assertEquals(driver.findElement(By.id("rangeSuccess")).getText(),"95");
    }

    @Test
    public void Scenario3() {
        WebDriver driver = driverThreadLocal.get();
        driver.findElement(By.linkText("Input Form Submit")).click();
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete';"));
        WebElement submitBtn = driver.findElement(By.xpath("//button[text()='Submit']"));
        submitBtn.click();
        WebElement name = driver.findElement(By.id("name"));
        WebElement email = driver.findElement(By.id("inputEmail4"));
        WebElement password = driver.findElement(By.id("inputPassword4"));
        WebElement company = driver.findElement(By.id("company"));
        WebElement website = driver.findElement(By.id("websitename"));
        WebElement city = driver.findElement(By.id("inputCity"));
        WebElement Address1 = driver.findElement(By.id("inputAddress1"));
        WebElement Address2 = driver.findElement(By.id("inputAddress2"));
        WebElement State = driver.findElement(By.id("inputState"));
        WebElement Zip = driver.findElement(By.id("inputZip"));
        Select countrySelect = new Select(driver.findElement(By.xpath("//select[@name='country']")));
        try {
            name.sendKeys("soumya");
            email.sendKeys("xyz@gmail.com");
            password.sendKeys("Qwerty@123");
            company.sendKeys("wipro");
            website.sendKeys("https://www.lambdatest.com/selenium-playground/input-form-demo");
            city.sendKeys("jharsuguda");
            Address1.sendKeys("New Phalsamunda");
            Address2.sendKeys("ITPS, Banharpali");
            State.sendKeys("Odisha");
            Zip.sendKeys("768234");
            countrySelect.selectByVisibleText("United States");
            submitBtn.click();
            String successMessage = driver.findElement(By.xpath("//p[contains(@class,'success-msg hidden')]")).getText();
            Assert.assertEquals(successMessage, "Thanks for contacting us, we will get back to you shortly.");
        } catch (Exception e) {
            if (driver.findElement(By.id("exit_popup_close")).isDisplayed()) {
                WebElement close = driver.findElement(By.id("exit_popup_close"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", close);

            }
            submitBtn.click();
            String successMessage = driver.findElement(By.xpath("//p[contains(@class,'success-msg hidden')]")).getText();
            Assert.assertEquals(successMessage, "Thanks for contacting us, we will get back to you shortly.");
        }
    }

    @AfterMethod
    public void close() {
        System.out.println("driver closed");
        WebDriver driver = driverThreadLocal.get();
        driver.quit();
    }
}
