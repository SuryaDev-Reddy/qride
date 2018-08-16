package scheduletrip;

import javax.validation.Valid;
import trip.ScheduleTripTransactionResult;
import trip.Trip;

// Class can be used to generate the following JSON response.
// Reusing ScheduleTripTransactionResult.ScheduleTripTransactionStatus for scheduleTripStatus field.
// {
//  "scheduleTripStatus": "SUCCESSFULLY_BOOKED",
//  "trip": {
//    "tripId": "TRIP_ID_1",
//    "carId": "CAR_ID_1",
//    "driverId": "DRIVER_ID_1",
//    "sourceLocation": {
//      "latitude": 12.908486,
//      "longitude": 77.536386
//    },
//    "destinationLocation": {
//      "latitude": 12.908486,
//      "longitude": 77.536386
//    },
//    "startTimeInEpochs": 1512152782,
//    "tripPrice": 129.00,
//    "tripStatus": "TRIP_STATUS_SCHEDULED"
//  }
// }
public class ScheduleTripResponse {
  ScheduleTripTransactionResult.ScheduleTripTransactionStatus scheduleTripStatus;
  @Valid Trip trip;

  public ScheduleTripTransactionResult.ScheduleTripTransactionStatus getScheduleTripStatus() {
    return scheduleTripStatus;
  }

  public void setScheduleTripStatus(
      ScheduleTripTransactionResult.ScheduleTripTransactionStatus scheduleTripStatus) {
    this.scheduleTripStatus = scheduleTripStatus;
  }

  public Trip getTrip() {
    return trip;
  }

  public void setTrip(Trip trip) {
    this.trip = trip;
  }
}
