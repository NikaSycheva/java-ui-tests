package tests.unitickets.pages;


import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class UtSearchResultSelenidePage {

    private SelenideElement titleLoader = $x("//span[@class='countdown-title__text']");
    private SelenideElement selectedDatePrice = $x("//li[@class='price--current']//span[@class='prices__price currency_font currency_font--rub']");

    private SelenideElement selectedDateDeparted = $x("//li[@class='price--current']//span[1]");
    private SelenideElement selectedDateBack = $x("//li[@class='price--current']//span[3]");

    private ElementsCollection listOfDepartedDays =  $$x("//div[@class='ticket-action-airline-container']//following::span[@class='flight-brief-date__day'][1]");
    private ElementsCollection listOfBackDays =  $$x("//div[@class='ticket-action-airline-container']//following::span[@class='flight-brief-date__day'][3]");


    public UtSearchResultSelenidePage assertAllDaysDepartedShouldHaveDay(int expectedDepartedDay){
        String day = String.valueOf(expectedDepartedDay);
        listOfDepartedDays.should(CollectionCondition.containExactTextsCaseSensitive(day));
        return this;
    }

    public UtSearchResultSelenidePage assertAllDaysBackShouldHaveDay(int expectedBackDay){
        String day = String.valueOf(expectedBackDay);
        listOfBackDays.should(CollectionCondition.containExactTextsCaseSensitive(day));
        return this;
    }

    public UtSearchResultSelenidePage assertMainDateDeparted(int expectedDay){
        String day = String.valueOf(expectedDay);
        selectedDateDeparted.should(Condition.partialText(day));
        return this;
    }

    public UtSearchResultSelenidePage assertMainDateBack(int expectedDay){
        String day = String.valueOf(expectedDay);
        selectedDateBack.should(Condition.partialText(day));
        return this;
    }

    public UtSearchResultSelenidePage waitForPage(){
        selectedDatePrice.should(Condition.matchText("\\d+"));
        return this;
    }

    //ждем исчезновения текста в шапке что идет поиск
    public UtSearchResultSelenidePage waitForTitleDisappear(){
        titleLoader.should(Condition.disappear, Duration.ofSeconds(30));
        return this;
    }
}

