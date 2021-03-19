package ro.ubb.truckers.repository;

import org.postgresql.jdbc.PgConnection;
import ro.ubb.truckers.domain.entity.*;
import ro.ubb.truckers.domain.validator.*;
import ro.ubb.truckers.repository.mapper.file.*;
import ro.ubb.truckers.repository.mapper.sql.*;
import ro.ubb.truckers.repository.mapper.xml.*;
import ro.ubb.truckers.util.Pair;
import ro.ubb.truckers.util.ServiceProvider;
import ro.ubb.truckers.util.constant.RepositoryType;

import java.sql.SQLException;
import java.util.EnumMap;

/**
 * Helps with feeding the application the selected repository by the user with ease.
 */
public class RepositoryProvider {
    /**
     * A map between all the types of repositories and their respective instances for the {@code Company} entity.
     */
    private final EnumMap<RepositoryType, Repository<Integer, Company>> companyRepositories =
            new EnumMap<>(RepositoryType.class);
    /**
     * A map between all the types of repositories and their respective instances for the {@code Delivery} entity.
     */
    private final EnumMap<RepositoryType, Repository<Integer, Delivery>> deliveryRepositories =
            new EnumMap<>(RepositoryType.class);
    /**
     * A map between all the types of repositories and their respective instances for the {@code Garage} entity.
     */
    private final EnumMap<RepositoryType, Repository<Integer, Garage>> garageRepositories =
            new EnumMap<>(RepositoryType.class);
    /**
     * A map between all the types of repositories and their respective instances for the {@code Truck} entity.
     */
    private final EnumMap<RepositoryType, Repository<Integer, Truck>> truckRepositories =
            new EnumMap<>(RepositoryType.class);
    /**
     * A map between all the types of repositories and their respective instances for the {@code User} entity.
     */
    private final EnumMap<RepositoryType, Repository<Integer, User>> userRepositories =
            new EnumMap<>(RepositoryType.class);
    /**
     * A map between all the types of repositories and their respective instances for the user {@code UserCompany}
     * entity.
     */
    private final EnumMap<RepositoryType, Repository<Pair<Integer, Integer>, UserCompany>> userCompanyRepositories =
            new EnumMap<>(RepositoryType.class);

    /**
     * Specifies which repository to use.
     */
    private RepositoryType currentRepository = RepositoryType.IN_MEMORY;

    /**
     * Sets up each type of repository for each type of entity.
     */
    public RepositoryProvider() {
        setupCompanyRepositories();
        setupDeliveryRepositories();
        setupGarageRepositories();
        setupTruckRepositories();
        setupUserRepositories();
        setupUserCompanyRepositories();
    }

    /**
     * Specifies the repository type that will be used by the entire application.
     *
     * @param repositoryType represents the new repository type that will be used.
     */
    public void setRepositoryType(RepositoryType repositoryType) {
        currentRepository = repositoryType;
    }

    /**
     * Sets the file name of the {@code FileRepository}.
     *
     * @param fileName represents the name of the file.
     */
    public void setFileRepositoriesFileName(String fileName) {
        ((FileRepository<Integer, Company>) companyRepositories.get(RepositoryType.FILE)).setFileName(fileName);
        ((FileRepository<Integer, Delivery>) deliveryRepositories.get(RepositoryType.FILE)).setFileName(fileName);
        ((FileRepository<Integer, Garage>) garageRepositories.get(RepositoryType.FILE)).setFileName(fileName);
        ((FileRepository<Integer, Truck>) truckRepositories.get(RepositoryType.FILE)).setFileName(fileName);
        ((FileRepository<Integer, User>) userRepositories.get(RepositoryType.FILE)).setFileName(fileName);
        ((FileRepository<Pair<Integer, Integer>, UserCompany>) userCompanyRepositories.get(RepositoryType.FILE))
                .setFileName(fileName);
    }

    /**
     * Sets the file name of the {@code XmlRepository}.
     *
     * @param fileName represents the name of the file.
     */
    public void setXmlRepositoriesFileName(String fileName) {
        ((XmlRepository<Integer, Company>) companyRepositories.get(RepositoryType.XML)).setFileName(fileName);
        ((XmlRepository<Integer, Delivery>) deliveryRepositories.get(RepositoryType.XML)).setFileName(fileName);
        ((XmlRepository<Integer, Garage>) garageRepositories.get(RepositoryType.XML)).setFileName(fileName);
        ((XmlRepository<Integer, Truck>) truckRepositories.get(RepositoryType.XML)).setFileName(fileName);
        ((XmlRepository<Integer, User>) userRepositories.get(RepositoryType.XML)).setFileName(fileName);
        ((XmlRepository<Pair<Integer, Integer>, UserCompany>) userCompanyRepositories.get(RepositoryType.XML))
                .setFileName(fileName);
    }

    /**
     * Retrieves the current type of repository for the {@code Company} entity.
     *
     * @return a {@code Repository} instance.
     */
    public Repository<Integer, Company> getCompanyRepository() {
        return companyRepositories.get(currentRepository);
    }

    /**
     * Retrieves the current type of repository for the {@code Delivery} entity.
     *
     * @return a {@code Repository} instance.
     */
    public Repository<Integer, Delivery> getDeliveryRepository() {
        return deliveryRepositories.get(currentRepository);
    }

