package tests.threadqa;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class SeleniumTests {
    private WebDriver driver;
    //говорим в какую папку сохранять картинку
    private String downloadFolder = System.getProperty("user.dir") + File.separator + "build" + File.separator + "downloadFiles";

    @BeforeAll
    public static void downloadDriver(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();

        Map<String, String> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadFolder);
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        //driver.manage().window().setSize(new Dimension(1920,1080));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @AfterEach
    public void tearDown() {
        driver.close();
    }

    @Test
    public void simpleUiTest() {
        String expectedTitle = "Олег Пендрак - Инженер по автоматизации тестирования QA Automation";

        driver.get("https://threadqa.ru");
        String actualTitle = driver.getTitle();

        Assertions.assertEquals(expectedTitle, actualTitle);
    }

    @Test
    public void SimpleFormTest() {
        String expectedName = "Lika Orlova";
        String expectedEmail = "lika.orlova@matrox.ru";
        String expectedCurrentAddress = "Saint-Petersburg, Russia";
        String expectedPermanentAddress = "Saratov, Russia";

        driver.get("http://85.192.34.140:8081/");
        WebElement elementElements = driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Elements']"));
        elementElements.click();

        WebElement elementTextBox = driver.findElement(By.xpath("//span[text()='Text Box']"));
        elementTextBox.click();

        WebElement fieldFullName = driver.findElement(By.id("userName"));
        WebElement fieldEmail = driver.findElement(By.id("userEmail"));
        WebElement fieldCurrentAddress = driver.findElement(By.id("currentAddress"));
        WebElement fieldPermanentAddress = driver.findElement(By.id("permanentAddress"));
        WebElement submit = driver.findElement(By.id("submit"));

        fieldFullName.sendKeys(expectedName);//ввод текста в поле
        fieldEmail.sendKeys(expectedEmail);
        fieldCurrentAddress.sendKeys(expectedCurrentAddress);
        fieldPermanentAddress.sendKeys(expectedPermanentAddress);
        submit.click();

        WebElement newName = driver.findElement(By.id("name"));
        WebElement newEmail = driver.findElement(By.id("email"));
        WebElement newCurrentAddress = driver.findElement(By.xpath("//p[@id='currentAddress']"));
        WebElement newPermanentAddress = driver.findElement(By.xpath("//p[@id='permanentAddress']"));

        String actualName = newName.getText();
        String actualEmail = newEmail.getText();
        String actualCurrentAddress = newCurrentAddress.getText();
        String actualPermanentAddress = newPermanentAddress.getText();

        Assertions.assertAll(
                () -> Assertions.assertTrue(actualName.contains(expectedName)),
                () -> Assertions.assertTrue(actualEmail.contains(expectedEmail)),
                () -> Assertions.assertTrue(actualCurrentAddress.contains(expectedCurrentAddress)),
                () -> Assertions.assertTrue(actualPermanentAddress.contains(expectedPermanentAddress))
        );
    }

    @Test
    public void uploadAndDownloadTest() {
        String fileName = "threadqa.jpeg";

        driver.get("http://85.192.34.140:8081/");
        WebElement elementElements = driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Elements']"));
        elementElements.click();

        WebElement elementUploadAndDownload = driver.findElement(By.xpath("//span[text()='Upload and Download']"));
        elementUploadAndDownload.click();

        WebElement uploadBtn = driver.findElement(By.id("uploadFile"));
        uploadBtn.sendKeys(System.getProperty("user.dir") + "/src/test/resources/" + fileName);

        WebElement uploadedFakePath = driver.findElement(By.xpath("//p[@id='uploadedFilePath']"));
        Assertions.assertTrue(uploadedFakePath.getText().contains(fileName));
    }

    @Test
    @SneakyThrows
    public void downloadTest() {
        driver.get("http://85.192.34.140:8081/");
        WebElement elementElements = driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Elements']"));
        elementElements.click();

        WebElement elementUploadAndDownload = driver.findElement(By.xpath("//span[text()='Upload and Download']"));
        elementUploadAndDownload.click();

        WebElement downloadBtn = driver.findElement(By.id("downloadButton"));
        downloadBtn.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        //TimeUnit.SECONDS.sleep(15);

        wait.until(x-> Paths.get(downloadFolder, File.separator, "sticker.png").toFile().exists());

        File file = new File(downloadFolder + File.separator + "sticker.png");

        Assertions.assertAll(
                () -> Assertions.assertTrue(file.length() != 0),
                () -> Assertions.assertNotNull(file)
        );
    }
}
