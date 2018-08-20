package car;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class CarDriverTest {
  @Test
  public void testJsonGeneration() {
    final String expectedJsonString =
        "{"
            + "\"driverId\":\"DRIVER_ID_2\","
            + "\"name\":\"MANJUNATH\","
            + "\"phone\":\"9987899891\""
            + "}";
    CarDriver carDriver = new CarDriver();
    carDriver.setDriverId("DRIVER_ID_2");
    carDriver.setName("MANJUNATH");
    carDriver.setPhone("9987899891");

    String actualJsonString = new String();
    try {
      actualJsonString = new ObjectMapper().writeValueAsString(carDriver);
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
