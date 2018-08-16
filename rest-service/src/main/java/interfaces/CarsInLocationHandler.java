package interfaces;

import carsinlocation.CarsInLocationRequest;
import carsinlocation.CarsInLocationResponse;

// TIP: (DESIGN) - Such an interface provides a clear separation between the higher level
// communication module (REST API, gRPC etc) from the underlying business logic implementation.
// eg: In future, if we add gRPC as an additional communication mechanism,
// the interface implementation class CarsInLocationHandlerImpl would require no changes
// as long as CarsInLocationRequest is populated correctly.
public interface CarsInLocationHandler {

  // TIP: (DESIGN) - If CarsInLocationRequest contains only GeoLocation for now,
  // why didn't we declare the function the following way:
  // CarsInLocationResponse getCarsInLocation(GeoLocation geoLocation);
  // Imagine what would happen if we have to pass more inputs to getCarsInLocation() in future.
  // What effect would it have on clients using this interface?

  //  Input:
  //    CarsInLocationRequest - User's current location.
  //                          - Is expected to be a valid value.
  //  Output: CarsInLocationResponse contains:
  //    1. Array of car statuses if there are cars available/close to trip completion near the user
  //       location.
  //    2. Empty array, if there are no available cars.
  CarsInLocationResponse getCarsInLocation(CarsInLocationRequest carsInLocationRequest);
}
