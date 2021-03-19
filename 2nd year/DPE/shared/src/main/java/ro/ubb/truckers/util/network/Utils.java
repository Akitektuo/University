package ro.ubb.truckers.util.network;

import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.util.ServiceProvider;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class Utils {
    private Utils() {
    }

    public static <T> Future<T> async(Callable<T> task) {
        var executor = ServiceProvider.inject(ThreadPoolExecutor.class);

        return executor.submit(task);
    }

    public static <T> Future<T> async(T value) {
        var executor = ServiceProvider.inject(ThreadPoolExecutor.class);

        return executor.submit(() -> value);
    }

    public static <T> T await(Future<T> future) {
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new TruckersException(getWrappedExceptionMessage(e));
        }
    }

    public static String getWrappedExceptionMessage(Exception exception) {
        var message = exception.getMessage();

        return getWrappedExceptionMessage(message);
    }

    public static String getWrappedExceptionMessage(String message) {
        return message.substring(message.indexOf(' ')).strip();
    }
}
