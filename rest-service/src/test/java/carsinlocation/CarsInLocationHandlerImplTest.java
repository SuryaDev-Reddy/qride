package carsinlocation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import car.CarStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import interfaces.ReadDataHandler;

import java.util.ArrayList;
import java.util.List;

import location.GeoLocation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import utils.TestUtils;



// Scope: Tests CarsInLocationHandlerImpl.getCarsInLocation() function.

public class CarsInLocationHandlerImplTest {

  private final CarsInLocationHandlerImpl carsInLocationHandlerImpl =

      new CarsInLocationHandlerImpl();



  // Utility function to generate a bunch of cars based on the geoLocation passed to help testing.

  private List<CarStatus> generateCarStatus(GeoLocation geoLocation) {

    List<CarStatus> carStatusList = new ArrayList<>();

    for (int i = 0; i < 5; i++) {

      CarStatus carStatus = new CarStatus();



      carStatus.setGeoLocation(

          TestUtils.createGeoLocationObject(

              geoLocation.getLatitude() + i, geoLocation.getLongitude() + i));

      carStatus.setCarAvailability(CarStatus.CarAvailability.CAR_AVAILABLE);

      carStatusList.add(carStatus);

    }

    return carStatusList;

  }



  @Before

  public void setUp() throws Exception {}



  @After

   public void tearDown() throws Exception {}



  // TODO: CRIO_TEST - Make code changes to ensure

  // CarsInLocationHandlerImpl.getCarsInLocation() works as intended.

  @Test

  public void getCarsInLocationRegularCase() throws Exception {

    // Create a mock readDataHandler that would return back a bunch of available cars using

    // generateCarStatus() utility function.

    ReadDataHandler readDataHandlerMock = Mockito.mock(ReadDataHandler.class);

    GeoLocation geoLocation = TestUtils.createGeoLocationObject(50.0, 100.0);

    when(readDataHandlerMock.getCarsInLocation(geoLocation))
        .thenReturn(generateCarStatus(geoLocation));

    carsInLocationHandlerImpl.setReadDataHandler(readDataHandlerMock);

    System.out.println(

        "CarsInLocationHandlerImplTest:getCarsInLocationRegularCase "

            + "You need to make changes to the following code to test your class");



    CarsInLocationRequest carsInLocationRequest = new CarsInLocationRequest();

    carsInLocationRequest.setGeoLocation(geoLocation);

    CarsInLocationResponse carsInLocationResponse =

        carsInLocationHandlerImpl.getCarsInLocation(carsInLocationRequest);

    List<CarStatus> carsStatus = generateCarStatus(geoLocation);

    for (int i = 0;i < carsInLocationResponse.getCarStatuses().size();i++) {
      assertEquals(carsStatus.get(i).toString(),
          carsInLocationResponse.getCarStatuses().get(i).toString());
    }


    // Compare CarsInLocationResponse based on what we would expect based on logic in

    // generateCarStatus().

    String responseJsonString = "";

    try {

      responseJsonString = new ObjectMapper().writeValueAsString(carsInLocationResponse);

    } catch (JsonProcessingException e) {

      e.printStackTrace();

    }

    System.out.println(responseJsonString);

  }



  // TODO: CRIO_TEST - Test if CarsInLocationHandlerImpl.getCarsInLocation() function

  // can handle when readDataHandler.getCarsInLocation() returns null due to a bug.

  @Test

  public void getCarsInLocationEmptyResponse() throws Exception {

    ReadDataHandler readDataHandler = Mockito.mock(ReadDataHandler.class);

    GeoLocation geoLocation = TestUtils.createGeoLocationObject(50.0, 100.0);

    when(readDataHandler.getCarsInLocation(geoLocation)).thenReturn(null);

    carsInLocationHandlerImpl.setReadDataHandler(readDataHandler);

    System.out.println(

        "CarsInLocationHandlerImplTest:getCarsInLocationEmptyResponse "

            + "You need to make changes to the following code to test your class");



    CarsInLocationRequest carsInLocationRequest = new CarsInLocationRequest();

    carsInLocationRequest.setGeoLocation(geoLocation);

    CarsInLocationResponse carsInLocationResponse =

        carsInLocationHandlerImpl.getCarsInLocation(carsInLocationRequest);



    assertNotNull(

        "CarsInLocationResponse must not be empty", carsInLocationResponse.getCarStatuses());

    assertEquals(0, carsInLocationResponse.getCarStatuses().size());

  }

}

