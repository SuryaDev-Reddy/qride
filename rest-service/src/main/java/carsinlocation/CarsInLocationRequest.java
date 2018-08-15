package carsinlocation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import location.GeoLocation;

/*
 * This class helps deserialize the following JSON request.
 * {
 *   "geoLocation": {
 *     "latitude": 12.908486,
 *     "longitude": 77.536386
 *  }
 * }
 */
public class CarsInLocationRequest {
  @Valid @NotNull private GeoLocation geoLocation;

  public GeoLocation getGeoLocation() {
    return geoLocation;
  }

  public void setGeoLocation(GeoLocation geoLocation) {
    this.geoLocation = geoLocation;
  }
}
