package scheduletrip;

import interfaces.ScheduleTripHandler;
import interfaces.WriteDataHandler;
import write.WriteDataHandlerImpl;

public class ScheduleTripHandlerImpl implements ScheduleTripHandler {
  // Used to write data to storage.
  private WriteDataHandler writeDataHandler;

  public ScheduleTripHandlerImpl() {
    writeDataHandler = new WriteDataHandlerImpl();
  }

  @Override
  public ScheduleTripResponse scheduleTrip(ScheduleTripRequest scheduleTripRequest) {
    // TODO: CRIO_TASK: - Implement interface ScheduleTripHandler.scheduleTrip().
    // Schedule a trip when car type, the estimated price, and
    // the source/destination locations are given.
    // Input:
    //   scheduleTripRequest - Contains valid car type, estimate price & source &
    //                         destination locations.
    // Output:
    //   1. If we are able to find a car available of this particular car type within
    //      the search radius, schedule it & return ScheduleTripResponse populated with:
    //        - scheduleTripStatus = SUCCESSFULLY_BOOKED.
    //        - trip details with updated startTimeInEpochs
    //          & tripStatus = TRIP_STATUS_SCHEDULED.
    //   2. If no cars are available, return ScheduleTripResponse populated with:
    //        - scheduleTripStatus = NO_CARS_AVAILABLE without any trip information.
    assert (scheduleTripRequest != null);

    return null;
  }

  public void setWriteDataHandler(WriteDataHandler writeDataHandler) {
    this.writeDataHandler = writeDataHandler;
  }
}
