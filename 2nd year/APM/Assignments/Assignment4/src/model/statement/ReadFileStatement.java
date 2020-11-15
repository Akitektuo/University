package model.statement;

import model.ProgramState;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.type.Types;
import model.value.IntegerValue;

import java.io.IOException;

public class ReadFileStatement implements StatementInterface {
    private final ExpressionInterface fileNameExpression;
    private final String variableName;

    public ReadFileStatement(ExpressionInterface fileNameExpression, String variableName) {
        this.fileNameExpression = fileNameExpression;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException {
        var systemTable = programState.getSystemTable();
        var fileTable = programState.getFileTable();

        var fileNameValue = fileNameExpression.evaluate(systemTable);
        if (fileNameValue.getType().get() != Types.STRING) {
            throw new StatementException("File name is not of type string!");
        }

        var fileName = (String) fileNameValue.getValue();
        if (!fileTable.hasKey(fileName)) {
            throw new StatementException(String.format("File '%s' is not opened!", fileName));
        }

        if (!systemTable.hasKey(variableName)) {
            throw new StatementException(String.format("Variable '%s' has not been declared!", variableName));
        }

        var variableType = systemTable.get(variableName).getType();
        if (variableType.get() != Types.NUMBER) {
            throw new StatementException(String.format("Variable '%s' is not of type number!", variableName));
        }

        var buffer = fileTable.get(fileName);
        try {
            var line = buffer.readLine();
            if (line == null) {
                return programState.setVariable(variableName, new IntegerValue(0));
            }
            return programState.setVariable(variableName, new IntegerValue(Integer.parseInt(line)));
        } catch (IOException exception) {
            throw new StatementException(exception.getMessage());
        }
    }

    @Override
    public String toString() {
        return String.format("readFile(%s, %s)", fileNameExpression, variableName);
    }
}
