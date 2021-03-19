package ro.ubb.truckers.network;

import ro.ubb.truckers.controller.Controller;
import ro.ubb.truckers.domain.TruckersException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;

public class ServerApplication {
    private final ServerManager server = new ServerManager();
    private final Controller controller = new Controller();

    public ServerApplication() {
        registerServices();
    }

    public void run() {
        server.start();
    }

    private void registerServices() {
        Arrays.stream(Controller.class.getMethods())
                .forEach(this::addHandler);
    }

    private void addHandler(Method method) {
        if (method.getReturnType().equals(Void.TYPE)) {
            server.addHandler(method.getName(), buildConsumer(method));
            return;
        }
        server.addHandler(method.getName(), buildFunction(method));
    }

    private Consumer<String> buildConsumer(Method method) {
        return parameters -> {
            try {
                if (parameters.isBlank()) {
                    method.invoke(controller);
                    return;
                }
                method.invoke(controller, parameters);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new TruckersException(e.getCause());
            }
        };
    }

    @SuppressWarnings("unchecked")
    private Function<String, Future<String>> buildFunction(Method method) {
        return parameters -> {
            try {
                if (parameters.isBlank()) {
                    return (Future<String>) method.getReturnType().cast(method.invoke(controller));
                }
                return (Future<String>) method.getReturnType().cast(method.invoke(controller, parameters));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new TruckersException("Invocation exception on controller method: %s", e.getMessage());
            }
        };
    }
}
