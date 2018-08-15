package car;

import com.fasterxml.jackson.annotation.JsonTypeName;
import location.GeoLocation;


// This class when serialized produces the following JSON format.
// {
//   "geoLocation": {
//     "latitude": 32.37718599937095,
//     "longitude": 82.71417729525433
//   },
//   "carAvailability": "CAR_AVAILABLE"
// }
@JsonTypeName(value = "carStatus")
public class CarStatus {
  private GeoLocation geoLocation;
  private CarAvailability carAvailability;

  public GeoLocation getGeoLocation() {
    return geoLocation;
  }

  public void setGeoLocation(GeoLocation geoLocation) {
    this.geoLocation = geoLocation;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    if (getGeoLocation() != null) {
      stringBuilder.append(getGeoLocation()).append(" ");
    }
    if (getCarAvailability() != null) {
      stringBuilder.append(getCarAvailability());
    }
    return stringBuilder.toString();
  }

  public CarAvailability getCarAvailability() {
    return carAvailability;
  }

  public void setCarAvailability(CarAvailability carAvailability) {
    this.carAvailability = carAvailability;
  }

  public enum CarAvailability {
    CAR_AVAILABLE,
    CAR_ON_TRIP_CLOSE_TO_COMPLETION,
    CAR_ON_TRIP,
    CAR_OFF_DUTY,
  }
}
