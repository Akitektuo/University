package utils;

import container.List;
import container.ListInterface;
import model.ProgramState;
import model.expression.ReadHeapExpression;
import model.expression.ValueExpression;
import model.expression.VariableExpression;
import model.expression.binary.*;
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
    public static final ListInterface<String> VISUAL_PROGRAMS = new List<>(
            // 1.
            new CodeFormatter("reference<number> a;")
                    .addLine("reference<number> b;")
                    .addLine("number v;")
                    .newLine()
                    .addLine("new(a, 0);")
                    .addLine("new(b, 0);")
                    .newLine()
                    .addLine("writeHeap(a, 1);")
                    .addLine("writeHeap(b, 2);")
                    .newLine()
                    .addLine("v = readHeap(a) < readHeap(b) ? 100 : 200;")
                    .addLine("print(v);")
                    .newLine()
                    .addLine("v = readHeap(b) - 2 > readHeap(a) ? 100 : 200;")
                    .addLine("print(v);")
                    .build(),
            // 2.
            new CodeFormatter("number a;")
                    .addLine("number b;")
                    .newLine()
                    .addLine("a = 2 + 3 * 5;")
                    .addLine("b = a + 1;")
                    .newLine()
                    .addLine("print(b);")
                    .build(),
            // 3.
            new CodeFormatter("boolean a;")
                    .addLine("number v;")
                    .newLine()
                    .addLine("a = 2;")
                    .addLine("if (a)")
                    .addLine("v = 2;", 2)
                    .addLine("else")
                    .addLine("v = 3;", 2)
                    .newLine()
                    .addLine("print(v);")
                    .build(),
            // 4.
            new CodeFormatter("string varf;")
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
                    .build(),
            // 5.
            new CodeFormatter("reference<number> v;")
                    .addLine("new(v, 20);")
                    .newLine()
                    .addLine("reference<reference<number>> a;")
                    .addLine("new(a, v);")
                    .newLine()
                    .addLine("print(v);")
                    .addLine("print(a);")
                    .build(),
            // 6.
            new CodeFormatter("reference<number> v;")
                    .addLine("new(v, 20);")
                    .newLine()
                    .addLine("reference<reference<number>> a;")
                    .addLine("new(a, v);")
                    .newLine()
                    .addLine("print(readHeap(v));")
                    .addLine("print(readHeap(readHeap(a)) + 5);")
                    .build(),
            // 7.
            new CodeFormatter("reference<number> v;")
                    .addLine("new(v, 20);")
                    .newLine()
                    .addLine("reference<reference<number>> a;")
                    .addLine("new(a, v);")
                    .newLine()
                    .addLine("writeHeap(v, 30);")
                    .addLine("print(readHeap(readHeap(a)));")
                    .build(),
            // 8.
            new CodeFormatter("reference<number> v;")
                    .addLine("new(v, 20);")
                    .addLine("print(readHeap(v));")
                    .newLine()
                    .addLine("new(v, 30);")
                    .addLine("print(readHeap(v));")
                    .build(),
            // 9.
            new CodeFormatter("number v;")
                    .addLine("v = 4;")
                    .newLine()
                    .addLine("while (v > 0) do")
                    .addLine("print(v)", 2)
                    .addLine("v = v - 1", 2)
                    .newLine()
                    .addLine("print(v);")
                    .build(),
            // 10.
            new CodeFormatter("number v;")
                    .addLine("reference<number> a;")
                    .addLine("v = 10;")
                    .addLine("new(a, 22);")
                    .newLine()
                    .addLine("fork {")
                    .addLine("writeHeap(a, 30);", 2)
                    .addLine("v = 32;", 2)
                    .addLine("print(v);", 2)
                    .addLine("print(readHeap(a));", 2)
                    .addLine("}")
                    .newLine()
                    .addLine("print(v);")
                    .addLine("print(readHeap(a));")
                    .build()
    );

    public static ListInterface<ProgramState> PROGRAMS = new List<>(
            // 1.
            new ProgramState(new CompoundStatement(
                    new DeclarationStatement("a", new ReferenceType(new NumberType())),
                    new DeclarationStatement("b", new ReferenceType(new NumberType())),
                    new DeclarationStatement("v", new NumberType()),

                    new AllocateHeapStatement("a", new ValueExpression(new IntegerValue(0))),
                    new AllocateHeapStatement("b", new ValueExpression(new IntegerValue(0))),

                    new WriteHeapStatement("a", new ValueExpression(new IntegerValue(1))),
                    new WriteHeapStatement("b", new ValueExpression(new IntegerValue(2))),

                    new ConditionalAssignmentStatement("v",
                            new LessBinaryExpression(
                                    new ReadHeapExpression(new VariableExpression("a")),
                                    new ReadHeapExpression(new VariableExpression("b"))
                            ),
                            new ValueExpression(new IntegerValue(100)),
                            new ValueExpression(new IntegerValue(200))),
                    new PrintStatement(new VariableExpression("v")),

                    new ConditionalAssignmentStatement("v",
                            new GreaterBinaryExpression(
                                    new SubtractBinaryExpression(
                                            new ReadHeapExpression(new VariableExpression("b")),
                                            new ValueExpression(new IntegerValue(2))
                                    ),
                                    new ReadHeapExpression(new VariableExpression("a"))
                            ),
                            new ValueExpression(new IntegerValue(100)),
                            new ValueExpression(new IntegerValue(200))),
                    new PrintStatement(new VariableExpression("v"))
            )),
            // 2.
            new ProgramState(new CompoundStatement(
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
            )),
            // 3.
            new ProgramState(new CompoundStatement(
                    new DeclarationStatement("a", new BooleanType()),
                    new DeclarationStatement("v", new NumberType()),
                    new AssignmentStatement("a", new ValueExpression(new BooleanValue(true))),
                    new IfStatement(
                            new VariableExpression("a"),
                            new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                            new AssignmentStatement("v", new ValueExpression(new IntegerValue(3)))
                    ),
                    new PrintStatement(new VariableExpression("v"))
            )),
            // 4.
            new ProgramState(new CompoundStatement(
                    new DeclarationStatement("varf", new StringType()),
                    new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                    new OpenReadFileStatement(new VariableExpression("varf")),
                    new DeclarationStatement("varc", new NumberType()),
                    new ReadFileStatement(new VariableExpression("varf"), "varc"),
                    new PrintStatement(new VariableExpression("varc")),
                    new ReadFileStatement(new VariableExpression("varf"), "varc"),
                    new PrintStatement(new VariableExpression("varc")),
                    new CloseReadFileStatement(new VariableExpression("varf"))
            )),
            // 5.
            new ProgramState(new CompoundStatement(
                    new DeclarationStatement("v", new ReferenceType(new NumberType())),
                    new AllocateHeapStatement("v", new ValueExpression(new IntegerValue(20))),
                    new DeclarationStatement("a", new ReferenceType(new ReferenceType(new NumberType()))),
                    new AllocateHeapStatement("a", new VariableExpression("v")),
                    new PrintStatement(new VariableExpression("v")),
                    new PrintStatement(new VariableExpression("a"))
            )),
            // 6.
            new ProgramState(new CompoundStatement(
                    new DeclarationStatement("v", new ReferenceType(new NumberType())),
                    new AllocateHeapStatement("v", new ValueExpression(new IntegerValue(20))),
                    new DeclarationStatement("a", new ReferenceType(new ReferenceType(new NumberType()))),
                    new AllocateHeapStatement("a", new VariableExpression("v")),
                    new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                    new PrintStatement(new AddBinaryExpression(
                            new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))),
                            new ValueExpression(new IntegerValue(5))
                    ))
            )),
            // 7.
            new ProgramState(new CompoundStatement(
                    new DeclarationStatement("v", new ReferenceType(new NumberType())),
                    new AllocateHeapStatement("v", new ValueExpression(new IntegerValue(20))),
                    new DeclarationStatement("a", new ReferenceType(new ReferenceType(new NumberType()))),
                    new AllocateHeapStatement("a", new VariableExpression("v")),
                    new WriteHeapStatement("v", new ValueExpression(new IntegerValue(30))),
                    new PrintStatement(
                            new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a")))
                    )
            )),
            // 8.
            new ProgramState(new CompoundStatement(
                    new DeclarationStatement("v", new ReferenceType(new NumberType())),
                    new AllocateHeapStatement("v", new ValueExpression(new IntegerValue(20))),
                    new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                    new AllocateHeapStatement("v", new ValueExpression(new IntegerValue(30))),
                    new PrintStatement(new ReadHeapExpression(new VariableExpression("v")))
            )),
            // 9.
            new ProgramState(new CompoundStatement(
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
            )),
            // 10.
            new ProgramState(new CompoundStatement(
                    new DeclarationStatement("v", new NumberType()),
                    new DeclarationStatement("a", new ReferenceType(new NumberType())),
                    new AssignmentStatement("v", new ValueExpression(new IntegerValue(10))),
                    new AllocateHeapStatement("a", new ValueExpression(new IntegerValue(22))),
                    new ForkStatement(new CompoundStatement(
                            new WriteHeapStatement("a", new ValueExpression(new IntegerValue(30))),
                            new AssignmentStatement("v", new ValueExpression(new IntegerValue(32))),
                            new PrintStatement(new VariableExpression("v")),
                            new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                    )),
                    new PrintStatement(new VariableExpression("v")),
                    new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
            ))
    );
}
