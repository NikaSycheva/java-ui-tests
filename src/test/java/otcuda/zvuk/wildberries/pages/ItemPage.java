package otcuda.zvuk.wildberries.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import otcuda.zvuk.BasePage;

import java.util.Arrays;
import java.util.List;

public class ItemPage extends BasePage {
    private final By itemTitle = By.xpath("//h1[@class='product-page__title']");
    private final By itemPrice = By.xpath("//span[@class='price-block__price']");

    public ItemPage(WebDriver driver) {
        super(driver);
    }

    public String getItemName() {
        return driver.findElement(itemTitle).getText();
    }

    public List<Integer> getItemPrice() {
        WebElement twoPrices = driver.findElement(itemPrice);
        String twoPricesString = twoPrices.getText();

        return Arrays.stream(twoPricesString.split("\n"))
                .map(it -> it.replaceAll("[^0-9.]", ""))
                .map(Integer::parseInt)
                .toList();

        /*String[] priceText = twoPricesString.split("\n");
        priceText[0] = priceText[0].replaceAll("[^0-9.]", "");
        priceText[1] = priceText[1].replaceAll("[^0-9.]", "");

        int priceText1 = Integer.parseInt(priceText[0]);
        int priceText2 = Integer.parseInt(priceText[1]);

        return new Integer[]{priceText1, priceText2};*/
    }

    public Integer getItemPriceWithJsExecutor() {
        String priceText = getTextJs(itemPrice);
        priceText = priceText.replaceAll("[^0-9.]", "");
        return Integer.parseInt(priceText);
    }
}
