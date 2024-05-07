package otcuda.zvuk.herokuapp;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HerokuappTests {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @AfterEach
    public void tearDown() {
        driver.close();
    }

    @Test
    public void authTest() {
        //данные об авторизации подставляем прям в ссылку admin:admin
        driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth");
        String authTitle = driver.findElement(By.xpath("//h3")).getText();
        Assertions.assertEquals("Basic Auth", authTitle);
    }

    @Test
    public void alertOk() {
        String expectedAlertText = "I am a JS Alert";
        String expectedResultText = "You successfully clicked an alert";

        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        driver.findElement(By.xpath("//button[@onclick='jsAlert()']")).click();
        String actualAlertText = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();

        String resultResultText = driver.findElement(By.id("result")).getText();

        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedAlertText, actualAlertText),
                () -> Assertions.assertEquals(expectedResultText, resultResultText)
        );
    }

    @Test
    public void iFrameMailRuTest() {
        driver.get("https://mail.ru/");
        driver.findElement(By.xpath("//button[@data-click-counter='75068996']"))
                .click();
        WebElement iframeAuthBtn = driver.findElement(By.xpath("//iframe[@class='ag-popup__frame__layout__iframe']"));
        driver.switchTo().frame(iframeAuthBtn);
        driver.findElement(By.xpath("//input[@name='username']"))
                .sendKeys("ebareza@mail.ru");
    }

    @Test
    public void selectMenuTest() {
        driver.get("http://85.192.34.140:8081/");

        driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Elements']")).click();
        driver.findElement(By.xpath("//div[text()='Widgets']")).click();
        driver.findElement(By.xpath("//span[text()='Select Menu']")).click();
        driver.findElement(By.xpath("//div[@id='selectOne']")).click();
        driver.findElement(By.xpath("//div[text()='Mr.']")).click();
        int i = 1;
    }

    @SneakyThrows
    @Test
    public void sliderTest(){
        driver.get("http://85.192.34.140:8081/");

        driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Elements']")).click();
        driver.findElement(By.xpath("//div[text()='Widgets']")).click();
        TimeUnit.SECONDS.sleep(5);
        driver.findElement(By.xpath("//span[text()='Slider']")).click();

        WebElement slider = driver.findElement(By.xpath("//input[@type='range']"));
        int expectedSliderValue = 85;
        int currentSliderValue = Integer.parseInt(slider.getAttribute("value"));
        int valueToMove = expectedSliderValue - currentSliderValue;
        for(int i = 0; i < valueToMove; i++ ){
            slider.sendKeys(Keys.ARROW_RIGHT);
        }

        WebElement sliderValue = driver.findElement(By.id("sliderValue"));
        int actualSliderValue = Integer.parseInt(sliderValue.getAttribute("value"));

        Assertions.assertEquals(expectedSliderValue, actualSliderValue);
    }

    @SneakyThrows
    @Test
    public void hoverMenuTest(){
        driver.get("http://85.192.34.140:8081/");
        driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Elements']")).click();
        driver.findElement(By.xpath("//div[text()='Widgets']")).click();

        WebElement menuElement = driver.findElement(By.xpath("//span[text()='Menu']"));
        menuElement.click();

        WebElement menuItemSecond = driver.findElement(By.xpath("//a[text()='Main Item 2']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(menuItemSecond).build().perform();

        WebElement subSubList = driver.findElement(By.xpath("//a[text()= 'SUB SUB LIST »']"));
        actions.moveToElement(subSubList).build().perform();

        List<WebElement> subSubListMenu = driver.findElements(By.xpath("//a[contains(text(), 'Sub Sub Item')]"));

        Assertions.assertEquals(2, subSubListMenu.size());
    }
}
