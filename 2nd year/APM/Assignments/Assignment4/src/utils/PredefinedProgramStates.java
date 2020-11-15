package utils;

import model.ProgramState;
import model.expression.ValueExpression;
import model.expression.VariableExpression;
import model.expression.binary.AddBinaryExpression;
import model.expression.binary.MultiplyBinaryExpression;
import model.statement.*;
import model.statement.file.CloseReadFileStatement;
import model.statement.file.OpenReadFileStatement;
import model.statement.file.ReadFileStatement;
import model.type.BooleanType;
import model.type.NumberType;
import model.type.StringType;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.StringValue;

public class PredefinedProgramStates {
    public static ProgramState PROGRAM_1;
    public static ProgramState PROGRAM_2;
    public static ProgramState PROGRAM_3;
    public static ProgramState PROGRAM_4;

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

    public static final String VISUAL_PROGRAM_4 = new CodeFormatter("string varf;")
            .addLine("varf = \"test.in\";")
            .addLine("openReadFile(varf);")
            .newLine()
            .addLine("number varc;")
            .newLine()
            .addLine("readFile(varf, varc);")
            .addLine("print(varc);")
            .newLine()
            .addLine("readFile(varf, varc);")
            .addLine("print(varc);")
            .newLine()
            .addLine("closeReadFile(varf);")
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
                    new AssignmentStatement("a", new AddBinaryExpression(
                            new ValueExpression(new IntegerValue(2)),
                            new MultiplyBinaryExpression(
                                    new ValueExpression(new IntegerValue(3)),
                                    new ValueExpression(new IntegerValue(5))
                            )
                    )),
                    new AssignmentStatement("b", new AddBinaryExpression(
                            new VariableExpression("a"),
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

            PROGRAM_4 = new ProgramState(new CompoundStatement(
                    new DeclarationStatement("varf", new StringType()),
                    new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                    new OpenReadFileStatement(new VariableExpression("varf")),
                    new DeclarationStatement("varc", new NumberType()),
                    new ReadFileStatement(new VariableExpression("varf"), "varc"),
                    new PrintStatement(new VariableExpression("varc")),
                    new ReadFileStatement(new VariableExpression("varf"), "varc"),
                    new PrintStatement(new VariableExpression("varc")),
                    new CloseReadFileStatement(new VariableExpression("varf"))
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
