package view.command;

import controller.Controller;

import static utils.PredefinedProgramStates.PROGRAMS;
import static utils.PredefinedProgramStates.VISUAL_PROGRAMS;

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

    public RunExampleCommand(Controller controller, final int index) {
        super(String.valueOf(index + 1), VISUAL_PROGRAMS.get(index));
        this.controller = controller;
        this.beforeExecution = () -> controller.addProgramState(PROGRAMS.get(index));
    }

    @Override
    public void execute() {
        try {
            beforeExecution.run();
        } catch (Exception exception) {
            System.out.printf("Type error: %s\n", exception.getMessage());
        }
        try {
            controller.executeAllSteps();
            System.out.println("Executed successfully!");
        } catch (Exception exception) {
            System.out.printf("Error: %s\n", exception.getMessage());
        }
    }
}
