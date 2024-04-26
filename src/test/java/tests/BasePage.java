package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected Actions actions;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        js = (JavascriptExecutor) driver;
    }

    public String getTextJs(By elementLocator){
        return (String) js.executeScript("return arguments[0].textContent;",
                driver.findElement(elementLocator));
    }

    public void jsClick(By elementLocator){
        js.executeScript("arguments[0].click", driver.findElement(elementLocator));
    }

    public void waitPageLoadsWb(){
        By pageLoader = By.xpath("//div[@class='general-preloader']");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(pageLoader));
    }

    public void waitForElementUpdated(By elementLocator){
        wait.until(ExpectedConditions.stalenessOf(driver.findElement(elementLocator)));

    }

    public void clearTextFieldFull(By elementLocator){
        driver.findElement(elementLocator).clear();

        /*если не получается удалить обычно
        driver.findElement(elementLocator).clear();
        driver.findElement(elementLocator).sendKeys(Keys.LEFT_CONTROL + "A");
        driver.findElement(elementLocator).sendKeys(Keys.BACK_SPACE);*/
    }

    public WebElement waitForTextPresentedInList(By list, String value){
        wait.until(ExpectedConditions.elementToBeClickable(list));
        return driver.findElements(list).stream()
                .filter(x->x.getText().contains(value))
                .findFirst()
                .orElseThrow(()-> new NoSuchElementException("Города" + value + " нет"));
    }


    public void waitForTextMatchesRegex(By locator, String regex){
        Pattern pattern = Pattern.compile(regex);
        wait.until(ExpectedConditions.textMatches(locator, pattern));
    }

    public void waitForElementDisappear(By locator){
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void waitForElementAppear(By locator){
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public Integer getDigitFromWebElement(WebElement element){
        String text = element.getText().replaceAll("[^0-9.]", "");
        return Integer.parseInt(text);
    }

    public List<Integer> getDigitsFromList(By locator){
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        return driver.findElements(locator).stream()
                .filter(WebElement::isDisplayed)//filter(x->x.isDisplayed())
                .map(x->getDigitFromWebElement(x))//map(x->getDigitFromWebElement(x))
                .toList();

    }
}

