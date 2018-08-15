package location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/*
 * This class helps deserialize the following JSON.
 * {
 *   "latitude": 12.908486,
 *   "longitude": 77.536386
 * }
 */
@Embeddable
public class GeoLocation implements Serializable {
  @NotNull private Double latitude;
  @NotNull private Double longitude;

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    if (getLatitude() != null) {
      stringBuilder.append(getLatitude()).append(" ");
    }
    if (getLongitude() != null) {
      stringBuilder.append(getLongitude());
    }
    return stringBuilder.toString();
  }

  @JsonIgnore
  public boolean isValidGeoLocation() {
    if (getLatitude() != null
        && getLatitude() >= -90.0D
        && getLatitude() <= 90.0D
        && getLongitude() != null
        && getLongitude() >= -180.0D
        && getLongitude() <= 180.0D) {
      return true;
    }
    return false;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }
}
