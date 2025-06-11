package restfulbooker;

import com.herocuapp.restfulbooker.BaseTest;
import com.herocuapp.restfulbooker.Booking;
import com.herocuapp.restfulbooker.Bookingdates;
import com.herocuapp.restfulbooker.Bookingid;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import io.qameta.allure.*;

@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class CreateBookingTest extends BaseTest {

    //@Test
    public void createBookingTest(){
        Response response = createBooking();
        response.print();

        // Verifications
        // Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

        // Verify All fields
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = response.jsonPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstName, "Dmitry", "firstname in response is not expected");

        String actualLastName = response.jsonPath().getString("booking.lastname");
        softAssert.assertEquals(actualLastName, "Shyshkin", "lastname in response is not expected");

        int price = response.jsonPath().getInt("booking.totalprice");
        softAssert.assertEquals(price, 150, "totalprice in response is not expected");

        boolean depositpaid = response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertFalse(depositpaid, "depositpaid should be false, but it's not");

        String actualCheckin = response.jsonPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2020-03-25", "checkin in response is not expected");

        String actualCheckout = response.jsonPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2020-03-27", "checkout in response is not expected");

        String actualAdditionalneeds = response.jsonPath().getString("booking.additionalneeds");
        softAssert.assertEquals(actualAdditionalneeds, "Baby crib", "additionalneeds in response is not expected");

        softAssert.assertAll();
    }


    @Test(description = "Создание бронирования и проверка всех полей через JSON путь")
    @Epic("Booking API")
    @Feature("Create Booking")
    @Story("Создание нового бронирования")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяется корректность создания бронирования с верификацией каждого поля ответа.")
    @Step("Создание бронирования с именем: {0} и фамилией: {1}")
    public void createBookingWithPOJOTest(){
        Bookingdates bookingdates = new Bookingdates("2020-03-25", "2020-03-27");
        Booking booking = new Booking("Olga", "Shyshkin", 200, false, bookingdates, "Baby crib");

        // Get response
        Response response = RestAssured.given(spec)
                .filter(new AllureRestAssured())
        .contentType(ContentType.JSON).body(booking)
                .post("/booking");
        response.print();

        Bookingid bookingid = response.as(Bookingid.class);

        // Verifications
        // Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");
        System.out.println("Request booking : " + booking.toString());
        System.out.println("Response booking: " + bookingid.getBooking().toString());
        // Verify All fields
        Assert.assertEquals(bookingid.getBooking().toString(),booking.toString());

    }
}
