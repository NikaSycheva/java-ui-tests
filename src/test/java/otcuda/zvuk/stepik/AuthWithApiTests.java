package otcuda.zvuk.stepik;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import otcuda.zvuk.stepik.models.StepikAuthModel;

import java.util.*;

import static com.codeborne.selenide.Selenide.$x;
import static io.restassured.RestAssured.given;

public class AuthWithApiTests {
    private StepikAuthModel testUser;

    @BeforeEach
    public void init(){
        Selenide.open("https://stepik.org/catalog");
        testUser = new StepikAuthModel("ebareza@yandex.ru", "NikaSochi201&");
    }

    @Test
    public void checkUserName(){
        authApi(testUser);
        $x("//img[@alt='User avatar']").should(Condition.visible).click();
        $x("//li[@data-qa='menu-item-profile']").should(Condition.visible).click();
        $x("//h1").should(Condition.text("Ника Сычева"));
    }

    private void authApi(StepikAuthModel user){
    }

    @Test
    public void apiAuthTest(){
        Set<Cookie> cookiesBrowser = WebDriverRunner.getWebDriver().manage().getCookies();
        Map<String, String> authHeaders = new HashMap<>();
        authHeaders.put("Referer", "https://stepik.org/catalog?auth=login");
        authHeaders.put("Origin", "https://stepik.org");//должно быть переменной в ресурсах
        authHeaders.put("authority", "stepik.org");

        //куки из селенида нужно переделать в куки ресташурда
        List<io.restassured.http.Cookie> restAssuredCookies = new ArrayList<>();
        for (Cookie cookie : cookiesBrowser) {
            io.restassured.http.Cookie temp = new io.restassured.http.Cookie
                    .Builder(cookie.getName(), cookie.getValue())
                    .setDomain(cookie.getDomain())
                    .setPath("/")
                    .build();
            restAssuredCookies.add(temp);
            if(cookie.getName().equals("csrftoken")){
                authHeaders.put("X-Csrftoken", cookie.getValue());
            }
        }

        Map<String, String> authCookies = given().contentType(ContentType.JSON)
                .body(testUser)
                .headers(authHeaders)
                .cookies(new Cookies(restAssuredCookies))
                .post("https://stepik.org/api/users/login")
                .then().log().all().extract().cookies();

        Cookie csrf = new Cookie("csrftoken", authCookies.get("csrftoken"),
                "stepik.org", "/", null);
        Cookie sessionId = new Cookie("sessionid", authCookies.get("sessionid"),
                "stepik.org", "/", null);

        //помещаем эти куки в браузер
        WebDriverRunner.getWebDriver().manage().addCookie(csrf);
        WebDriverRunner.getWebDriver().manage().addCookie(sessionId);
        Selenide.refresh();

        checkUserName();

        $x("//img[@alt='User avatar']").should(Condition.visible).click();
        $x("//li[@data-qa='menu-item-profile']").should(Condition.visible).click();
        $x("//h1").should(Condition.text("Ника Сычева"));

    }
}
