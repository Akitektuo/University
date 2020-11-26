package utils;

import com.sun.jdi.IntegerType;
import model.ProgramState;
import model.expression.ReadHeapExpression;
import model.expression.ValueExpression;
import model.expression.VariableExpression;
import model.expression.binary.AddBinaryExpression;
import model.expression.binary.GreaterBinaryExpression;
import model.expression.binary.MultiplyBinaryExpression;
import model.expression.binary.SubtractBinaryExpression;
import model.statement.*;
import model.statement.file.CloseReadFileStatement;
import model.statement.file.OpenReadFileStatement;
import model.statement.file.ReadFileStatement;
import model.statement.heap.AllocateHeapStatement;
import model.statement.heap.WriteHeapStatement;
import model.type.BooleanType;
import model.type.NumberType;
import model.type.ReferenceType;
import model.type.StringType;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.StringValue;

public class PredefinedProgramStates {
    public static ProgramState PROGRAM_1;
    public static ProgramState PROGRAM_2;
    public static ProgramState PROGRAM_3;
    public static ProgramState PROGRAM_4;
    public static ProgramState PROGRAM_5;
    public static ProgramState PROGRAM_6;
    public static ProgramState PROGRAM_7;
    public static ProgramState PROGRAM_8;
    public static ProgramState PROGRAM_9;

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

    public static final String VISUAL_PROGRAM_5 = new CodeFormatter("reference<number> v;")
            .addLine("new(v, 20);")
            .newLine()
            .addLine("reference<reference<number>> a;")
            .addLine("new(a, v);")
            .newLine()
            .addLine("print(v);")
            .addLine("print(a);")
            .build();

    public static final String VISUAL_PROGRAM_6 = new CodeFormatter("reference<number> v;")
            .addLine("new(v, 20);")
            .newLine()
            .addLine("reference<reference<number>> a;")
            .addLine("new(a, v);")
            .newLine()
            .addLine("print(readHeap(v));")
            .addLine("print(readHeap(readHeap(a)) + 5);")
            .build();

    public static final String VISUAL_PROGRAM_7 = new CodeFormatter("reference<number> v;")
            .addLine("new(v, 20);")
            .newLine()
            .addLine("reference<reference<number>> a;")
            .addLine("new(a, v);")
            .newLine()
            .addLine("writeHeap(v, 30);")
            .addLine("print(readHeap(readHeap(a)));")
            .build();


    public static final String VISUAL_PROGRAM_8 = new CodeFormatter("reference<number> v;")
            .addLine("new(v, 20);")
            .addLine("print(readHeap(v));")
            .newLine()
            .addLine("new(v, 30);")
            .addLine("print(readHeap(v));")
            .build();

    public static final String VISUAL_PROGRAM_9 = new CodeFormatter("number v;")
            .addLine("v = 4;")
            .newLine()
            .addLine("while (v > 0) do")
            .addLine("print(v)", 2)
            .addLine("v = v - 1", 2)
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

            PROGRAM_5 = new ProgramState(new CompoundStatement(
                    new DeclarationStatement("v", new ReferenceType(new NumberType())),
                    new AllocateHeapStatement("v", new ValueExpression(new IntegerValue(20))),
                    new DeclarationStatement("a", new ReferenceType(new ReferenceType(new NumberType()))),
                    new AllocateHeapStatement("a", new VariableExpression("v")),
                    new PrintStatement(new VariableExpression("v")),
                    new PrintStatement(new VariableExpression("a"))
            ));

            PROGRAM_6 = new ProgramState(new CompoundStatement(
                    new DeclarationStatement("v", new ReferenceType(new NumberType())),
                    new AllocateHeapStatement("v", new ValueExpression(new IntegerValue(20))),
                    new DeclarationStatement("a", new ReferenceType(new ReferenceType(new NumberType()))),
                    new AllocateHeapStatement("a", new VariableExpression("v")),
                    new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                    new PrintStatement(new AddBinaryExpression(
                            new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))),
                            new ValueExpression(new IntegerValue(5))
                    ))
            ));

            PROGRAM_7 = new ProgramState(new CompoundStatement(
                    new DeclarationStatement("v", new ReferenceType(new NumberType())),
                    new AllocateHeapStatement("v", new ValueExpression(new IntegerValue(20))),
                    new DeclarationStatement("a", new ReferenceType(new ReferenceType(new NumberType()))),
                    new AllocateHeapStatement("a", new VariableExpression("v")),
                    new WriteHeapStatement("v", new ValueExpression(new IntegerValue(30))),
                    new PrintStatement(
                            new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a")))
                    )
            ));

            PROGRAM_8 = new ProgramState(new CompoundStatement(
                    new DeclarationStatement("v", new ReferenceType(new NumberType())),
                    new AllocateHeapStatement("v", new ValueExpression(new IntegerValue(20))),
                    new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                    new AllocateHeapStatement("v", new ValueExpression(new IntegerValue(30))),
                    new PrintStatement(new ReadHeapExpression(new VariableExpression("v")))
            ));

            PROGRAM_9 = new ProgramState(new CompoundStatement(
                    new DeclarationStatement("v", new NumberType()),
                    new AssignmentStatement("v", new ValueExpression(new IntegerValue(4))),
                    new WhileStatement(
                            new GreaterBinaryExpression(
                                    new VariableExpression("v"),
                                    new ValueExpression(new IntegerValue(0))
                            ),
                            new CompoundStatement(
                                    new PrintStatement(new VariableExpression("v")),
                                    new AssignmentStatement("v", new SubtractBinaryExpression(
                                            new VariableExpression("v"),
                                            new ValueExpression(new IntegerValue(1))
                                    ))
                            )
                    ),
                    new PrintStatement(new VariableExpression("v"))
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
