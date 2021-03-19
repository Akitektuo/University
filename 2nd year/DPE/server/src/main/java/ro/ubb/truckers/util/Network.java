package ro.ubb.truckers.util;

import org.json.JSONException;
import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.validator.ValidatorException;
import ro.ubb.truckers.util.network.Request;
import ro.ubb.truckers.util.network.Utils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Network {
    private Network() {
    }

    public static UnaryOperator<Request> wrapHandler(Consumer<String> methodCall) {
        return wrapHandler(parameters -> {
            methodCall.accept(parameters);

            return CompletableFuture.completedFuture("");
        });
    }

    public static UnaryOperator<Request> wrapHandler(Function<String, Future<String>> methodCall) {
        return request -> {
            try {
                var response = Utils.await(methodCall.apply(request.getBody()));

                return new Request(Request.OK, response);
            } catch (JSONException e) {
                return Request.fromError(Request.PRECONDITION_FAILED, e.getMessage());
            } catch (TruckersException | ValidatorException e) {
                return Request.fromError(Request.INTERNAL_SERVER_ERROR, e.getMessage());
            } catch (Exception e) {
                return Request.fromError(Request.INTERNAL_SERVER_ERROR);
            }
        };
    }
}
