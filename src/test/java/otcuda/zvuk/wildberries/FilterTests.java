package otcuda.zvuk.wildberries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import otcuda.zvuk.BaseTest;
import otcuda.zvuk.wildberries.pages.ItemPage;
import otcuda.zvuk.wildberries.pages.MainPage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class FilterTests extends BaseTest {

    @BeforeEach
    public void openSite(){
        driver.get("https://www.wildberries.ru/");
    }

    @Test
    public void searchResultTest(){
        String expectedItem = "Iphone";
        Integer expectedMinPrice = 36000;
        Integer expectedMaxPrice = 60000;

        ItemPage itemPage = new MainPage(driver)
                .searchItem(expectedItem)
                .openFilters()
                .setMinPrice(expectedMinPrice)
                .setMaxPrice(expectedMaxPrice)
                .applyFilters()
                .openItem();

        String actualItem = itemPage.getItemName();
        List<Integer> actualPrice = itemPage.getItemPrice();

        assertThat(actualItem.toLowerCase()).contains(expectedItem.toLowerCase());
        assertThat(actualPrice)
                .allMatch(it ->it <= expectedMaxPrice && it >= expectedMinPrice);
    }

    @Test
    @Disabled("сейчас на вб неактуально, тк 2 цены NumberFormatException: For input string: \"3622237343\"")
    public void searchResultWithJaExecutorTest(){
        String expectedItem = "Iphone";
        Integer expectedMinPrice = 36000;
        Integer expectedMaxPrice = 60000;

        ItemPage itemPage =  new MainPage(driver)
                .searchItem(expectedItem)
                .openFilters()
                .setMinPrice(expectedMinPrice)
                .setMaxPrice(expectedMaxPrice)
                .applyFilters()
                .openItem();

        String actualItem = itemPage.getItemName();
        Integer actualPrice = itemPage.getItemPriceWithJsExecutor();

        Assertions.assertAll(
                () -> Assertions.assertTrue(actualItem.toLowerCase().contains(expectedItem.toLowerCase())),
                () -> Assertions.assertTrue(actualPrice <= expectedMaxPrice && actualPrice >= expectedMinPrice)
        );
    }
}

