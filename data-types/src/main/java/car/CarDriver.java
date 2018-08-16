package car;

import javax.validation.constraints.NotNull;

// Class can be used to create the following JSON format.
// {
//    "driverId": "DRIVER_ID_1",
//    "name": "MANJUNATH",
//    "phone": "9987899891"
// }
// driverId, name & phone are plain strings.
public class CarDriver {
  @NotNull private String driverId;
  private String name;
  private String phone;

  public String getDriverId() {
    return driverId;
  }

  public void setDriverId(String driverId) {
    this.driverId = driverId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
