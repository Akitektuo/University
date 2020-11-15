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
    }

    public void start() {
        textMenuView.show();
    }
}
