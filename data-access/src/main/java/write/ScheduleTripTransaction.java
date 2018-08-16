package write;

import tables.TripTable;
import trip.ScheduleTripTransactionInput;
import trip.ScheduleTripTransactionResult;

// This class contains methods to schedule a trip.
public class ScheduleTripTransaction {
  // TODO: CRIO_TASK: Implement scheduleTrip() function.
  // Does everything to schedule a trip:
  //   - Finds out if there is a car available of given carType in the nearby radius.
  //   - If available, creates a Trip entry, populates it and stores it in the appropriate table.
  //   - Also changes the status of the specific car.
  // Input:
  //   scheduleTripTransactionInput - Contains carType, tripPrice, start/end locations.
  // Output:
  //   1. If we are able to find a car available of this particular car type within
  //      the search radius, schedule the closest proximity car & return
  //      ScheduleTripTransactionResult populated with:
  //      - scheduleTripTransactionStatus = SUCCESSFULLY_BOOKED.
  //      - trip details with updated tripId (any random id works), carId, driverId,
  //        startTimeInEpochs, source/destination locations, trip price,
  //        & tripStatus = TRIP_STATUS_SCHEDULED.
  //   2. If no cars are available, return ScheduleTripTransactionResult populated with:
  //      - scheduleTripTransactionStatus = NO_CARS_AVAILABLE without any trip information.
  //   3. In case of any other errors, return ScheduleTripTransactionResult populated with
  //      appropriate scheduleTripTransactionStatus without any trip information.
  //   If a car was selected, make sure to update the Trip info & status of the selected car
  //   to CAR_ON_TRIP in appropriate tables.
  public ScheduleTripTransactionResult scheduleTrip(
      ScheduleTripTransactionInput scheduleTripTransactionInput) {
     return null;
  }

  // TODO: CRIO_TASK: Utility function to create a new TripTable entry from
  // scheduleTripTransactionInput.
  // Helps keep your code modularized, but it is not mandatory to use this function.
  private TripTable createNewTripTableEntry(
      ScheduleTripTransactionInput scheduleTripTransactionInput) {
     return null;
  }
}
