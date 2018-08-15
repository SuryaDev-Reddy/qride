package carsinlocation;

import car.CarStatus;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.List;

/*
 * This class produces the following json when serialized using Jackson.
 * Output: CarsInLocationResponse - Array of carStatus
 * {
 *     "carStatuses": [{
 *         "carStatus": {
 *             "geoLocation": {
 *                 "latitude": 0.0036492730023700215,
 *                 "longitude": 0.04278965249297461
 *             },
 *             "carAvailability": "CAR_AVAILABLE"
 *         }
 *     }, {
 *         "carStatus": {
 *             "geoLocation": {
 *                 "latitude": 0.13222748716934793,
 *                 "longitude": 0.033663367718575685
 *             },
 *             "carAvailability": "CAR_AVAILABLE"
 *         }
 *     }]
 * }
 */
public class CarsInLocationResponse {
  @JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
  private List<CarStatus> carStatuses;

  public List<CarStatus> getCarStatuses() {
    return carStatuses;
  }

  public void setCarStatuses(List<CarStatus> carStatuses) {
    this.carStatuses = carStatuses;
  }
}
