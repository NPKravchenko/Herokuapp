package com.herocuapp.restfulbooker;

import com.herocuapp.restfulbooker.BaseTest;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({io.qameta.allure.testng.AllureTestNg.class})
@Epic("Booking API")
@Feature("Delete Booking")
public class DeleteBookingTest extends BaseTest {

    @Test(groups = {"Booking", "Smoke"})
    @Story("Delete existed booking based on ID")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Deletes booking and checking that it is deleted")
    public void deleteBookingTest() {
        // Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        // Get bookingId of new booking
        int bookingid = responseCreate.jsonPath().getInt("bookingid");

        // delete booking
        Response responseDelete = RestAssured.given(spec)
                .filter(new AllureRestAssured())
                .auth().preemptive().
                basic("admin", "password123").
                delete("/booking/" + bookingid);
        responseDelete.print();

        // Verifications
        // Verify response 201
        Assert.assertEquals(responseDelete.getStatusCode(), 201, "Status code should be 201, but it's not.");

        Response responseGet = RestAssured.get("https://restful-booker.herokuapp.com/booking/" + bookingid);
        responseGet.print();
        Assert.assertEquals(responseGet.getBody().asString(), "Not Found", "Body should be 'Not found', but it's not ");
    }
}
