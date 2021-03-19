package ro.ubb.truckers.util.constant;

import ro.ubb.truckers.domain.TruckersException;

import java.util.Arrays;

public enum RepositoryType {
    IN_MEMORY("inMemory"),
    FILE("file"),
    XML("xml"),
    SQL("sql");

    private final String name;

    RepositoryType(String name) {
        this.name = name;
    }

    /**
     * Returns the found enum based on the formatted name.
     *
     * @param name the formatted name of the enum.
     * @return the found @{code RepositoryType} enum with the given name.
     * @throws TruckersException in case no enum was found with the given name.
     */
    public static RepositoryType enumOf(String name) {
        return Arrays.stream(RepositoryType.values())
                .filter(value -> value.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new TruckersException("No enum value with name '%s' found", name));
    }

    /**
     * Returns the formatted name of the enum.
     *
     * @return a {@code String} representing the formatted name.
     */
    public String getName() {
        return name;
    }
}
