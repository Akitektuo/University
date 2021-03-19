package ro.ubb.truckers;

import ro.ubb.truckers.network.ServerApplication;
import ro.ubb.truckers.repository.RepositoryProvider;
import ro.ubb.truckers.service.*;
import ro.ubb.truckers.util.ServiceProvider;
import ro.ubb.truckers.util.constant.RepositoryType;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Executors;

import static ro.ubb.truckers.util.Constants.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        configureServices();
        runServer();
    }

    private static void configureServices() throws SQLException {
        ServiceProvider.registerService(DriverManager.getConnection(SQL_URL, SQL_USERNAME, SQL_PASSWORD));

        var repositoryProvider = new RepositoryProvider();
        repositoryProvider.setFileRepositoriesFileName(DEFAULT_FILE_NAME);
        repositoryProvider.setXmlRepositoriesFileName(DEFAULT_XML_NAME);
        repositoryProvider.setRepositoryType(RepositoryType.SQL);

        ServiceProvider.registerService(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
        ServiceProvider.registerService(repositoryProvider);

        ServiceProvider.registerService(new CompanyService());
        ServiceProvider.registerService(new DeliveryService());
        ServiceProvider.registerService(new GarageService());
        ServiceProvider.registerService(new TruckService());
        ServiceProvider.registerService(new UserService());
    }

    private static void runServer() {
        new ServerApplication().run();
    }
}
