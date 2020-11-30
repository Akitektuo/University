package model.statement.file;

import container.DictionaryInterface;
import model.ProgramState;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.statement.StatementException;
import model.statement.StatementInterface;
import model.type.TypeInterface;
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
        var fileTable = programState.getFileTable();

        var fileName = (String) fileNameExpression.evaluate(programState).getValue();
        if (!fileTable.hasKey(fileName)) {
            throw new StatementException("File '%s' is not opened!", fileName);
        }

        var variable = programState.getVariable(variableName);
        if (variable == null) {
            throw new StatementException("Variable '%s' has not been declared!", variableName);
        }

        var buffer = fileTable.get(fileName);
        try {
            var line = buffer.readLine();
            if (line == null) {
                programState.setVariable(variableName, new IntegerValue(0));
                return null;
            }
            programState.setVariable(variableName, new IntegerValue(Integer.parseInt(line)));
        } catch (IOException exception) {
            throw new StatementException(exception.getMessage());
        }
        return null;
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws StatementException, ExpressionException {
        var fileNameType = fileNameExpression.typeCheck(typeTable);
        if (fileNameType.get() != Types.STRING) {
            throw new StatementException("File name is not of type string!");
        }

        var variableType = typeTable.get(variableName);
        if (variableType.get() != Types.NUMBER) {
            throw new StatementException("Variable '%s' is not of type number!", variableName);
        }

        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("readFile(%s, %s)", fileNameExpression, variableName);
    }
}
