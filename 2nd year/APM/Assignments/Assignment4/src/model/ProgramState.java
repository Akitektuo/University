package model;

import container.*;
import model.expression.ExpressionException;
import model.statement.StatementException;
import model.statement.StatementInterface;
import model.value.ReferenceValue;
import model.value.ValueInterface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ProgramState {
    private final StackInterface<StatementInterface> executionStack = new Stack<>();
    private final DictionaryInterface<String, ValueInterface> systemTable = new Dictionary<>();
    private final ListInterface<String> output = new List<>();
    private final DictionaryInterface<String, BufferedReader> fileTable = new Dictionary<>();
    private final MemoryHeap<ValueInterface> memoryHeap = new MemoryHeap<>();

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

    public ValueInterface getVariable(String variableName) {
        return systemTable.get(variableName);
    }

    public ProgramState openFile(String fileName) throws FileNotFoundException {
        fileTable.set(fileName, new BufferedReader(new FileReader(fileName)));

        return this;
    }

    public ProgramState closeFile(String fileName) throws IOException {
        fileTable.remove(fileName).close();

        return this;
    }

    public int allocateInMemory(ValueInterface value) {
        return memoryHeap.set(value);
    }

    public ValueInterface getFromMemory(int address) {
        return memoryHeap.get(address);
    }

    public ProgramState setInMemory(int address, ValueInterface value) {
        memoryHeap.set(address, value);

        return this;
    }

    public boolean isFileOpened(String fileName) {
        return fileTable.hasKey(fileName);
    }

    private ListInterface<Integer> getUsedAddresses() {
        var addresses = new List<Integer>();

        systemTable.getValues()
                .filter(value -> value instanceof ReferenceValue)
                .forEach(value -> addresses.add(((ReferenceValue) value).getRelatedAddresses(memoryHeap)));

        return addresses;
    }

    public boolean collectGarbage() {
        var addressesInUse = getUsedAddresses();

        return memoryHeap.filtered((key, value) -> addressesInUse.contains(key));
    }

    public ProgramState executeNext() throws StatementException, ExpressionException {
        return executionStack.pop().execute(this);
    }

    public boolean canExecute() {
        return !executionStack.isEmpty();
    }

    public ListInterface<String> getOutput() {
        return output;
    }

    public DictionaryInterface<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    @Override
    public String toString() {
        var splitter = "--------------------------------";

        return String.format("\nEXECUTION STACK\n" + splitter +
                        "\n%s\n\nSYSTEM TABLE\n" + splitter +
                        "\n%s\n\nFILE TABLE\n" + splitter +
                        "\n%s\n\nMEMORY HEAP\n" + splitter +
                        "\n%s\n\nOUTPUT\n" + splitter + "\n%s\n",
                executionStack, systemTable, fileTable, memoryHeap, output);
    }
}
