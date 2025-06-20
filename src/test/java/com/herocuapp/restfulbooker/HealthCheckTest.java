package com.herocuapp.restfulbooker;

import com.herocuapp.restfulbooker.BaseTest;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Listeners({io.qameta.allure.testng.AllureTestNg.class})
@Epic("Health API")
@Feature("Health Check")
public class HealthCheckTest extends BaseTest {
    @Test(groups = {"Health"})
    @Story("Check service status")
    @Severity(SeverityLevel.MINOR)
    @Description("send GET on /ping and check that request status code is 201")
    public void healthCheckTest(){
        System.out.println("Health check");
        given().spec(spec).when().
                when().
                get("/ping").
        then().
                assertThat().
                statusCode(201);
    }

    @Test
    public void headersAndCookiesTest() {
        Header someHeader = new Header("some_name", "Some_value");
        spec.header(someHeader);

        Cookie someCookie = new Cookie.Builder("some cookie", "some cookie value").build();
        spec.cookie(someCookie);

        Response response = RestAssured.given(spec).
                cookie("Test cookie name", "Test cookie value").
                header("Test header name", "Test header Value").log().all().get("/ping");

        // Get headers
        Headers headers = response.getHeaders();
        System.out.println("Headers: " + headers);

        Header serverHeader1 = headers.get("Server");
        System.out.println(serverHeader1.getName() + ": " + serverHeader1.getValue());

        String serverHeader2 = response.getHeader("Server");
        System.out.println("Server: " + serverHeader2);

        // Get cookies
        Cookies cookies = response.getDetailedCookies();
        System.out.println("Cookies: " + cookies);
    }
}
