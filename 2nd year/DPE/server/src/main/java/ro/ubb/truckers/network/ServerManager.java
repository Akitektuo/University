package ro.ubb.truckers.network;

import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.util.Network;
import ro.ubb.truckers.util.ServiceProvider;
import ro.ubb.truckers.util.constant.Constants;
import ro.ubb.truckers.util.network.Request;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ServerManager {
    protected final ThreadPoolExecutor executor = ServiceProvider.inject(ThreadPoolExecutor.class);
    private final HashMap<String, UnaryOperator<Request>> handlers = new HashMap<>();

    public ServerManager addHandler(String apiName, UnaryOperator<Request> handler) {
        handlers.put(apiName, handler);

        return this;
    }

    public ServerManager addHandler(String apiName, Function<String, Future<String>> methodCall) {
        addHandler(apiName, Network.wrapHandler(methodCall));

        return this;
    }

    public ServerManager addHandler(String apiName, Consumer<String> methodCall) {
        addHandler(apiName, Network.wrapHandler(methodCall));

        return this;
    }

    public void start() {
        try (var serverSocket = new ServerSocket(Constants.PORT)) {
            while (true) {
                var clientSocket = serverSocket.accept();
                executor.submit(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            throw new TruckersException("IO exception: %s", e.getMessage());
        }
    }

    private void handleClient(Socket socket) {
        try (var input = socket.getInputStream();
             var output = socket.getOutputStream()) {
            var request = new Request(input);

            var handler = handlers.get(request.getHeader());

            var response = handler == null ?
                    Request.fromError(Request.NOT_FOUND) :
                    handler.apply(request);

            response.writeTo(output);
        } catch (IOException e) {
            throw new TruckersException("IO exception: %s", e.getMessage());
        }
    }
}
