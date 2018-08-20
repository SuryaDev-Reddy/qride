package write;

import car.Car;
import car.Car.CarType;
import car.CarStatus;
import car.CarStatus.CarAvailability;
import globals.GlobalConstants;
import interfaces.ReadDataHandler;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import location.GeoLocation;
import location.GeoUtils;
import read.FormatConverters;
import read.ReadDataHandlerImpl;
import tables.CarDetailsTable;
import tables.CarStatusTable;
import tables.DriverDetailsTable;
import tables.TripTable;
import trip.ScheduleTripTransactionInput;
import trip.ScheduleTripTransactionResult;
import trip.ScheduleTripTransactionResult.ScheduleTripTransactionStatus;
import trip.Trip;
import trip.Trip.TripStatus;

// This class contains methods to schedule a trip.
public class ScheduleTripTransaction {
  // TODO: CRIO_TASK: Implement scheduleTrip() function.
  // Does everything to schedule a trip:
  //   - Finds out if there is a car available of given carType in the nearby radius.
  //   - If available, creates a Trip entry, populates it and stores it in the appropriate table.
  //   - Also changes the status of the specific car.
  // Input:
  //   scheduleTripTransactionInput - Contains carType, tripPrice, start/end locations.
  // Output:
  //   1. If we are able to find a car available of this particular car type within
  //      the search radius, schedule the closest proximity car & return
  //      ScheduleTripTransactionResult populated with:
  //      - scheduleTripTransactionStatus = SUCCESSFULLY_BOOKED.
  //      - trip details with updated tripId (any random id works), carId, driverId,
  //        startTimeInEpochs, source/destination locations, trip price,
  //        & tripStatus = TRIP_STATUS_SCHEDULED.
  //   2. If no cars are available, return ScheduleTripTransactionResult populated with:
  //      - scheduleTripTransactionStatus = NO_CARS_AVAILABLE without any trip information.
  //   3. In case of any other errors, return ScheduleTripTransactionResult populated with
  //      appropriate scheduleTripTransactionStatus without any trip information.
  //   If a car was selected, make sure to update the Trip info & status of the selected car
  //   to CAR_ON_TRIP in appropriate tables.
  private ReadDataHandler readDataHandler;
  private EntityManager em;

  public ScheduleTripTransaction() {
    em = GlobalConstants.getEntityManagerFactory().createEntityManager();
    this.readDataHandler = new ReadDataHandlerImpl();
  }

  public ScheduleTripTransactionResult scheduleTrip(
      ScheduleTripTransactionInput scheduleTripTransactionInput) {

    Car.CarType carType = scheduleTripTransactionInput.getCarType();
    float tripPrice = scheduleTripTransactionInput.getTripPrice();
    GeoLocation sourceLocation = scheduleTripTransactionInput.getSourceLocation();
    GeoLocation destinationLocation = scheduleTripTransactionInput.getDestinationLocation();

    List<CarStatus> carStatusList = readDataHandler.getCarsInLocationOfCarType(sourceLocation,carType);

    ScheduleTripTransactionResult scheduleTripTransactionResult = new ScheduleTripTransactionResult();

    if (carStatusList.size() == 0 ) {
      scheduleTripTransactionResult.setScheduleTripTransactionStatus(ScheduleTripTransactionStatus.NO_CARS_AVAILABLE);
    }

    else {

      TripTable tripTable = createNewTripTableEntry(scheduleTripTransactionInput);
      assert (tripTable != null);
      Trip trip = FormatConverters.convertTripTableEntryIntoTrip(tripTable);

      scheduleTripTransactionResult.setScheduleTripTransactionStatus(ScheduleTripTransactionStatus.SUCCESSFULLY_BOOKED);

      assert (trip != null);
      String carId = trip.getCarId();
      CarStatusTable carStatusTable = em.find(CarStatusTable.class, carId);
      carStatusTable.setCarAvailability(CarAvailability.CAR_ON_TRIP);

      em.getTransaction().begin();
      em.persist(carStatusTable);
      em.getTransaction().commit();

      scheduleTripTransactionResult.setTrip(trip);
    }

    return scheduleTripTransactionResult;
  }

  // TODO: CRIO_TASK: Utility function to create a new TripTable entry from
  // scheduleTripTransactionInput.
  // Helps keep your code modularized, but it is not mandatory to use this function.
  private TripTable createNewTripTableEntry(
      ScheduleTripTransactionInput scheduleTripTransactionInput) {

    Random rand = new Random();
    int tripId = rand.nextInt(100);

    TripTable tripTable = new TripTable();
    tripTable.setTripId("TRIP_ID"+ tripId);

    Car.CarType carType = scheduleTripTransactionInput.getCarType();
    float tripPrice = scheduleTripTransactionInput.getTripPrice();
    GeoLocation sourceLocation = scheduleTripTransactionInput.getSourceLocation();
    GeoLocation destinationLocation = scheduleTripTransactionInput.getDestinationLocation();

    List<CarStatus> carStatusList = readDataHandler.getCarsInLocationOfCarType(sourceLocation,carType);

    String carId = carStatusList.get(0).getCarId();

    assert (carId != null);

    CarDetailsTable carDetails = em.find(CarDetailsTable.class, carId);
    String driverId = carDetails.getDriverId();

    long startEpoch = System.currentTimeMillis();

    tripTable.setCarId(carId);
    tripTable.setDriverId(driverId);
    tripTable.setSourceLocation(sourceLocation);
    tripTable.setDestinationLocation(destinationLocation);
    tripTable.setTripPrice(tripPrice);
    tripTable.setTripStatus(TripStatus.TRIP_STATUS_SCHEDULED);
    tripTable.setStartTimeInEpochs(startEpoch);

    em.getTransaction().begin();
    em.persist(tripTable);
    em.getTransaction().commit();

    return tripTable;
  }
}
