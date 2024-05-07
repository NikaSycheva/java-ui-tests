package otcuda.zvuk.unitickets.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

import otcuda.zvuk.BasePage;


public class UtSearchResultPage extends BasePage {
    private final By titleLoader = By.xpath("//span[@class='countdown-title__text']");
    private final By selectedDatePrice = By.xpath("//li[@class='price--current']//span[4]");
    private final By selectedDateDeparted = By.xpath("//li[@class='price--current']//span[1]");
    private final By selectedDateBack = By.xpath("//li[@class='price--current']//span[3]");

    private final By listOfDepartedDays =  By.xpath("//div[@class='ticket-action-airline-container']//following::span[@class='flight-brief-date__day'][1]");
    private final By listOfBackDays =  By.xpath("//div[@class='ticket-action-airline-container']//following::span[@class='flight-brief-date__day'][3]");

    protected UtSearchResultPage(WebDriver driver) {
        super(driver);
    }

    public List<Integer> getDaysDeparted(){
        return getDigitsFromList(listOfDepartedDays);
    }

    public List<Integer> getDaysBack(){
        return getDigitsFromList(listOfBackDays);
    }

    public Integer getMainDateDeparted(){
        return getDigitFromWebElement(driver.findElement(selectedDateDeparted));
    }

    public Integer getMainDateBack(){
        return getDigitFromWebElement(driver.findElement(selectedDateBack));
    }

    public UtSearchResultPage waitLoadSelectedDatePrice(){
        waitForElementAppear(selectedDateDeparted);
        waitForTextMatchesRegex(selectedDatePrice, "\\d+");
        return this;
    }

    //ждем исчезновения текста в шапке что идет поиск
    public UtSearchResultPage waitForTitleDisappear(){
        waitForElementDisappear(titleLoader);
        return this;
    }
}

