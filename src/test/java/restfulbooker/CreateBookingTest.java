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
    @Test(groups = {"Booking", "Smoke"})
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

        // Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");
        System.out.println("Request booking : " + booking.toString());
        System.out.println("Response booking: " + bookingid.getBooking().toString());
        // Verify All fields
        Assert.assertEquals(bookingid.getBooking().toString(),booking.toString());
    }
}
