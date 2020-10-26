package model;

import container.*;
import model.expression.ExpressionException;
import model.statement.StatementException;
import model.statement.StatementInterface;
import model.value.ValueInterface;

public class ProgramState {
    private final StackInterface<StatementInterface> executionStack = new Stack<>();
    private final DictionaryInterface<String, ValueInterface> systemTable = new Dictionary<>();
    private final ListInterface<String> output = new List<>();

    public ProgramState(StatementInterface initialStatement) {
        executionStack.push(initialStatement);
    }

    public ProgramState pushStatement(StatementInterface statement) {
        executionStack.push(statement);

        return this;
    }

    public ProgramState addOutput(String output) {
        this.output.add(output);

        return this;
    }

    public ProgramState setVariable(String variableName, ValueInterface variableValue) {
        systemTable.set(variableName, variableValue);

        return this;
    }

    public ProgramState executeNext() throws StatementException, ExpressionException {
        return executionStack.pop().execute(this);
    }

    public boolean canExecute() {
        return !executionStack.isEmpty();
    }

    public DictionaryInterface<String, ValueInterface> getSystemTable() {
        return systemTable;
    }

    public ListInterface<String> getOutput() {
        return output;
    }

    @Override
    public String toString() {
        var splitter = "--------------------------------";

        return String.format("\nEXECUTION STACK\n" + splitter +
                "\n%s\n\nSYSTEM TABLE\n" + splitter +
                "\n%s\n\nOUTPUT\n" + splitter + "\n%s\n",
                executionStack, systemTable, output);
    }
}
