package model.statement.file;

import container.DictionaryInterface;
import model.ProgramState;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.statement.StatementException;
import model.statement.StatementInterface;
import model.type.TypeInterface;
import model.type.Types;

import java.io.IOException;

public class CloseReadFileStatement implements StatementInterface {
    private final ExpressionInterface fileNameExpression;

    public CloseReadFileStatement(ExpressionInterface fileNameExpression) {
        this.fileNameExpression = fileNameExpression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException {
        var fileName = (String) fileNameExpression.evaluate(programState).getValue();
        if (!programState.isFileOpened(fileName)) {
            throw new StatementException("File '%s' is not opened!", fileName);
        }

        try {
            programState.closeFile(fileName);
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

        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("closeReadFile(%s)", fileNameExpression);
    }
}