    /**
     * Retrieves the current type of repository for the {@code Garage} entity.
     *
     * @return a {@code Repository} instance.
     */
    public Repository<Integer, Garage> getGarageRepository() {
        return garageRepositories.get(currentRepository);
    }

    /**
     * Retrieves the current type of repository for the {@code Truck} entity.
     *
     * @return a {@code Repository} instance.
     */
    public Repository<Integer, Truck> getTruckRepository() {
        return truckRepositories.get(currentRepository);
    }

    /**
     * Retrieves the current type of repository for the {@code User} entity.
     *
     * @return a {@code Repository} instance.
     */
    public Repository<Integer, User> getUserRepository() {
        return userRepositories.get(currentRepository);
    }

    /**
     * Retrieves the current type of repository for the {@code UserCompany} entity.
     *
     * @return a {@code Repository} instance.
     */
    public Repository<Pair<Integer, Integer>, UserCompany> getUserCompanyRepository() {
        return userCompanyRepositories.get(currentRepository);
    }

    /**
     * Sets up all the repositories for the {@code Company} entity.
     */
    private void setupCompanyRepositories() {
        var validator = new CompanyValidator();

        companyRepositories.put(RepositoryType.IN_MEMORY, new MemoryRepository<>(validator));
        companyRepositories.put(RepositoryType.FILE,
                new FileRepository<>("company", validator, new CompanyFileMapper()));
        companyRepositories.put(RepositoryType.XML,
                new XmlRepository<>("company", validator, new CompanyXmlMapper()));
        companyRepositories.put(RepositoryType.SQL,
                new SqlRepository<>("companies", validator, new CompanySqlMapper()));
    }

    /**
     * Sets up all the repositories for the {@code Delivery} entity.
     */
    private void setupDeliveryRepositories() {
        var validator = new DeliveryValidator();

        deliveryRepositories.put(RepositoryType.IN_MEMORY, new MemoryRepository<>(validator));
        deliveryRepositories.put(RepositoryType.FILE,
                new FileRepository<>("delivery", validator, new DeliveryFileMapper()));
        deliveryRepositories.put(RepositoryType.XML,
                new XmlRepository<>("delivery", validator, new DeliveryXmlMapper()));
        deliveryRepositories.put(RepositoryType.SQL,
                new SqlRepository<>("deliveries", validator, new DeliverySqlMapper()));
    }

    /**
     * Sets up all the repositories for the {@code Garage} entity.
     */
    private void setupGarageRepositories() {
        var validator = new GarageValidator();

        garageRepositories.put(RepositoryType.IN_MEMORY, new MemoryRepository<>(validator));
        garageRepositories.put(RepositoryType.FILE,
                new FileRepository<>("garage", validator, new GarageFileMapper()));
        garageRepositories.put(RepositoryType.XML,
                new XmlRepository<>("garage", validator, new GarageXmlMapper()));
        garageRepositories.put(RepositoryType.SQL,
                new SqlRepository<>("garages", validator, new GarageSqlMapper()));
    }

    /**
     * Sets up all the repositories for the {@code Truck} entity.
     */
    private void setupTruckRepositories() {
        var validator = new TruckValidator();

        truckRepositories.put(RepositoryType.IN_MEMORY, new MemoryRepository<>(validator));
        truckRepositories.put(RepositoryType.FILE,
                new FileRepository<>("truck", validator, new TruckFileMapper()));
        truckRepositories.put(RepositoryType.XML,
                new XmlRepository<>("truck", validator, new TruckXmlMapper()));
        truckRepositories.put(RepositoryType.SQL,
                new SqlRepository<>("trucks", validator, new TruckSqlMapper()));
    }

    /**
     * Sets up all the repositories for the {@code User} entity.
     */
    private void setupUserRepositories() {
        var validator = new UserValidator();

        userRepositories.put(RepositoryType.IN_MEMORY, new MemoryRepository<>(validator));
        userRepositories.put(RepositoryType.FILE,
                new FileRepository<>("user", validator, new UserFileMapper()));
        userRepositories.put(RepositoryType.XML,
                new XmlRepository<>("user", validator, new UserXmlMapper()));
        userRepositories.put(RepositoryType.SQL,
                new SqlRepository<>("users", validator, new UserSqlMapper()));
    }

    /**
     * Sets up all the repositories for the {@code UserCompany} entity.
     */
    private void setupUserCompanyRepositories() {
        var validator = new UserCompanyValidator();

        userCompanyRepositories.put(RepositoryType.IN_MEMORY, new MemoryRepository<>(validator));
        userCompanyRepositories.put(RepositoryType.FILE,
                new FileRepository<>("userCompany", validator, new UserCompanyFileMapper()));
        userCompanyRepositories.put(RepositoryType.XML,
                new XmlRepository<>("userCompany", validator, new UserCompanyXmlMapper()));
        userCompanyRepositories.put(RepositoryType.SQL,
                new SqlRepository<>("usercompanies", validator, new UserCompanySqlMapper()));
    }

    /**
     * Cleans up the repositories.
     */
    public void cleanUp() {
        try {
            ServiceProvider.inject(PgConnection.class).close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
