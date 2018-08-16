package qrideserver;

import interfaces.ScheduleTripHandler;
import org.springframework.web.bind.annotation.RestController;
import scheduletrip.ScheduleTripHandlerImpl;

// API URI: "/qride/v1/schedule_trip"
// Description: Schedule a trip when car type, the estimated price, and
// the source/destination locations are given.
// Method: POST
// Data Params: carType, tripPrice, start/end locations
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
// Success Output:
// 1. If we are able to find a car available of this particular car type within
//    the search radius, schedule it & return:
//      - scheduleTripStatus = SUCCESSFULLY_BOOKED.
//      - trip details with updated startTimeInEpochs
//        & tripStatus = TRIP_STATUS_SCHEDULED.
// 2. If no cars are available, return:
//      - scheduleTripStatus = NO_CARS_AVAILABLE without any trip information.
// HTTP Code: 200
// Content:
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
//      "latitude": 13.808486,
//      "longitude": 77.038396
//    },
//    "startTimeInEpochs": 1512152782,
//    "tripPrice": 129.00,
//    "tripStatus": "TRIP_STATUS_SCHEDULED"
//  }
// }
//
// Error Response:
// HTTP Code: 4xx, if client side error.
//          : 5xx, if server side error.

@RestController
public class ScheduleTripController {
  private ScheduleTripHandler scheduleTripHandler;

  public ScheduleTripController() {
    this.scheduleTripHandler = new ScheduleTripHandlerImpl();
  }

  public void setScheduleTripHandler(ScheduleTripHandler scheduleTripHandler) {
    this.scheduleTripHandler = scheduleTripHandler;
  }

  // TODO: CRIO_TASK: Make sure you write the appropriate RequestMapping here.
}
