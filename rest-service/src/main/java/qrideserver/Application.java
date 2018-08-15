package qrideserver;

import globals.GlobalConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tables.setup.TableSetup;

@SpringBootApplication
public class Application {
  private static final Log logger = LogFactory.getLog(Application.class);

  // TODO: CRIO_TASK: Run main to start your app server!
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);

    // Setup the persistence context first before initializing the tables.
    GlobalConstants.initDatabase(GlobalConstants.PERSISTENCE_UNIT_NAME);

    // Setup the tables.
    TableSetup tableSetup = new TableSetup();
    tableSetup.init();

    // TIP: You can enable tracing for different classes & use logs for debugging.
    // Make sure to enable tracing for your class in application.properties file.
    if (logger.isTraceEnabled()) {
      // TIP: If your server starts successfully, you can find the following message in the
      // logs.
      logger.trace("Congrats! Your app server has started");
    }
  }
}
