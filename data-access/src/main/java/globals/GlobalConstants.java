package globals;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GlobalConstants {
  public static final String HAVER_SINE_FORMULA =
      "111.045 * DEGREES(ACOS(COS(RADIANS(:latPoint)) * COS(RADIANS(latitude))"
          + " * COS(RADIANS(:longPoint) - RADIANS(longitude)) + SIN(RADIANS(:latPoint))"
          + " * SIN(RADIANS(latitude))))";

  // TIP: This will be the persistence unit you will be using for dev.
  // You can find its definition in persistence.xml file.
  public static final String PERSISTENCE_UNIT_NAME = "qrideDatabaseEmbedded";

  // Name of the test persistence unit.
  public static final String PERSISTENCE_TEST_UNIT_NAME = "qrideDatabaseEmbedded";

  // Radius within which available cars are searched.
  public static final double SEARCH_RADIUS = 3.0;

  public static final int CAR_STATUS_TABLE_INIT_SEED = 777;

  // EntityManagerFactory that will be used. You need to set this to the test, dev/production
  // databases appropriately.
  private static EntityManagerFactory emf;

  public static void initDatabase(String persistenceUnit) {
    assert (emf == null);
    emf = Persistence.createEntityManagerFactory(persistenceUnit);
  }

  public static EntityManagerFactory getEntityManagerFactory() {
    assert (emf != null);
    return emf;
  }
}
