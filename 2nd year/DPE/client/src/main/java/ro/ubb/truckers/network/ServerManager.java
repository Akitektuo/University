package ro.ubb.truckers.network;

import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.util.constant.Constants;
import ro.ubb.truckers.util.network.Request;
import ro.ubb.truckers.util.network.Utils;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Future;

public class ServerManager {
    private ServerManager() {
    }

    public static Future<String> sendRequest(String apiName) {
        return sendRequest(apiName, "");
    }

    public static Future<String> sendRequest(String apiName, String parameters) {
        return Utils.async(handleRequest(apiName, parameters));
    }

    private static String handleRequest(String apiName, String parameters) {
        var request = new Request(apiName, parameters);

        try (var socket = new Socket(Constants.HOST, Constants.PORT);
             var input = socket.getInputStream();
             var output = socket.getOutputStream()) {
            request.writeTo(output);

            var response = new Request(input);

            return switch (response.getHeader()) {
                case Request.OK -> response.getBody();
                case Request.NOT_FOUND -> throw new TruckersException("API '%s' not found", apiName);
                case Request.PRECONDITION_FAILED -> throw new TruckersException("Could not parse request parameters: %s", request.getBody());
                case Request.INTERNAL_SERVER_ERROR -> throw handleInternalServerError(response);
                case Request.NOT_IMPLEMENTED -> throw new TruckersException("API %s not implemented", apiName);
                default -> throw new TruckersException("Unknown response code: %s", request.getHeader());
            };
        } catch (IOException e) {
            throw new TruckersException("Could not connect to server with API: %s", apiName);
        }
    }

    private static TruckersException handleInternalServerError(Request response) {
        if (response.getBody().isBlank()) {
            return new TruckersException("Internal server error");
        }
        return new TruckersException(Utils.getWrappedExceptionMessage(response.getBody()));
    }
}
