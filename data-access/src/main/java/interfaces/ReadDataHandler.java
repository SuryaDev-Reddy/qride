package interfaces;

import car.Car;
import car.CarStatus;
import java.util.List;
import location.GeoLocation;

// Interface that is used for all data read accesses.
// Helps separate data layer from API handlers.
public interface ReadDataHandler {
  //  Input:
  //    geoLocation - A valid geolocation.
  //  Output:
  //    1. Array of car statuses if there are cars available/close to trip completion near the
  //       geolocation.
  //    2. Empty array, if there are no available cars.
  List<CarStatus> getCarsInLocation(GeoLocation geoLocation);

  // Returns available cars of a specific type near the location ordered by their proximity to
  // the location.
  // Input:
  //   geoLocation - A valid geolocation.
  //   carType - Valid car type.
  // Output:
  //   1. Array of car statuses if there are cars available of the specific type near
  //      the geolocation.
  //      The cars must be ordered by their proximity to the given location; ie. cars closer by
  //      are first in the list.
  //   2. Empty array, if there are no available cars of this type or in case of invalid input.
  List<CarStatus> getCarsInLocationOfCarType(GeoLocation geoLocation, Car.CarType carType);
}
