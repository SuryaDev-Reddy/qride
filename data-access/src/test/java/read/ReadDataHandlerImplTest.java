package read;

import static org.junit.Assert.assertEquals;

import car.Car.CarType;
import car.CarStatus;
import car.CarStatus.CarAvailability;
import globals.GlobalConstants;
import interfaces.ReadDataHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.h2.tools.RunScript;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import tables.setup.TestTableSetup;
import utils.TestUtils;

// Scope: Tests ReadDataHandler interface implementation for both normal & boundary cases.
public class ReadDataHandlerImplTest {
  private static EntityManagerFactory emf;
  private static EntityManager em;
  private final ReadDataHandler readDataHandler = new ReadDataHandlerImpl();

  @BeforeClass
  public static void init() throws FileNotFoundException, SQLException {
    System.out.println("In init - will be run only once");
    // TIP: You can uncomment the following lines & go to http://localhost:8082/ in
    // your browser to debug the database.
    // Server webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082");
    // webServer.start();
    // 1. Make sure you suspend only the specific thread and not all threads.
    //    See here for details: https://www.jetbrains.com/help/idea/breakpoints.html#d1481308e168
    // 2. Also make sure you type the connection url correctly as given in persistence.xml file.

    // Initialize the database.
    GlobalConstants.initDatabase(GlobalConstants.PERSISTENCE_TEST_UNIT_NAME);
    emf = GlobalConstants.getEntityManagerFactory();
    em = emf.createEntityManager();

    Session session = em.unwrap(Session.class);
    session.doWork(
        new Work() {
          @Override
          public void execute(Connection connection) throws SQLException {
            try {
              File script = new File(getClass().getResource("/schema.sql").getFile());
              RunScript.execute(connection, new FileReader(script));
            } catch (FileNotFoundException e) {
              throw new RuntimeException("Could not initialize the data base with schema.sql");
            }
          }
        });

    // Setup the tables.
    System.out.println("Initialize Tables");
    TestTableSetup testTableSetup = new TestTableSetup();
    testTableSetup.init();
  }

  @AfterClass
  public static void tearDown() {
    // TIP: If you are hitting this assert, then it is possible that you are running
    // all the JUnit tests in the same JVM. You might have to use the 'Fork mode' in Intellij
    // test configuration to fix that.
    // Refer https://www.jetbrains.com/help/idea/run-debug-configuration-junit.html for details.
    em.clear();
    em.close();
    emf.close();
  }


  // TODO: CRIO_TEST - Add/modify tests to ensure
  // ReadDataHandlerImpl.getCarsInLocation() is tested well.
  @Test
  public void getCarsInLocationNormalCase() {
    List<CarStatus> carStatusList =
        readDataHandler.getCarsInLocation(TestUtils.createGeoLocationObject(12.953472, 77.478061));
    // Assert based on our test table data.
    assertEquals("# of available cars expected to be within the radius", 20, carStatusList.size());
    for (CarStatus carStatus : carStatusList) {
      assertEquals(
          "Only return cars which are available",
          CarStatus.CarAvailability.CAR_AVAILABLE,
          carStatus.getCarAvailability());
    }
  }

  // Test for handling cases where there are no cars available in a given location.
  @Test
  public void getCarsInLocationEmptyCase() {
    List<CarStatus> carStatusList =
        readDataHandler.getCarsInLocation(TestUtils.createGeoLocationObject(14.00D, 80.00D));
    assertEquals("# of available cars expected to be within the radius", 0, carStatusList.size());

  }


  @Test
  public void getCarsForAvailabilityCase() {

    List<CarStatus> carStatusList =
        readDataHandler.getCarsInLocation(TestUtils.createGeoLocationObject(12.953472, 77.478061));

    for (int i = 0;i < carStatusList.size();i++) {
      assertEquals(carStatusList.get(i).getCarAvailability().equals(CarAvailability.CAR_AVAILABLE)
          || carStatusList.get(i).getCarAvailability()
          .equals(CarAvailability.CAR_ON_TRIP_CLOSE_TO_COMPLETION),true);
    }
  }

  @Test
  public void getCarsInLocationOfType() {
    List<CarStatus> carStatusList =
        readDataHandler.getCarsInLocationOfCarType(TestUtils.createGeoLocationObject(12.953472, 77.478061),CarType.CAR_TYPE_HATCHBACK);
    assert (carStatusList.size() != 0);
  }

  @Test
  public void getCarsWithinRadiusCase() {
    List<CarStatus> carStatusList =
        readDataHandler.getCarsInLocation(TestUtils.createGeoLocationObject(12.953472, 77.478061));
  }
}

