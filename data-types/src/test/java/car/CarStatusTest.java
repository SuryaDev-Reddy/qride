package car;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import location.GeoLocation;
import org.junit.Test;

public class CarStatusTest {
  @Test
  public void testJsonGeneration() {
    final String expectedJsonString =
        "{\"geoLocation\":{\"latitude\":12.908486,\"longitude\":77.536386},"
            + "\"carAvailability\":\"CAR_AVAILABLE\"}";

    // Setting up an CarStatus object for testing.
    CarStatus carStatus = new CarStatus();
    carStatus.setCarAvailability(CarStatus.CarAvailability.CAR_AVAILABLE);
    GeoLocation geoLocation = new GeoLocation();
    geoLocation.setLatitude(12.908486);
    geoLocation.setLongitude(77.536386);
    carStatus.setGeoLocation(geoLocation);

    String actualJsonString = "";

    try {
      actualJsonString = new ObjectMapper().writeValueAsString(carStatus);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    assertEquals("Validate if the generated json is correct", expectedJsonString, actualJsonString);
  }
}
