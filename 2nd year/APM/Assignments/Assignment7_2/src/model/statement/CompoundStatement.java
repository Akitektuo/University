package model.statement;

import container.DictionaryInterface;
import model.ProgramState;
import model.expression.ExpressionException;
import model.type.TypeInterface;

import java.util.Arrays;

public class CompoundStatement implements StatementInterface {
    private final StatementInterface start, end;

    public CompoundStatement(StatementInterface... statements) throws StatementException {
        if (statements.length < 1) {
            throw new StatementException("A compound statement can only be formed from at least 2 statements");
        }

        this.start = statements[0];

        if (statements.length == 1) {
            this.end = new NoOperationStatement();
        } else {
            this.end = new CompoundStatement(Arrays.stream(statements).skip(1).toArray(StatementInterface[]::new));
        }
    }

    @Override
    public ProgramState execute(ProgramState programState) {
        programState.pushStatement(end)
                .pushStatement(start);

        return null;
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws StatementException, ExpressionException {
        return end.typeCheck(start.typeCheck(typeTable));
    }

    @Override
    public String toString() {
        return String.format("CompoundStatement(%s, %s)", start, end);
    }
}
