package qrideserver;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import globals.GlobalConstants;
import interfaces.ReadDataHandler;
import interfaces.WriteDataHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import scheduletrip.ScheduleTripHandlerImpl;
import tables.setup.TableSetup;

// Scope: Tests /qride/v1/schedule_trip REST API with valid & invalid inputs.
// TODO: CRIO_TEST: Add/modify necessary tests to ensure the REST API is tested well.
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ScheduleTripControllerTest {
  @Autowired private MockMvc mockMvc;

  private ScheduleTripHandlerImpl scheduleTripHandler;

  @Mock private WriteDataHandler writeDataHandler;

  @Mock private ReadDataHandler readDataHandler;

  @InjectMocks private ScheduleTripController scheduleTripController;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(scheduleTripController).build();

    // Setup the persistence context first before initializing the tables.
    GlobalConstants.initDatabase(GlobalConstants.PERSISTENCE_UNIT_NAME);

    // Setup the tables.
    TableSetup tableSetup = new TableSetup();
    tableSetup.init();
  }

  // {
//  "carType": "CAR_TYPE_HATCHBACK",
//  "tripPrice": 129.00,
//  "sourceLocation": {
//    "latitude": 12.908486,
//    "longitude": 77.536386
//  },
//  "destinationLocation": {
//    "latitude": 13.808486,
//    "longitude": 77.038396
//  }
// }
  @Test
  public void isEqualSourceDestination() throws Exception {
    String requestBody = "{\"carType\": \"CAR_TYPE_HATCHBACK\", \"tripPrice\": 129.00, \"sourceLocation\": { \"latitude\": 12.908486, \"longitude\": 77.536386 }, "
        + "\"destinationLocation\": { \"latitude\": 12.908486, \"longitude\": 77.536386 } } ";
    this.mockMvc
        .perform(
            get("/qride/v1/schedule_trip")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }

  @Test
  public void checkOutput1() throws Exception {
    String requestBody = "{\"carType\": \"CAR_TYPE_HATCHBACK\", \"tripPrice\": 129.00, \"sourceLocation\": { \"latitude\": 12.908486, \"longitude\": 77.536386 }, "
        + "\"destinationLocation\": { \"latitude\": 13.808486, \"longitude\": 77.038396 } } ";
    System.out.println("SURYA_TESTING" + requestBody);


    this.mockMvc
        .perform(
            get("/qride/v1/schedule_trip")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andDo(print())
        .andExpect(status().isAccepted())
        .andExpect(jsonPath("$.trip").exists())
        .andExpect(jsonPath("$.scheduleTripStatus").value("SUCCESSFULLY_BOOKED"))
        .andExpect(jsonPath("$.trip.tripStatus").value("TRIP_STATUS_SCHEDULED"));
  }
}
