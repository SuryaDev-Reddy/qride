package car;

import javax.validation.constraints.NotNull;

// Class such that it can be used to create the following JSON format.
//  {
//    "carId": "CAR_ID_1",
//    "carType": "CAR_TYPE_HATCHBACK",
//    "carModel": "TOYOTA ETIOS",
//    "carLicense": "KA03 3122"
//  }
// carId, carModel & carLicense are plain strings.
public class Car {
  @NotNull private String carId;

  @NotNull private CarType carType;
  private String carModel;
  private String carLicense;

  public String getCarId() {
    return carId;
  }

  public void setCarId(String carId) {
    this.carId = carId;
  }

  public CarType getCarType() {
    return carType;
  }

  public void setCarType(CarType carType) {
    this.carType = carType;
  }

  public String getCarModel() {
    return carModel;
  }

  public void setCarModel(String carModel) {
    this.carModel = carModel;
  }

  public String getCarLicense() {
    return carLicense;
  }

  public void setCarLicense(String carLicense) {
    this.carLicense = carLicense;
  }

  public enum CarType {
    UNKNOWN,
    CAR_TYPE_HATCHBACK,
    CAR_TYPE_SEDAN,
    CAR_TYPE_MINIVAN
  }
}
