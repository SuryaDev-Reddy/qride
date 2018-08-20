package write;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.Is.is;

import car.Car.CarType;
import car.CarStatus;
import car.CarStatus.CarAvailability;
import globals.GlobalConstants;
import interfaces.WriteDataHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import location.GeoLocation;
import org.h2.tools.RunScript;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import tables.CarStatusTable;
import tables.setup.TestTableSetup;
import trip.ScheduleTripTransactionInput;
import trip.ScheduleTripTransactionResult;
import trip.ScheduleTripTransactionResult.ScheduleTripTransactionStatus;
import trip.Trip;
import trip.Trip.TripStatus;
import utils.TestUtils;

public class WriteDataHandlerImplTest {

  private static EntityManagerFactory emf;
  private static EntityManager em;
  private final WriteDataHandler writeDataHandler = new WriteDataHandlerImpl();

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

  @Test
  public void getValidScheduleTransactionStatus() {
    GeoLocation sourceLocation = TestUtils.createGeoLocationObject(12.908486,77.536386);
    GeoLocation destinationLocation = TestUtils.createGeoLocationObject(12.908486,78.536386);

    ScheduleTripTransactionInput scheduleTripTransactionInput = new ScheduleTripTransactionInput(
        CarType.CAR_TYPE_HATCHBACK,(float)129,sourceLocation,destinationLocation);

    ScheduleTripTransactionResult scheduleTripTransactionResult = writeDataHandler.scheduleTrip(scheduleTripTransactionInput);
    ScheduleTripTransactionStatus scheduleTripTransactionStatus = scheduleTripTransactionResult.getScheduleTripTransactionStatus();
    assertThat(scheduleTripTransactionStatus.name(), anyOf(is("SUCCESSFULLY_BOOKED"), is("NO_CARS_AVAILABLE")));
  }


  @Test
  public void checkTripStatusAndCarStatus() {
    GeoLocation sourceLocation = TestUtils.createGeoLocationObject(12.908486,77.536386);
    GeoLocation destinationLocation = TestUtils.createGeoLocationObject(12.908486,78.536386);

    ScheduleTripTransactionInput scheduleTripTransactionInput = new ScheduleTripTransactionInput(
        CarType.CAR_TYPE_HATCHBACK,(float)129,sourceLocation,destinationLocation);

    ScheduleTripTransactionResult scheduleTripTransactionResult = writeDataHandler.scheduleTrip(scheduleTripTransactionInput);
    Trip trip = scheduleTripTransactionResult.getTrip();
    if (trip != null) {
      assertEquals(trip.getTripStatus(),TripStatus.TRIP_STATUS_SCHEDULED);
      String carId = trip.getCarId();
      CarStatusTable carStatusTable = em.find(CarStatusTable.class, carId);
      assertEquals(carStatusTable.getCarAvailability(), CarAvailability.CAR_ON_TRIP);
    }
  }

}