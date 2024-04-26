package tests.wildberries.pages;
import org.openqa.selenium.*;
import tests.BasePage;


public class SearchResultPage extends BasePage {

    private final By allFiltersBtn = By.xpath("//button[@class='dropdown-filter__btn dropdown-filter__btn--all']");
    private final By startPriceField = By.xpath("//input[@name='startN']");
    private final By endPriceField = By.xpath("//input[@name='endN']");
    private final By showFiltersBtn = By.xpath("//button[text()='Показать']");
    private final By items = By.xpath("//div[@class='product-card-list']//article");

    public SearchResultPage(WebDriver driver){
        super(driver);
    }

    public SearchResultPage openFilters(){
        driver.findElement(allFiltersBtn).click();
        return this;
    }

    public SearchResultPage setMinPrice(Integer minPrice){
        driver.findElement(startPriceField).clear();
        driver.findElement(startPriceField).sendKeys(String.valueOf(minPrice));
        return this;
    }

    public SearchResultPage setMaxPrice(Integer maxPrice){
        clearTextFieldFull(endPriceField);//потому что у Олега не получалось отчистить поле по-простому
        driver.findElement(endPriceField).sendKeys(String.valueOf(maxPrice));
        return this;
    }

    public SearchResultPage applyFilters(){
        driver.findElement(showFiltersBtn).click();
        waitForElementUpdated(items);//у меня работало и без этого. но может выдавать ошибку, что изменились элементы и не найти
        return this;
    }


    public ItemPage openItem(){
        //берем первый в списке выдачи товар
        driver.findElements(items).get(0).click();
        waitPageLoadsWb();
        return new ItemPage(driver);

        //берем в списке выдачи любой товар iPhone 11
        /*String item = "iPhone 11";
        driver.findElements(items).stream()
                .filter(x -> x.getText().contains(item))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Товар" + item + "не найден"))
                 .click();*/

    }


}
