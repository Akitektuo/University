package view;

import controller.Controller;
import repository.RepositoryInterface;
import view.command.ExitCommand;
import view.command.RunExampleCommand;

import static utils.PredefinedProgramStates.*;

public class MainView {
    private final TextMenuView textMenuView = new TextMenuView();

    public MainView(RepositoryInterface repository) {
        var controller = new Controller(repository, true);

        textMenuView.addCommand(new ExitCommand("0", "Exit"));
        for (var i = 0; i < PROGRAMS.getSize(); i++) {
            textMenuView.addCommand(new RunExampleCommand(controller, i));
        }
    }

    public void start() {
        textMenuView.show();
    }
}
