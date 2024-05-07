package otcuda.zvuk.unitickets.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class UtMainSelenidePage {

    private SelenideElement townFromField = $x("//input[@placeholder='Откуда']");
    private SelenideElement townToField = $x("//input[@placeholder='Куда']");

    private ElementsCollection listOfCityFrom = $$x("//div[@class='origin field active']//div[@class='city']");
    private ElementsCollection listOfCityTo = $$x("//div[@class='destination field active']//div[@class='city']");

    private String dayInCalendarXPath = "//span[text()='%d']";

    private SelenideElement dateDepartedField = $x("//input[@placeholder='Туда']");
    private SelenideElement dateBackField = $x("//input[@placeholder='Обратно']");

    private SelenideElement searchBtn = $x("//div[@class='search_btn']");

    public UtMainSelenidePage addCityFrom(String townFrom) {
        townFromField.clear();
        townFromField.sendKeys(townFrom);
        townFromField.click();
        listOfCityFrom.find(Condition.partialText(townFrom)).click();
        return this;
    }

    public UtMainSelenidePage addCityTo(String townTo){
        townToField.clear();
        townToField.sendKeys(townTo);
        townToField.click();
        listOfCityTo.find(Condition.partialText(townTo)).click();
        return this;
    }

    public UtMainSelenidePage addDateDeparted(int dayDepartedNumber) {
        dateDepartedField.click();
        getDay(dayDepartedNumber).click();
        $x("//h2").click();
        return this;
    }

    public UtMainSelenidePage addDateBack(int dayBackNumber){
        dateBackField.click();
        getDay(dayBackNumber).click();
        $x("//h2").click();
        return this;
    }

    public UtSearchResultSelenidePage foundTickets(){
        searchBtn.click();
        return Selenide.page(UtSearchResultSelenidePage.class);
    }

    private SelenideElement getDay(int day){
        return $x(String.format(dayInCalendarXPath, day));
    }
}

