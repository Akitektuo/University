package utils;

import model.ProgramState;
import model.expression.ArithmeticExpression;
import model.expression.ArithmeticOperator;
import model.expression.ValueExpression;
import model.expression.VariableExpression;
import model.statement.*;
import model.type.BooleanType;
import model.type.NumberType;
import model.value.BooleanValue;
import model.value.IntegerValue;

public class PredefinedProgramStates {
    public static ProgramState PROGRAM_1;
    public static ProgramState PROGRAM_2;
    public static ProgramState PROGRAM_3;

    public static final String VISUAL_PROGRAM_1 = new CodeFormatter("number v;")
            .newLine()
            .addLine("v = 2;")
            .newLine()
            .addLine("print(v);")
            .build();
    
    public static final String VISUAL_PROGRAM_2 = new CodeFormatter("number a;")
            .addLine("number b;")
            .newLine()
            .addLine("a = 2 + 3 * 5;")
            .addLine("b = a + 1;")
            .newLine()
            .addLine("print(b);")
            .build();

    public static final String VISUAL_PROGRAM_3 = new CodeFormatter("boolean a;")
            .addLine("number v;")
            .newLine()
            .addLine("a = 2;")
            .addLine("if (a)")
            .addLine("v = 2;", 2)
            .addLine("else")
            .addLine("v = 3;", 2)
            .newLine()
            .addLine("print(v);")
            .build();

    static {
        try {
            PROGRAM_1 = new ProgramState(new CompoundStatement(
                    new DeclarationStatement("v", new NumberType()),
                    new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                    new PrintStatement(new VariableExpression("v"))
            ));

            PROGRAM_2 = new ProgramState(new CompoundStatement(
                    new DeclarationStatement("a", new NumberType()),
                    new DeclarationStatement("b", new NumberType()),
                    new AssignmentStatement("a", new ArithmeticExpression(
                            new ValueExpression(new IntegerValue(2)),
                            ArithmeticOperator.ADDITION,
                            new ArithmeticExpression(
                                    new ValueExpression(new IntegerValue(3)),
                                    ArithmeticOperator.MULTIPLICATION,
                                    new ValueExpression(new IntegerValue(5))
                            )
                    )),
                    new AssignmentStatement("b", new ArithmeticExpression(
                            new VariableExpression("a"),
                            ArithmeticOperator.ADDITION,
                            new ValueExpression(new IntegerValue(1))
                    )),
                    new PrintStatement(new VariableExpression("b"))
            ));

            PROGRAM_3 = new ProgramState(new CompoundStatement(
                    new DeclarationStatement("a", new BooleanType()),
                    new DeclarationStatement("v", new NumberType()),
                    new AssignmentStatement("a", new ValueExpression(new BooleanValue(true))),
                    new IfStatement(
                            new VariableExpression("a"),
                            new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                            new AssignmentStatement("v", new ValueExpression(new IntegerValue(3)))
                    ),
                    new PrintStatement(new VariableExpression("v"))
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
