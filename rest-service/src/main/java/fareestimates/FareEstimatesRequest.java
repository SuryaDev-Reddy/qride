package fareestimates;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import location.GeoLocation;

// Class can be used to deserialize the following JSON request.
// {
//  "sourceLocation": {
//    "latitude": 12.908486,
//    "longitude": 77.536386
//  },
//  "destinationLocation": {
//    "latitude": 12.908486,
//    "longitude": 77.536386
//  }
// }
// Though we get the input for FareEstimates REST API, the code doesn't look into
// FareEstimatesRequest and instead temporarily sends dummy values back in the controller layer.
public class FareEstimatesRequest {
  @Valid @NotNull private GeoLocation sourceLocation;

  @Valid @NotNull private GeoLocation destinationLocation;

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
