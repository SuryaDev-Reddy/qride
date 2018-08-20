package trip;

import com.fasterxml.jackson.annotation.JsonInclude;
import location.GeoLocation;

// Class can be used to create the following JSON format.
// {
//    "tripId": "TRIP_ID_1",
//    "carId": "CAR_ID_1",
//    "driverId": "DRIVER_ID_1",
//    "sourceLocation": {
//      "latitude": 12.908486,
//      "longitude": 77.536386
//    },
//    "destinationLocation": {
//      "latitude": 12.908486,
//      "longitude": 77.536386
//    },
//    "startTimeInEpochs": 1512152782,
//    "endTimeInEpochs": 1512170782,
//    "tripPrice": 129.00,
//    "tripStatus": "TRIP_STATUS_COMPLETED",
//    "paymentMode": "PAYTM_PAYMENT"
// }
// tripId, carId are strings; startTimeInEpochs, endTimeInEpochs are time epochs;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Trip {
  private String tripId;
  private String carId;
  private String driverId;
  private GeoLocation sourceLocation;
  private GeoLocation destinationLocation;
  private Long startTimeInEpochs;
  private Long endTimeInEpochs;
  private Float tripPrice;
  private TripStatus tripStatus;
  private PaymentMode paymentMode;

  public String getTripId() {
    return tripId;
  }

  public void setTripId(String tripId) {
    this.tripId = tripId;
  }

  public String getCarId() {
    return carId;
  }

  public void setCarId(String carId) {
    this.carId = carId;
  }

  public String getDriverId() {
    return driverId;
  }

  public void setDriverId(String driverId) {
    this.driverId = driverId;
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

  public Long getStartTimeInEpochs() {
    return startTimeInEpochs;
  }

  public void setStartTimeInEpochs(Long startTimeInEpochs) {
    this.startTimeInEpochs = startTimeInEpochs;
  }

  public Long getEndTimeInEpochs() {
    return endTimeInEpochs;
  }

  public void setEndTimeInEpochs(Long endTimeInEpochs) {
    this.endTimeInEpochs = endTimeInEpochs;
  }

  public Float getTripPrice() {
    return tripPrice;
  }

  public void setTripPrice(Float tripPrice) {
    this.tripPrice = tripPrice;
  }

  public TripStatus getTripStatus() {
    return tripStatus;
  }

  public void setTripStatus(TripStatus tripStatus) {
    this.tripStatus = tripStatus;
  }

  public PaymentMode getPaymentMode() {
    return paymentMode;
  }

  public void setPaymentMode(PaymentMode paymentMode) {
    this.paymentMode = paymentMode;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    if (getTripId() != null) {
      stringBuilder.append("TripId:" + getTripId()).append(" ");
    }
    if (getCarId() != null) {
      stringBuilder.append("CarId:" + getCarId()).append(" ");
    }
    if (getDriverId() != null) {
      stringBuilder.append("DriverId:" + getDriverId()).append(" ");
    }
    if (getSourceLocation() != null) {
      stringBuilder.append("SourceLocation:" + getSourceLocation()).append(" ");
    }
    if (getDestinationLocation() != null) {
      stringBuilder.append("DestinationLocation:" + getDestinationLocation()).append(" ");
    }
    if (getCarId() != null) {
      stringBuilder.append("CarId:" + getCarId()).append(" ");
    }
    if (getStartTimeInEpochs() != null) {
      stringBuilder.append("StartTimeInEpochs:" + getStartTimeInEpochs()).append(" ");
    }
    if (getEndTimeInEpochs() != null) {
      stringBuilder.append("EndTimeInEpochs:" + getEndTimeInEpochs()).append(" ");
    }
    if (getTripPrice() != null) {
      stringBuilder.append("TripPrice:" + getTripPrice()).append(" ");
    }
    if (getTripStatus() != null) {
      stringBuilder.append("TripStatus:" + getTripStatus()).append(" ");
    }
    if (getPaymentMode() != null) {
      stringBuilder.append("CarId:" + getPaymentMode()).append(" ");
    }
    return stringBuilder.toString();
  }

  public enum PaymentMode {
    UNKNOWN,
    CASH_PAYMENT,
    CARD_PAYMENT,
    PAYTM_PAYMENT
  }

  public enum TripStatus {
    UNKNOWN,
    TRIP_STATUS_SCHEDULED,
    TRIP_STATUS_ONGOING,
    TRIP_STATUS_COMPLETED,
    TRIP_STATUS_CANCELLED
  }
}
