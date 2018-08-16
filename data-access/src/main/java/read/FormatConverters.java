package read;

import car.CarStatus;
import tables.CarStatusTable;
import tables.TripTable;
import trip.Trip;

// A bunch of utility functions that convert from storage to serving layer objects and vice-versa.
// TIP: (DESIGN) It is very useful to separate storage layer objects from serving layer
// objects.
// It is based on the principle of Separation of Concerns:
// - You can change the storage schema objects without affecting the serving layer.
// - You can introduce more complex object types in the serving layer without having to do large
//   storage migrations.
// Search for "data layer service layer isolation" in Google & learn more!
public class FormatConverters {

  // Utility function to convert an entry from CarStatusTable into CarStatus.
  // Helps keep your code modularized, but it is not mandatory to use this function.
  public static CarStatus convertCarStatusTableIntoCarStatus(CarStatusTable carStatusTable) {
    CarStatus carStatus = new CarStatus();
    carStatus.setGeoLocation(carStatusTable.getGeoLocation());
    carStatus.setCarAvailability(carStatusTable.getCarAvailability());
    carStatus.setCarId(carStatusTable.getCarId());
    return carStatus;
  }

  // Utility function to convert an entry from TripTable into Trip.
  public static Trip convertTripTableEntryIntoTrip(TripTable tripTableEntry) {
    assert (tripTableEntry != null);
    return null;
  }
}
