package read;

import car.Car;
import car.CarStatus;
import interfaces.ReadDataHandler;
import java.util.List;
import location.GeoLocation;

// Class contains the implementation of ReadDataHandlers reading data
// from Datastore (SQL/Cache).
public class ReadDataHandlerImpl implements ReadDataHandler {

  @Override
  public List<CarStatus> getCarsInLocation(GeoLocation geoLocation) {
    assert (geoLocation != null);
    assert (geoLocation.getLongitude() != null);
    assert (geoLocation.getLatitude() != null);

    GetCarsInLocationFromDataStore getCarsInLocationFromDataStore =
        new GetCarsInLocationFromDataStore();
    return getCarsInLocationFromDataStore.getCarsInLocation(geoLocation);
  }

  @Override
  public List<CarStatus> getCarsInLocationOfCarType(GeoLocation geoLocation, Car.CarType carType) {
    GetCarsInLocationFromDataStore getCarsInLocationFromDataStore =
        new GetCarsInLocationFromDataStore();
    return getCarsInLocationFromDataStore.getCarsInLocationOfCarType(geoLocation, carType);
  }
}
