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

// Class that populates various tables with some dummy values for tests.
public class TestTableSetup {
  private EntityManagerFactory emf;

  public TestTableSetup() {
    emf = GlobalConstants.getEntityManagerFactory();
    assert (emf != null);
  }

  public void init() {
    if (!areTablesAlreadyInitialized()) {
      initCarStatusTable();
    }
  }

  private boolean areTablesAlreadyInitialized() {
    // HACK: We don't want to do the initialization of tables multiple times during tests.
    // As a quick hack, we are just checking whether the CAR_ID_1 exists in CarStatus table
    // and if so, not proceed ahead with initializing the tables.
    EntityManager em = emf.createEntityManager();
    CarStatusTable carDetailsTableEntry = em.find(CarStatusTable.class, "CAR_ID_1");
    if (carDetailsTableEntry == null) {
      return false;
    } else {
      return true;
    }
  }

  private void initCarStatusTable() {
    // Position the cars randomly within Bangalore.
    // 12.973277, 77.596921 - E
    // 12.953472, 77.478061 - W
    // 13.018920, 77.532130 - N
    // 12.882345, 77.492376 - S
    Double[] bangaloreLatitudeBoundaries = {12.870595, 13.039963};
    Double[] bangaloreLongitudeBoundaries = {77.481075, 77.700260};
    positionCarsWithinBoundary(0, bangaloreLatitudeBoundaries, bangaloreLongitudeBoundaries);

    Double[] bayAreaLatitudeBoundaries = {37.377695, 37.431580};
    Double[] bayAreaLongitudeBoundaries = {-121.964033, -121.902025};
    positionCarsWithinBoundary(3000, bayAreaLatitudeBoundaries, bayAreaLongitudeBoundaries);
  }

  private void positionCarsWithinBoundary(
      int startingCarId, Double[] latitudeBoundaries, Double[] longitudeBoundaries) {
    CarStatusTable carStatusTable = new CarStatusTable();

    // Using a constant seed to make the testing easier.
    Random generator = new Random(GlobalConstants.CAR_STATUS_TABLE_INIT_SEED);
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();

    for (int i = startingCarId; i < startingCarId + 3000; i++) {
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
