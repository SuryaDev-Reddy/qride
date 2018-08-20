package scheduletrip;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import car.Car.CarType;
import car.CarStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.ReadDataHandler;
import interfaces.ScheduleTripHandler;
import interfaces.WriteDataHandler;
import java.util.ArrayList;
import java.util.List;
import location.GeoLocation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import trip.ScheduleTripTransactionInput;
import trip.ScheduleTripTransactionResult;
import trip.ScheduleTripTransactionResult.ScheduleTripTransactionStatus;
import trip.Trip;
import trip.Trip.TripStatus;
import utils.TestUtils;

// Scope: Tests ScheduleTripHandlerImpl.scheduleTrip() function.
public class ScheduleTripHandlerImplTest {

  private final ScheduleTripHandlerImpl scheduleTripHandlerImpl = new ScheduleTripHandlerImpl();

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}


  @Test
  public void scheduletripRegularCase() throws Exception {

    WriteDataHandler writeDataHandler = Mockito.mock(WriteDataHandler.class);

    GeoLocation sourceLocation = TestUtils.createGeoLocationObject(12.908486,77.536386);
    GeoLocation destinationLocation = TestUtils.createGeoLocationObject(12.908486,78.536386);

    ScheduleTripTransactionInput scheduleTripTransactionInput = new ScheduleTripTransactionInput(
        CarType.CAR_TYPE_HATCHBACK,(float)129,sourceLocation,destinationLocation);

    when(writeDataHandler.scheduleTrip(scheduleTripTransactionInput))
        .thenReturn(generateTripInfo(scheduleTripTransactionInput));
    //when(writeDataHandler.scheduleTrip(another)).thenReturn();

    scheduleTripHandlerImpl.setWriteDataHandler(writeDataHandler);

    ScheduleTripRequest scheduleTripRequest = new ScheduleTripRequest();
    scheduleTripRequest.setCarType(CarType.CAR_TYPE_HATCHBACK);
    scheduleTripRequest.setSourceLocation(sourceLocation);
    scheduleTripRequest.setDestinationLocation(destinationLocation);
    scheduleTripRequest.setTripPrice((float)129);


    ScheduleTripResponse scheduleTripResponse = scheduleTripHandlerImpl.scheduleTrip(scheduleTripRequest);

    ScheduleTripTransactionResult scheduleTripTransactionResult = generateTripInfo(scheduleTripTransactionInput);

    assertEquals(scheduleTripResponse.getScheduleTripStatus(),
        scheduleTripTransactionResult.getScheduleTripTransactionStatus());
    assertEquals(scheduleTripResponse.getTrip().toString(), scheduleTripTransactionResult.getTrip().toString());


    /* compare ScheduleTrip Response with the logic in generateTripInfo*/
    String responseJsonString = "";
    try {
      responseJsonString = new ObjectMapper().writeValueAsString(scheduleTripResponse);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    System.out.println(responseJsonString);
  }


  public ScheduleTripTransactionResult generateTripInfo(ScheduleTripTransactionInput scheduleTripTransactionInput) {

    ScheduleTripTransactionResult scheduleTripTransactionResult = new ScheduleTripTransactionResult();
    scheduleTripTransactionResult.setScheduleTripTransactionStatus(ScheduleTripTransactionStatus.SUCCESSFULLY_BOOKED);

    Trip trip = new Trip();
    trip.setTripId("TRIP_ID5");
    trip.setCarId("CAR_ID8");
    trip.setDriverId("DRIVER_ID8");
    trip.setSourceLocation(scheduleTripTransactionInput.getSourceLocation());
    trip.setDestinationLocation(scheduleTripTransactionInput.getDestinationLocation());
    trip.setStartTimeInEpochs((long) 12345678);
    trip.setTripPrice(scheduleTripTransactionInput.getTripPrice());
    trip.setTripStatus(TripStatus.TRIP_STATUS_SCHEDULED);

    scheduleTripTransactionResult.setTrip(trip);

    return scheduleTripTransactionResult;
  }

}
