package truckers.util;

import ro.ubb.truckers.domain.entity.Company;
import ro.ubb.truckers.domain.entity.Garage;
import ro.ubb.truckers.domain.entity.Truck;
import ro.ubb.truckers.domain.entity.User;
import ro.ubb.truckers.repository.RepositoryProvider;
import ro.ubb.truckers.util.ServiceProvider;

import java.time.LocalDate;

public class Seeder {
    private Seeder() {
    }

    /**
     * Seeds a company into the repository and returns it.
     *
     * @param id of the entity to create and seed.
     * @return the created and seeded entity.
     */
    public static Company seedCompany(int id) {
        var company = new Company(id, "Company name " + id);

        ServiceProvider.inject(RepositoryProvider.class)
                .getCompanyRepository()
                .save(company);

        return company;
    }

    /**
     * Seeds a garage into the repository and returns it.
     *
     * @param id of the entity to create and seed.
     * @return the created and seeded entity.
     */
    public static Garage seedGarage(int id) {
        var garage = new Garage(id, "Location " + id, id % 10 * 2, id % 5, id);

        ServiceProvider.inject(RepositoryProvider.class)
                .getGarageRepository()
                .save(garage);

        return garage;
    }

    /**
     * Seeds a truck into the repository and returns it.
     *
     * @param id of the entity to create and seed.
     * @return the created and seeded entity.
     */
    public static Truck seedTruck(int id) {
        var truck = new Truck(id, "Model" + id, "XX" + id + "XXX", id, 0, id);

        ServiceProvider.inject(RepositoryProvider.class)
                .getTruckRepository()
                .save(truck);

        return truck;
    }

    /**
     * Seeds a user into the repository and returns it.
     *
     * @param id of the entity to create and seed.
     * @return the created and seeded entity.
     */
    public static User seedUser(int id) {
        var user = new User(id,
                "First" + id,
                "Last" + id,
                String.format("email%s@email.com", id),
                "mysecretpassword" + id,
                LocalDate.of(1900 + id % 121, 1 + id % 11, 1 + id % 27),
                0);

        ServiceProvider.inject(RepositoryProvider.class)
                .getUserRepository()
                .save(user);

        return user;
    }
}
