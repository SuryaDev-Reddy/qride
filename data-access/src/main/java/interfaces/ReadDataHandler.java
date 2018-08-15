package interfaces;

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
}
