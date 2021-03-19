package truckers.util;

import org.postgresql.jdbc.PgConnection;
import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.repository.RepositoryProvider;
import ro.ubb.truckers.service.*;
import ro.ubb.truckers.util.ServiceProvider;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Util {
    private Util() {
    }

    /**
     * Initializes the service provider with the required services for tests.
     */
    public static void initializeServiceProvider() {
        ServiceProvider.registerServiceMock(PgConnection.class);
        ServiceProvider.registerService(new RepositoryProvider());
        ServiceProvider.registerService(new CompanyService());
        ServiceProvider.registerService(new DeliveryService());
        ServiceProvider.registerService(new GarageService());
        ServiceProvider.registerService(new TruckService());
        ServiceProvider.registerService(new UserService());
    }

    /**
     * Clears all the defined services used for tests.
     */
    public static void destroyServiceProvider() {
        ServiceProvider.clear();
    }
}
