package otcuda.zvuk.unitickets.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import otcuda.zvuk.BasePage;


public class UtMainPage extends BasePage {
    private final By townFromField = By.xpath("//input[@placeholder='Откуда']");
    private final By townToField = By.xpath("//input[@placeholder='Куда']");

    private final By listOfCityFrom = By.xpath("//div[@class='origin field active']//div[@class='city']");
    private final By listOfCityTo = By.xpath("//div[@class='destination field active']//div[@class='city']");

    private String dayInCalendarXPath = "//span[text()='%d']";

    private final By dateDepartedField = By.xpath("//input[@placeholder='Туда']");
    private final By dateBackField = By.xpath("//input[@placeholder='Обратно']");

    private final By searchBtn = By.xpath("//div[@class='search_btn']");


    public UtMainPage(WebDriver driver) {
        super(driver);
    }

    public UtMainPage addCityFrom(String townFrom) {
        driver.findElement(townFromField).clear();
        driver.findElement(townFromField).sendKeys(townFrom);
        driver.findElement(townFromField).click();
        waitForTextPresentedInList(listOfCityFrom, townFrom).click();
        return this;
    }

    public UtMainPage addCityTo(String townTo){
        driver.findElement(townToField).clear();
        driver.findElement(townToField).sendKeys(townTo);
        driver.findElement(townToField).click();
        waitForTextPresentedInList(listOfCityTo, townTo).click();
        return this;
    }

    public UtMainPage addDateDeparted(int dayDepartedNumber) {
        driver.findElement(dateDepartedField).click();
        getDay(dayDepartedNumber).click();
        wait.until(ExpectedConditions.invisibilityOf(getDay(dayDepartedNumber)));
        return this;
    }

    public UtMainPage addDateBack(int dayBackNumber){
        driver.findElement(dateBackField).click();
        getDay(dayBackNumber).click();
        wait.until(ExpectedConditions.invisibilityOf(getDay(dayBackNumber)));
        return this;
    }

    public UtSearchResultPage foundTickets(){
        var searchbtn = driver.findElement(searchBtn);
        searchbtn.click();
        return new UtSearchResultPage(driver);
    }

    private WebElement getDay(int day){
        By dayLocatort = By.xpath(String.format(dayInCalendarXPath, day));
        return driver.findElement(dayLocatort);
    }

}

