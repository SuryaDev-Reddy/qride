package scheduletrip;

import car.Car;
import car.CarStatus;
import car.CarStatus.CarAvailability;
import fareestimates.FareEstimatesRequest;
import interfaces.ReadDataHandler;
import interfaces.ScheduleTripHandler;
import interfaces.WriteDataHandler;
import java.util.List;
import location.GeoLocation;
import read.ReadDataHandlerImpl;
import trip.ScheduleTripTransactionInput;
import trip.ScheduleTripTransactionResult;
import trip.ScheduleTripTransactionResult.ScheduleTripTransactionStatus;
import trip.Trip;
import trip.Trip.TripStatus;
import write.WriteDataHandlerImpl;

public class ScheduleTripHandlerImpl implements ScheduleTripHandler {
  // Used to write data to storage.
  private WriteDataHandler writeDataHandler;

  private ReadDataHandler readDataHandler;

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

    GeoLocation sourceLocation = scheduleTripRequest.getSourceLocation();
    GeoLocation destinationLocation = scheduleTripRequest.getDestinationLocation();
    Car.CarType carType = scheduleTripRequest.getCarType();
    float tripPrice = scheduleTripRequest.getTripPrice();
    ScheduleTripResponse scheduleTripResponse = new ScheduleTripResponse();

    ScheduleTripTransactionInput scheduleTripTransactionInput = new ScheduleTripTransactionInput(carType,tripPrice,sourceLocation,destinationLocation);

    ScheduleTripTransactionResult scheduleTripTransactionResult = writeDataHandler.scheduleTrip(scheduleTripTransactionInput);

    if (scheduleTripTransactionResult.getTrip() != null) {
      assert (scheduleTripTransactionResult.getScheduleTripTransactionStatus()
          .equals(ScheduleTripTransactionStatus.SUCCESSFULLY_BOOKED));
      assert (scheduleTripTransactionResult.getTrip().getTripStatus()
          .equals(TripStatus.TRIP_STATUS_SCHEDULED));

      scheduleTripResponse.setScheduleTripStatus(ScheduleTripTransactionStatus.SUCCESSFULLY_BOOKED);
      scheduleTripResponse.setTrip(scheduleTripTransactionResult.getTrip());
    }
    else {
      assert (scheduleTripTransactionResult.getScheduleTripTransactionStatus().equals(ScheduleTripTransactionStatus.NO_CARS_AVAILABLE));
      scheduleTripResponse.setScheduleTripStatus(ScheduleTripTransactionStatus.NO_CARS_AVAILABLE);
    }

    return scheduleTripResponse;
  }

  public void setWriteDataHandler(WriteDataHandler writeDataHandler) {
    this.writeDataHandler = writeDataHandler;
  }
}
