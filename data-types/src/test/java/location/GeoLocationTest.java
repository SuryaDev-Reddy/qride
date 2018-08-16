package location;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.Test;

public class GeoLocationTest {
  @Test
  public void testJsonDeserialization() throws IOException {
    String jsonToDeserialize = "{ \"latitude\": 12.908486, \"longitude\": 77.536386 }";
    GeoLocation geoLocationActual = null;

    try {
      geoLocationActual = new ObjectMapper().readValue(jsonToDeserialize, GeoLocation.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals("Validate the value of latitude", 12.908486,
        geoLocationActual.getLatitude(), 0.0);
    assertEquals(
        "Validate the value of latitude", 77.536386,
        geoLocationActual.getLongitude(), 0.0);
  }

  @Test(expected = IOException.class)
  public void testIoException() throws IOException {
    String incorrectJsonToDeserialize =
        "{ \"latitude_error\": 12.908486, \"longitude\": 77.536386 }";

    GeoLocation geoLocation =
        new ObjectMapper().readValue(incorrectJsonToDeserialize, GeoLocation.class);
  }
}
