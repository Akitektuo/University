package view;

import controller.Controller;
import repository.RepositoryInterface;
import view.command.ExitCommand;
import view.command.RunExampleCommand;

import java.util.Scanner;

import static utils.PredefinedProgramStates.PROGRAMS;

public class MainView {
    private final Scanner input = new Scanner(System.in);
    private final Controller controller;

    public MainView(RepositoryInterface repository) {
        controller = new Controller(repository, true);
    }

    public void start() {
        if (isGuiChosen()) {
            initializeGui();
        } else {
            initializeTextMenu().show();
        }
    }

    public boolean isGuiChosen() {
        System.out.print("Do you want to use the GUI? (Y/N - default Y): ");
        var decision = input.nextLine();

        return decision.isBlank() || decision.toLowerCase().contains("y");
    }

    public void initializeGui() {

    }

    public TextMenuView initializeTextMenu() {
        var textMenuView = new TextMenuView();

        textMenuView.addCommand(new ExitCommand("0", "Exit"));
        for (var i = 0; i < PROGRAMS.getSize(); i++) {
            textMenuView.addCommand(new RunExampleCommand(controller, i));
        }

        return textMenuView;
    }
}
