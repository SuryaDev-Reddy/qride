package qrideserver;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import carsinlocation.CarsInLocationHandlerImpl;
import globals.GlobalConstants;
import interfaces.ReadDataHandler;
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
import tables.setup.TableSetup;

// Scope: Tests /qride/v1/cars_in_location REST API with valid & invalid inputs.
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarsInLocationControllerTests {

  @Autowired private MockMvc mockMvc;

  private CarsInLocationHandlerImpl carsInLocationHandler;

  @Mock private ReadDataHandler readDataHandler;

  @InjectMocks private CarsInLocationController carsInLocationController;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(carsInLocationController).build();

    // Setup the persistence context first before initializing the tables.
    GlobalConstants.initDatabase(GlobalConstants.PERSISTENCE_UNIT_NAME);

    // Setup the tables.
    TableSetup tableSetup = new TableSetup();
    tableSetup.init();
  }

  @Test
  public void normalCase() throws Exception {
    String requestBody = "{\"geoLocation\": {\"latitude\": 12.908486, \"longitude\": 77.536386}}";

    this.mockMvc
        .perform(
            get("/qride/v1/cars_in_location")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.carStatuses").isArray())
        .andExpect(jsonPath("$.carStatuses", hasSize(22)))
        .andExpect(jsonPath("$.carStatuses[0].carStatus.geoLocation.latitude").value(12.898976951279089));
  }
}
