package model;

import container.*;
import model.expression.ExpressionException;
import model.statement.StatementException;
import model.statement.StatementInterface;
import model.value.ValueInterface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class ProgramState {
    private static final ListInterface<Integer> usedIds = new List<>();

    private final StackInterface<StatementInterface> executionStack;
    private final DictionaryInterface<String, ValueInterface> systemTable;
    private final ListInterface<String> output;
    private final DictionaryInterface<String, BufferedReader> fileTable;
    private final IdDictionary<ValueInterface> memoryHeap;
    private final IdDictionary<ValueInterface> latchTable;
    private final int id = generateNewId();

    public ProgramState(StatementInterface initialStatement) {
        executionStack = new Stack<>(initialStatement);
        systemTable = new Dictionary<>();
        output = new List<>();
        fileTable = new Dictionary<>();
        memoryHeap = new IdDictionary<>();
        latchTable = new IdDictionary<>();
    }

    public ProgramState(StatementInterface initialStatement, ProgramState programStateToCopy) {
        executionStack = new Stack<>(initialStatement);
        systemTable = programStateToCopy.systemTable.clone();
        output = programStateToCopy.output;
        fileTable = programStateToCopy.fileTable.clone();
        memoryHeap = programStateToCopy.memoryHeap;
        latchTable = programStateToCopy.latchTable;
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

    public int defineLatch(ValueInterface value) {
        return latchTable.set(value);
    }

    public ValueInterface getLatch(int address)
    {
        return latchTable.get(address);
    }

    public ProgramState setLatch(int address, ValueInterface value)
    {
        latchTable.set(address, value);

        return this;
    }

    public boolean isFileOpened(String fileName) {
        return fileTable.hasKey(fileName);
    }

    public ProgramState executeNext() throws StatementException, ExpressionException {
        return executionStack.pop().execute(this);
    }

    public boolean isCompleted() {
        return executionStack.isEmpty();
    }

    public DictionaryInterface<String, ValueInterface> getSystemTable() {
        return systemTable;
    }

    public ListInterface<ValueInterface> getSystemTableValues() {
        return systemTable.getValues();
    }

    public StackInterface<StatementInterface> getExecutionStack() {
        return executionStack;
    }

    public IdDictionary<ValueInterface> getMemoryHeap() {
        return memoryHeap;
    }

    public IdDictionary<ValueInterface> getLatchTable() {
        return latchTable;
    }

    public ListInterface<String> getOutput() {
        return output;
    }

    public DictionaryInterface<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public ProgramState oneStep() {
        if (isCompleted()) {
            throw new StatementException("Nothing left to execute on program state with id %s", id);
        }
        return executeNext();
    }

    public void checkTypes() {
        executionStack.first().typeCheck(new Dictionary<>());
    }

    public Integer getId() {
        return id;
    }

    private int generateNewId() {
        var randomGenerator = new Random();

        var newId = randomGenerator.nextInt();
        while (usedIds.contains(newId)) {
            newId = randomGenerator.nextInt();
        }

        return usedIds.add(newId);
    }

    @Override
    public String toString() {
        var splitter = "--------------------------------";

        return String.format("\nID: %s\n\nEXECUTION STACK\n" + splitter +
                        "\n%s\n\nSYSTEM TABLE\n" + splitter +
                        "\n%s\n\nFILE TABLE\n" + splitter +
                        "\n%s\n\nMEMORY HEAP\n" + splitter +
                        "\n%s\n\nLATCH TABLE\n" + splitter +
                        "\n%s\n\nOUTPUT\n" + splitter + "\n%s\n",
                id, executionStack, systemTable, fileTable, memoryHeap, latchTable, output);
    }
}
