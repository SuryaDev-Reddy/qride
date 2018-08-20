package read;

import car.Car.CarType;
import car.CarStatus;
import car.CarStatus.CarAvailability;
import globals.GlobalConstants;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import location.GeoLocation;
import location.GeoUtils;
import tables.CarStatusTable;

// This class contains methods to get available cars from datastore which can be either cache or db.
public class GetCarsInLocationFromDataStore {
  private EntityManager em;

  GetCarsInLocationFromDataStore() {
    em = GlobalConstants.getEntityManagerFactory().createEntityManager();
  }

  // Splitting the function into gerCarsInLocation & getCarsInLocationDB like this helps abstract
  // whether we are returning from cache or from db eventually.
  public List<CarStatus> getCarsInLocation(GeoLocation geoLocation) {
    // Connect to SQL server and read the table.
    return getCarsInLocationFromDb(geoLocation);
  }

  public List<CarStatus> getCarsInLocationOfCarType(GeoLocation geoLocation, CarType carType) {
    return getCarsInLocationOfCarTypeFromDB(geoLocation,carType);
  }

  // Input:
  // geoLocation - A specific lat/long around which this function returns available cars.
  // Output:
  // List<CarStatus> - 1. Return list of cars that are (CarStatus.CarAvailability.CAR_AVAILABLE or
  //                      CarStatus.CarAvailability.CAR_ON_TRIP_CLOSE_TO_COMPLETION) &
  //                      within GlobalConstants.SEARCH_RADIUS (inclusive) from CarStatusTable.
  //                   2. Empty list if no car found.
  // Use the Haversine formula shown in GlobalConstants.HAVER_SINE_FORMULA to calculate the distance
  // between two lat/longs. There are definitely more accurate ways to calculate distance,
  // but make sure your submission code uses this formula.
  private List<CarStatus> getCarsInLocationFromDb(GeoLocation geoLocation) {
    List<CarStatusTable> carStatusTableList;
    List<CarStatus> status = null;

    try {
      Query nativeQuery = em.createNativeQuery("SELECT * FROM CarStatusTable WHERE ("
          + GlobalConstants.HAVER_SINE_FORMULA + "<=" + GlobalConstants.SEARCH_RADIUS
          + ") AND ( carAvailability = " + CarAvailability.CAR_AVAILABLE + " )" + "OR ( carAvailability = "
          + CarAvailability.CAR_ON_TRIP_CLOSE_TO_COMPLETION + ")", CarStatusTable.class);

      nativeQuery.setParameter("latPoint", geoLocation.getLatitude());
      nativeQuery.setParameter("longPoint", geoLocation.getLongitude());

      carStatusTableList = nativeQuery.getResultList();
      status = new ArrayList<>();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      return status;
    } finally {
      em.close();
    }

    for (CarStatusTable carStatusTable : carStatusTableList) {
      status.add(FormatConverters.convertCarStatusTableIntoCarStatus(carStatusTable));
    }

    return status;
  }

  private List<CarStatus> getCarsInLocationOfCarTypeFromDB(GeoLocation geoLocation, CarType carType) {
    List<CarStatusTable> carStatusTableList;
    List<CarStatus> status = new ArrayList<>();

    try {
      Query nativeQuery = em.createNativeQuery("SELECT * FROM CarStatusTable WHERE ("
              + GlobalConstants.HAVER_SINE_FORMULA + "<=" + GlobalConstants.SEARCH_RADIUS
              + ") AND ( carAvailability = " + CarAvailability.CAR_AVAILABLE.ordinal() + " )" + "OR ( carAvailability = "
              + CarAvailability.CAR_ON_TRIP_CLOSE_TO_COMPLETION.ordinal() + ")" + "AND (carType = :carType )"
              + "ORDER BY " + GlobalConstants.HAVER_SINE_FORMULA,
          CarStatusTable.class);

      nativeQuery.setParameter("latPoint", geoLocation.getLatitude());
      nativeQuery.setParameter("longPoint", geoLocation.getLongitude());
      nativeQuery.setParameter("carType", carType);

      carStatusTableList = nativeQuery.getResultList();

    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      return status;
    } finally {
      em.close();
    }

    for (CarStatusTable carStatusTable : carStatusTableList) {
      status.add(FormatConverters.convertCarStatusTableIntoCarStatus(carStatusTable));
    }
    return status;
  }

}
