package write;

import interfaces.WriteDataHandler;
import trip.ScheduleTripTransactionInput;
import trip.ScheduleTripTransactionResult;

public class WriteDataHandlerImpl implements WriteDataHandler {
  @Override
  public ScheduleTripTransactionResult scheduleTrip(
      ScheduleTripTransactionInput scheduleTripTransactionInput) {
    ScheduleTripTransaction scheduleTripTransaction = new ScheduleTripTransaction();
    return scheduleTripTransaction.scheduleTrip(scheduleTripTransactionInput);
  }
}
