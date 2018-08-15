package qrideserver;

import carsinlocation.CarsInLocationHandlerImpl;
import carsinlocation.CarsInLocationRequest;
import carsinlocation.CarsInLocationResponse;
import interfaces.CarsInLocationHandler;
import javax.validation.Valid;
import location.GeoLocation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * CarsInLocation REST API.
 * URL: /qride/v1/cars_in_location
 * Description: Given a geolocation, return list of cars available/close to trip completion
 * in that location.
 * Method: POST
 * Data Params: User's current location.
 * {
 *  "geoLocation": {
 *    "latitude": 12.908486,
 *    "longitude": 77.536386
 *  }
 * }
 *
 * Success Output:
 * HTTP Code: 200
 * Content:
 * 1. Array of car statuses if there are cars available/close to trip completion near
 *    the user location.
 * 2. Empty array, if there are no available cars.
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
 *             "carAvailability": "CAR_ON_TRIP_CLOSE_TO_COMPLETION"
 *         }
 *     }]
 * }
 *
 * Error Response:
 * HTTP Code: 4xx, if client side error.
 *          : 5xx, if server side error.
 */

@RestController
public class CarsInLocationController {
  private CarsInLocationHandler carsInLocationHandler;

  CarsInLocationController() {
    carsInLocationHandler = new CarsInLocationHandlerImpl();
  }

  @RequestMapping(value = "/qride/v1/cars_in_location")
  public ResponseEntity<CarsInLocationResponse> carsInLocation(
      @Valid
          @RequestBody
          final CarsInLocationRequest carsInLocationRequest) {
    GeoLocation geoLocation = carsInLocationRequest.getGeoLocation();
    assert (geoLocation != null);
    if (!geoLocation.isValidGeoLocation()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    return ResponseEntity.status(HttpStatus.OK)
        .body(carsInLocationHandler.getCarsInLocation(carsInLocationRequest));
  }

  public void setCarsInLocationHandler(CarsInLocationHandler carsInLocationHandler) {
    this.carsInLocationHandler = carsInLocationHandler;
  }
}
