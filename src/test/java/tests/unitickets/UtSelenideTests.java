package tests.unitickets;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.unitickets.pages.UtMainPage;
import tests.unitickets.pages.UtMainSelenidePage;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class UtSelenideTests {
    @BeforeEach
    public void openSite(){
        Selenide.open("https://uniticket.ru/");
    }

    @Test
    public void firstSelenideTest() {
        SelenideElement heasder = $x("//h1");
        heasder.should(Condition.text("Поиск дорогих авиабилетов"));
        heasder.exists();//содержит элемент

        WebDriverRunner.getWebDriver();

        Selenide.actions();
        Selenide.page(UtMainPage.class).addCityTo("");

        ElementsCollection elements = $$x("//input");
        elements.find(Condition.partialText("Казань")).click();
        var list = elements.texts();//вернет список стрингов текста всех элементов
        //elements.should(CollectionCondition.)
    }


    @Test
    public void searchTrip(){
        String townFrom = "Дубай";
        String townTo = "Казань";
        int expectedDayDeparted = 25;
        int expectedDayBack = 30;

        UtMainSelenidePage mainPage = new UtMainSelenidePage();
        mainPage.addCityFrom(townFrom)
                .addCityTo(townTo)
                .addDateDeparted(expectedDayDeparted)
                .addDateBack(expectedDayBack)
                .foundTickets()
                .waitForPage()
                .waitForTitleDisappear()
                .assertMainDateDeparted(expectedDayDeparted)
                .assertMainDateBack(expectedDayBack)
                .assertAllDaysDepartedShouldHaveDay(expectedDayDeparted)
                .assertAllDaysBackShouldHaveDay(expectedDayBack);

    }
}
