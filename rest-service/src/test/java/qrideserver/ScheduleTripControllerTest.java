package qrideserver;

import interfaces.WriteDataHandler;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import scheduletrip.ScheduleTripHandlerImpl;

// Scope: Tests /qride/v1/schedule_trip REST API with valid & invalid inputs.
// TODO: CRIO_TEST: Add/modify necessary tests to ensure the REST API is tested well.
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ScheduleTripControllerTest {
  @Autowired private MockMvc mockMvc;

  private ScheduleTripHandlerImpl scheduleTripHandler;

  @Mock private WriteDataHandler writeDataHandler;

  @InjectMocks private ScheduleTripController scheduleTripController;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(scheduleTripController).build();
  }
}
