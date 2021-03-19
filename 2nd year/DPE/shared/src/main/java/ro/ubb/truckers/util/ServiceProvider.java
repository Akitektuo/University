package ro.ubb.truckers.util;

import ro.ubb.truckers.domain.TruckersException;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class ServiceProvider {
    private static final HashMap<Class<?>, Object> services = new HashMap<>();

    private ServiceProvider() {
    }

    /**
     * Registers a service by passing the type and instance of that type.
     *
     * @param instance to store.
     * @param <T>      type of the instance.
     * @throws TruckersException if the type already has an instance.
     */
    public static <T> void registerService(T instance) {
        if (instance == null) {
            throw new NullPointerException("Instance is null");
        }
        if (services.containsKey(instance.getClass())) {
            throw new TruckersException("This type already has an instance");
        }
        services.put(instance.getClass(), instance);
    }

    /**
     * Registers a mocked service by passing the type and giving it {@code null} value.
     *
     * @param type to store.
     * @param <T>  type of the instance.
     * @throws TruckersException if the type already has an instance.
     */
    public static <T> void registerServiceMock(Class<T> type) {
        if (services.containsKey(type)) {
            throw new TruckersException("This type already has an instance");
        }
        services.put(type, null);
    }

    /**
     * Returns the instance of the provided type.
     *
     * @param type of the instance.
     * @param <T>  type of the instance.
     * @return the instance of specified type.
     */
    public static <T> T inject(Class<T> type) {
        if (!services.containsKey(type)) {
            throw new NoSuchElementException("No instance defined of type " + type.getName());
        }
        return type.cast(services.get(type));
    }

    /**
     * Clears all services.
     * !IMPORTANT Only use for testing!
     */
    public static void clear() {
        services.clear();
    }
}
