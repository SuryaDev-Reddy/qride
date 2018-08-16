package scheduletrip;

import car.Car;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import location.GeoLocation;

// Class can be used to deserialize the following JSON request.
// {
//  "carType": "CAR_TYPE_HATCHBACK",
//  "tripPrice": 129.00,
//  "sourceLocation": {
//    "latitude": 12.908486,
//    "longitude": 77.536386
//  },
//  "destinationLocation": {
//    "latitude": 12.908486,
//    "longitude": 77.536386
//  }
// }
public class ScheduleTripRequest {
  @NotNull private Car.CarType carType;
  @NotNull private Float tripPrice;
  @Valid private GeoLocation sourceLocation;
  @Valid private GeoLocation destinationLocation;

  public Car.CarType getCarType() {
    return carType;
  }

  public void setCarType(Car.CarType carType) {
    this.carType = carType;
  }

  public Float getTripPrice() {
    return tripPrice;
  }

  public void setTripPrice(Float tripPrice) {
    this.tripPrice = tripPrice;
  }

  public GeoLocation getSourceLocation() {
    return sourceLocation;
  }

  public void setSourceLocation(GeoLocation sourceLocation) {
    this.sourceLocation = sourceLocation;
  }

  public GeoLocation getDestinationLocation() {
    return destinationLocation;
  }

  public void setDestinationLocation(GeoLocation destinationLocation) {
    this.destinationLocation = destinationLocation;
  }
}
