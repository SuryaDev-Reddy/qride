package qrideserver;

import fareestimates.FareEstimatesRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// API URI: "/qride/v1/fare_estimates"
// Description: Given source & destination locations, return the fare estimates
// for various car types.
// Method: POST
// Data Params: Source location & destination location.
// {
//  "sourceLocation": {
//    "latitude": 12.908486,
//    "longitude": 77.536386
//  },
//  "destinationLocation": {
//    "latitude": 13.808486,
//    "longitude": 77.561386
//  }
// }
//
// Success Output: Array of EstimateForCarType
// HTTP Code: 200
// Content:
// {
//  "estimatesForCarTypes": [{
//    "estimateForCarType": {
//      "carType": "CAR_TYPE_HATCHBACK",
//      "tripPrice": 129.00
//    }
//  },
//    {
//      "estimateForCarType": {
//        "carType": "CAR_TYPE_SEDAN",
//        "tripPrice": 140.00
//      }
//    }, {
//      "estimateForCarType": {
//        "carType": "CAR_TYPE_MINIVAN",
//        "tripPrice": 181.00
//      }
//    }
//  ]
// }
//
// Error Response:
// HTTP Code: 4xx, if client side error.
//          : 5xx, if server side error.
@RestController
public class FareEstimatesController {
  @RequestMapping(value = "/qride/v1/fare_estimates")
  public ResponseEntity<String> fareEstimates(
      @RequestBody final FareEstimatesRequest fareEstimatesRequest) {
    // To use ScheduleRide from Android app, some dummy implementation is needed for FareEstimates.
    return ResponseEntity.status(HttpStatus.OK)
        .body("{\n"
            + "  \"estimatesForCarTypes\": [\n"
            + "    {\n"
            + "      \"estimateForCarType\": {\n"
            + "        \"carType\": \"CAR_TYPE_HATCHBACK\",\n"
            + "        \"tripPrice\": 129.00\n"
            + "      }\n"
            + "    },\n"
            + "    {\n"
            + "      \"estimateForCarType\": {\n"
            + "        \"carType\": \"CAR_TYPE_SEDAN\",\n"
            + "        \"tripPrice\": 140.00\n"
            + "      }\n"
            + "    },\n"
            + "    {\n"
            + "      \"estimateForCarType\": {\n"
            + "        \"carType\": \"CAR_TYPE_MINIVAN\",\n"
            + "        \"tripPrice\": 181.00\n"
            + "      }\n"
            + "    }\n"
            + "  ]\n"
            + "}\n");
  }
}
