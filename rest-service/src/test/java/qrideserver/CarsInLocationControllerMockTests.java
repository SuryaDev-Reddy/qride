package qrideserver;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import car.CarStatus;
import carsinlocation.CarsInLocationHandlerImpl;
import interfaces.ReadDataHandler;
import java.util.ArrayList;
import java.util.List;
import location.GeoLocation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import read.ReadDataHandlerImpl;
import utils.TestUtils;

// Scope: Tests /qride/v1/cars_in_location REST API with valid & invalid inputs.
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarsInLocationControllerMockTests {

  @Autowired private MockMvc mockMvc;
  private CarsInLocationHandlerImpl carsInLocationHandler;
  @Mock private ReadDataHandler readDataHandler;
  @InjectMocks private CarsInLocationController carsInLocationController;

  @Before
  public void init() {

    MockitoAnnotations.initMocks(this);
    // Setup a mock class to easily run your tests.
    readDataHandler = Mockito.mock(ReadDataHandlerImpl.class);
    Answer<List<CarStatus>> answer =
        new Answer<List<CarStatus>>() {

          public List<CarStatus> answer(InvocationOnMock invocation) throws Throwable {
            GeoLocation geoLocation = invocation.getArgumentAt(0, GeoLocation.class);
            return generateCarStatus(geoLocation);
          }
        };
    when(readDataHandler.getCarsInLocation(isA(GeoLocation.class))).thenAnswer(answer);
    carsInLocationHandler = new CarsInLocationHandlerImpl();
    carsInLocationHandler.setReadDataHandler(readDataHandler);
    carsInLocationController.setCarsInLocationHandler(carsInLocationHandler);
    mockMvc = MockMvcBuilders.standaloneSetup(carsInLocationController).build();
  }



  // Utility function to generate a bunch of cars based on the geoLocation passed to help testing.
  private List<CarStatus> generateCarStatus(GeoLocation geoLocation) {
    List<CarStatus> carStatusList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      CarStatus carStatus = new CarStatus();
      carStatus.setGeoLocation(
          TestUtils.createGeoLocationObject(
              geoLocation.getLatitude() + i, geoLocation.getLongitude() + i));
      carStatus.setCarAvailability(CarStatus.CarAvailability.CAR_AVAILABLE);
      carStatusList.add(carStatus);
    }
    return carStatusList;
  }



  @Test
  public void noParamShouldReturnClientError() throws Exception {
    this.mockMvc
        .perform(get("/qride/v1/cars_in_location"))
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }



  // Note the small letter 'l' in geolocation which is the error.
  @Test
  public void incorrectGeoLocationParamShouldReturnClientError() throws Exception {
    String requestBody = "{\"geolocation\": {\"latitude\": 12.0, \"longitude\": 77.0}}";
    this.mockMvc
        .perform(
            get("/qride/v1/cars_in_location")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }



  @Test
  public void emptyLatitudeParamShouldReturnClientError() throws Exception {

    String requestBody = "{\"geoLocation\": {\"longitude\": 77.0}}";
    this.mockMvc
        .perform(
            get("/qride/v1/cars_in_location")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }



  @Test
  public void normalCase() throws Exception {

    String requestBody = "{\"geoLocation\": {\"latitude\": 12.0, \"longitude\": 77.0}}";

    this.mockMvc
        .perform(
            get("/qride/v1/cars_in_location")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.carStatuses").isArray())
        .andExpect(jsonPath("$.carStatuses", hasSize(5)))
        .andExpect(jsonPath("$.carStatuses[0].carStatus.geoLocation.latitude").value(12.0));
  }
}