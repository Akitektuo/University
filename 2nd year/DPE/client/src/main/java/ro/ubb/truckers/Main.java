package ro.ubb.truckers;

import ro.ubb.truckers.console.CommandUI;
import ro.ubb.truckers.controller.Controller;
import ro.ubb.truckers.util.ServiceProvider;

import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        configureServices();
        runApplication();
    }

    private static void configureServices() {
        ServiceProvider.registerService(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
        ServiceProvider.registerService(new Controller());
    }

    private static void runApplication() {
        new CommandUI().run();
    }
}
