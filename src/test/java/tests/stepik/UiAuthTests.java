package tests.stepik;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$x;

public class UiAuthTests {
    private String login = "ebareza@yandex.ru";
    private String password = "NikaSochi201&";

    @Test
    @DisplayName("Проверка авторизации с главной страницы")
    public void uiAuthTest(){
        Selenide.open("https://stepik.org/catalog");
        $x("//a[contains(@class, 'ember-view navbar__auth navbar__auth_login st-link st-link_style_button')]").click();
        $x("//input[@name='login']").clear();
        $x("//input[@name='login']").sendKeys(login);
        $x("//input[@name='password']").clear();
        $x("//input[@name='password']").sendKeys(password);
        $x("//button[@class='sign-form__btn button_with-loader ']").click();

        $x("//img[@alt='User avatar']").should(Condition.visible).click();
        $x("//li[@data-qa='menu-item-profile']").should(Condition.visible).click();
        $x("//h1").should(Condition.text("Ника Сычева"));
    }
}

