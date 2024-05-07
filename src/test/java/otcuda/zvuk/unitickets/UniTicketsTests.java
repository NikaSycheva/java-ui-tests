package otcuda.zvuk.unitickets;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import otcuda.zvuk.BaseTest;
import otcuda.zvuk.unitickets.pages.UtMainPage;
import otcuda.zvuk.unitickets.pages.UtSearchResultPage;

import java.util.List;

public class UniTicketsTests extends BaseTest {

    @BeforeEach
    public void openSite(){
        driver.get("https://uniticket.ru/");
    }

    @Test
    public void searchTrip(){
        String townFrom = "Дубай";
        String townTo = "Казань";
        int expectedDayDeparted = 25;
        int expectedDayBack = 30;

        UtSearchResultPage searchPage =
                new UtMainPage(driver)
                        .addCityFrom(townFrom)
                        .addCityTo(townTo)
                        .addDateDeparted(expectedDayDeparted)
                        .addDateBack(expectedDayBack)
                        .foundTickets()
                        .waitForTitleDisappear()
                        .waitLoadSelectedDatePrice();

        int actualDaysDeparted = searchPage.getMainDateDeparted();
        int actualDaysBack = searchPage.getMainDateBack();

        Assertions.assertEquals(expectedDayDeparted, actualDaysDeparted);
        Assertions.assertEquals(expectedDayBack, actualDaysBack);

        List<Integer> listDaysDeparted = searchPage.getDaysDeparted();
        List<Integer> listDaysBack = searchPage.getDaysBack();
        boolean isAllDaysDepartedOk = listDaysDeparted.stream()
                .allMatch(x->x==expectedDayDeparted);
        boolean isAllDaysBackOk = listDaysBack.stream()
                .allMatch(x->x==expectedDayBack);

        Assertions.assertTrue(isAllDaysDepartedOk,"Дни вылета на странице выдачи не совпадают");
        Assertions.assertTrue(isAllDaysBackOk, "Дни прилета обратно на странице выдачи не совпадают");
    }

}

