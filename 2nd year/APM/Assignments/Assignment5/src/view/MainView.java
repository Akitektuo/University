package view;

import controller.Controller;
import repository.RepositoryInterface;
import view.command.ExitCommand;
import view.command.RunExampleCommand;

import static utils.PredefinedProgramStates.*;

public class MainView {
    private final Controller controller;
    private final TextMenuView textMenuView = new TextMenuView();

    public MainView(RepositoryInterface repository) {
        controller = new Controller(repository, true);

        textMenuView.addCommand(new ExitCommand("0", "Exit"));
        textMenuView.addCommand(new RunExampleCommand(
                "1", VISUAL_PROGRAM_1, controller, () -> controller.addProgramState(PROGRAM_1)));
        textMenuView.addCommand(new RunExampleCommand(
                "2", VISUAL_PROGRAM_2, controller, () -> controller.addProgramState(PROGRAM_2)));
        textMenuView.addCommand(new RunExampleCommand(
                "3", VISUAL_PROGRAM_3, controller, () -> controller.addProgramState(PROGRAM_3)));
        textMenuView.addCommand(new RunExampleCommand(
                "4", VISUAL_PROGRAM_4, controller, () -> controller.addProgramState(PROGRAM_4)));
        textMenuView.addCommand(new RunExampleCommand(
                "5", VISUAL_PROGRAM_5, controller, () -> controller.addProgramState(PROGRAM_5)));
        textMenuView.addCommand(new RunExampleCommand(
                "6", VISUAL_PROGRAM_6, controller, () -> controller.addProgramState(PROGRAM_6)));
        textMenuView.addCommand(new RunExampleCommand(
                "7", VISUAL_PROGRAM_7, controller, () -> controller.addProgramState(PROGRAM_7)));
        textMenuView.addCommand(new RunExampleCommand(
                "8", VISUAL_PROGRAM_8, controller, () -> controller.addProgramState(PROGRAM_8)));
        textMenuView.addCommand(new RunExampleCommand(
                "9", VISUAL_PROGRAM_9, controller, () -> controller.addProgramState(PROGRAM_9)));
        textMenuView.addCommand(new RunExampleCommand(
                "10", VISUAL_PROGRAM_10, controller, () -> controller.addProgramState(PROGRAM_10)));
    }

    public void start() {
        textMenuView.show();
    }
}
