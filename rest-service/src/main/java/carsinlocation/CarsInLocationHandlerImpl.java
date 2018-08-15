package carsinlocation;

import car.CarStatus;
import interfaces.CarsInLocationHandler;
import interfaces.ReadDataHandler;
import java.util.List;
import read.ReadDataHandlerImpl;

public class CarsInLocationHandlerImpl implements CarsInLocationHandler {
  // Used to read data from storage/cache.
  private ReadDataHandler readDataHandler;

  public CarsInLocationHandlerImpl() {
    readDataHandler = new ReadDataHandlerImpl();
  }

  @Override
  public CarsInLocationResponse getCarsInLocation(CarsInLocationRequest cars_in_location_request) {
    /*
     * Input:
     *   CarsInLocationRequest - User's current location.
     *                         - Is expected to be a valid value.
     * Output: CarsInLocationResponse contains:
     *   1. Array of car statuses if there are cars available/close to trip completion near the
     *      user location.
     *   2. Empty array, if there are no available cars.
     */

    // TIP: Precondition check is a powerful tool in maintaining sane code.
    // Apart from communicating to other developers about the assumptions you have made in the
    // function, it also catches defects right when they occur.
    // Useful Link #1: http://www.oracle.com/us/technologies/java/assertions-139853.html
    // Useful Link #2: http://web.mit.edu/6.005/www/fa15/classes/08-avoiding-debugging/
    // You might also want to check if valid lat/long are passed in.
    assert (cars_in_location_request != null);

    List<CarStatus> carStatusList =
        readDataHandler.getCarsInLocation(cars_in_location_request.getGeoLocation());

    CarsInLocationResponse carsInLocationResponse = new CarsInLocationResponse();
    carsInLocationResponse.setCarStatuses(carStatusList);

    return carsInLocationResponse;
  }

  public void setReadDataHandler(ReadDataHandler readDataHandler) {
    this.readDataHandler = readDataHandler;
  }
}
