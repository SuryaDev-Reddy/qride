package tables.setup;

import car.Car;
import car.Car.CarType;
import car.CarStatus;
import car.CarStatus.CarAvailability;
import globals.GlobalConstants;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import location.GeoLocation;
import tables.CarStatusTable;

// Class that populates various tables with some dummy values for dev purposes.
// In reality, the databases will be either created organically or directly populated
// using SQL scripts in the case of data migrations.
public class TableSetup {
  private static final int numberOfCarsInACity = 1000;
  private static final int numberOfCities = 6;

  private final EntityManagerFactory emf;

  public TableSetup() {
    emf = GlobalConstants.getEntityManagerFactory();
  }

  public void init() {
    initCarStatusTable();
  }

  // In reality, the table may not be necessarily organized this way and it could very well be
  // restricted to cities. But this helps understand certain SQL bottlenecks better.
  private void initCarStatusTable() {
    Double[] bangaloreLatitudeBoundaries = {12.818943, 13.112025};
    Double[] bangaloreLongitudeBoundaries = {77.431182, 77.767639};
    positionCarsWithinBoundary(
        0 * numberOfCarsInACity, bangaloreLatitudeBoundaries, bangaloreLongitudeBoundaries);

    Double[] chennaiLatitudeBoundaries = {12.847508, 13.596159};
    Double[] chennaiLongitudeBoundaries = {77.481075, 77.700260};
    positionCarsWithinBoundary(
        1 * numberOfCarsInACity, chennaiLatitudeBoundaries, chennaiLongitudeBoundaries);

    Double[] hyderabadLatitudeBoundaries = {17.175781, 17.587303};
    Double[] hyderabadLongitudeBoundaries = {78.260192, 78.696899};
    positionCarsWithinBoundary(
        2 * numberOfCarsInACity, hyderabadLatitudeBoundaries, hyderabadLongitudeBoundaries);

    Double[] delhiLatitudeBoundaries = {28.404286, 28.838257};
    Double[] delhiLongitudeBoundaries = {76.859436, 77.416992};
    positionCarsWithinBoundary(
        3 * numberOfCarsInACity, delhiLatitudeBoundaries, delhiLongitudeBoundaries);

    Double[] mumbaiLatitudeBoundaries = {18.927938, 19.223854};
    Double[] mumbaiLongitudeBoundaries = {72.813720, 72.978515};
    positionCarsWithinBoundary(
        4 * numberOfCarsInACity, mumbaiLatitudeBoundaries, mumbaiLongitudeBoundaries);

    Double[] kolkattaLatitudeBoundaries = {21.854701, 23.072993};
    Double[] kolkattaLongitudeBoundaries = {87.864990, 88.809814};
    positionCarsWithinBoundary(
        5 * numberOfCarsInACity, kolkattaLatitudeBoundaries, kolkattaLongitudeBoundaries);
  }

  private void positionCarsWithinBoundary(
      int startingCarId, Double[] latitudeBoundaries, Double[] longitudeBoundaries) {
    CarStatusTable carStatusTable = new CarStatusTable();

    // Using a constant seed to make the testing easier.
    Random generator = new Random(GlobalConstants.CAR_STATUS_TABLE_INIT_SEED);
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();

    for (int i = startingCarId; i < startingCarId + numberOfCarsInACity; i++) {
      GeoLocation geoLocation = new GeoLocation();
      geoLocation.setLatitude(
          latitudeBoundaries[0]
              + generator.nextDouble() * (latitudeBoundaries[1] - latitudeBoundaries[0]));
      geoLocation.setLongitude(
          longitudeBoundaries[0]
              + generator.nextDouble() * (longitudeBoundaries[1] - longitudeBoundaries[0]));

      carStatusTable.setCarId("CAR_ID_" + i);
      carStatusTable.setCarAvailability(
          CarStatus.CarAvailability.values()[i % CarAvailability.values().length]);
      carStatusTable.setCarType(Car.CarType.values()[i % CarType.values().length]);
      carStatusTable.setGeoLocation(geoLocation);
      em.persist(carStatusTable);
      em.flush();
      em.clear();
    }
    em.getTransaction().commit();
    em.close();
  }
}
