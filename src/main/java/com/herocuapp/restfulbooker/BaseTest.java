package com.herocuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import io.qameta.allure.restassured.AllureRestAssured;
import org.testng.annotations.*;

public class BaseTest {
    protected RequestSpecification spec;
    @BeforeClass(alwaysRun = true)
    public void setUp(){
        System.out.println("BeforeMethod запущен для: " + this.getClass().getSimpleName());
        spec = new RequestSpecBuilder().
                setBaseUri("https://restful-booker.herokuapp.com")
                .addFilter(new AllureRestAssured()) // 👈 ключевая строка
                .build();
        //RestAssured.filters(new AllureRestAssured());
    }

    protected Response createBooking() {
        //create JSON body
        JSONObject body = new JSONObject();
        body.put("firstname", "Dmitry");
        body.put("lastname", "Shyshkin");
        body.put("totalprice", 150);
        body.put("depositpaid", false);

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2020-03-25");
        bookingdates.put("checkout", "2020-03-27");
        body.put("bookingdates", bookingdates);
        body.put("additionalneeds", "Baby crib");

        // Get response
        return RestAssured.given(spec).contentType(ContentType.JSON).body(body.toString())
                .post("/booking");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(){
        RestAssured.reset();
    }
}
