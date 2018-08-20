package car;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class CarTest {
  @Test
  public void testJsonGeneration() {
    final String expectedJsonString =
        "{\"carId\":\"CAR_ID_1\","
            + " \"carType\": \"CAR_TYPE_HATCHBACK\","
            + "\"carModel\": \"TOYOTA ETIOS\","
            + "\"carLicense\":\"KA03 3122\"}";

    Car car = new Car();
    car.setCarId("CAR_ID_1");
    car.setCarType(Car.CarType.CAR_TYPE_HATCHBACK);
    car.setCarModel("TOYOTA ETIOS");
    car.setCarLicense("KA03 3122");

    String actualJsonString = new String();
    try {
      actualJsonString = new ObjectMapper().writeValueAsString(car);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    try {
      JSONAssert.assertEquals(expectedJsonString, actualJsonString, true);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }
}
