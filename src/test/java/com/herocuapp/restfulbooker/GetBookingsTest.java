package com.herocuapp.restfulbooker;

import com.herocuapp.restfulbooker.BaseTest;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import io.qameta.allure.*;

import java.util.List;

@Listeners({io.qameta.allure.testng.AllureTestNg.class})
@Epic("Booking API")
@Feature("Get Bookings")
public class GetBookingsTest extends BaseTest {

    @Test(groups = {"Booking", "Smoke"})
    @Story("Get list of bookings")
    @Severity(SeverityLevel.NORMAL)
    @Description("Check that request to /booking returns status code 200 and list of bookingIDs not empty")
    public void getBookingIdsWithoutFilterTest(){
        Response response = RestAssured.given(spec)
                .filter(new AllureRestAssured())
                .get("/booking");
        response.print();
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it is not");
        List<Integer> bookingIds = response.jsonPath().getList("bookingId");
        Assert.assertFalse(bookingIds.isEmpty(), "List of booking are empty, but it should not be");
    }

    //@Test(groups = {"Booking", "Regression"})
    @Story("Получение списка бронирований с параметрами firstname и lastname")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверяется, что запрос к /booking с фильтрами firstname= Dmitry и lastname= Shyshkin возвращает статус 200 и непустой список ID бронирований")
    public void getBookingIdsWithFilterTest(){
        // add query parameter to spec
        spec.queryParam("firstname", "Dmitry");
        spec.queryParam("lastname", "Shyshkin");

        // Get response with booking ids
        Response response = RestAssured.given(spec)
                .filter(new AllureRestAssured())
                .get("/booking");
        response.print();

        // Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

        // Verify at least 1 booking id in response
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertFalse(bookingIds.isEmpty(), "List of bookingIds is empty, but it shouldn't be");
    }
}
