package view;

import controller.Controller;
import model.ProgramState;
import model.expression.ValueExpression;
import model.expression.VariableExpression;
import model.statement.AssignmentStatement;
import model.statement.CompoundStatement;
import model.statement.DeclarationStatement;
import model.statement.PrintStatement;
import model.type.NumberType;
import model.value.IntegerValue;
import repository.Repository;

public class MainView {
    private final Controller controller;

    public MainView(Repository repository) {
        controller = new Controller(repository);
    }

    public void start() {
        try {
            controller.addProgramState(new ProgramState(new CompoundStatement(
                    new DeclarationStatement("n", new NumberType()),
                    new AssignmentStatement("n", new ValueExpression(new IntegerValue(2))),
                    new PrintStatement(new VariableExpression("n"))
            )));
            System.out.println(controller.executeAllSteps());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

}
