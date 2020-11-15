package view.command;

import controller.Controller;

public class RunExampleCommand extends BaseCommand {
    private final Controller controller;
    private final Runnable beforeExecution;

    public RunExampleCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
        beforeExecution = () -> {};
    }

    public RunExampleCommand(String key, String description, Controller controller, Runnable beforeExecution) {
        super(key, description);
        this.controller = controller;
        this.beforeExecution = beforeExecution;
    }

    @Override
    public void execute() {
        beforeExecution.run();
        try {
            controller.executeAllSteps();
            System.out.println("Executed successfully!");
        } catch (Exception exception) {
            System.out.printf("Error: %s%n", exception.getMessage());
        }
    }
}
