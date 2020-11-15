package model.statement;

import model.ProgramState;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.type.Types;

import java.io.FileNotFoundException;

public class OpenReadFileStatement implements StatementInterface {
    private final ExpressionInterface fileNameExpression;

    public OpenReadFileStatement(ExpressionInterface fileNameExpression) {
        this.fileNameExpression = fileNameExpression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException {
        var fileNameValue = fileNameExpression.evaluate(programState.getSystemTable());
        if (fileNameValue.getType().get() != Types.STRING) {
            throw new StatementException("File name is not of type string!");
        }

        var fileName = (String) fileNameValue.getValue();
        if (programState.isFileOpened(fileName)) {
            throw new StatementException(String.format("File '%s' is already opened!", fileName));
        }

        try {
            return programState.openFile(fileName);
        } catch (FileNotFoundException exception) {
            throw new StatementException(exception.getMessage());
        }
    }

    @Override
    public String toString() {
        return String.format("openReadFile(%s)", fileNameExpression);
    }
}
