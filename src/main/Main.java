package main;

import Controller.Controller;
import Model.Collection.Dictionary.MyDictionary;
import Model.Collection.Dictionary.MyIDictionary;
import Model.Exceptions.MyException;
import Model.Expresions.*;
import Model.ProgramState;
import Model.Statement.*;
import Model.Statement.Heap.HeapAllocation;
import Model.Statement.Heap.HeapWriting;
import Model.Type.*;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.Repository;
import Repository.InterfaceRepository;
import Model.Statement.File.*;
import View.TextMenu;
import View.ExitCommand;
import View.RunExample;

import javax.management.ValueExp;
import java.time.chrono.IsoChronology;
import java.util.Collection;

public class Main {
    public static void main(String[] args) throws MyException {

        IStatement ex1= new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(50))), new PrintStatement(new
                        VariableExpression("v"))));

        MyIDictionary<String, Type> typeEnv1 = new MyDictionary<>();
        ex1.typecheck(typeEnv1);

        ProgramState prg = new ProgramState(ex1);
        InterfaceRepository repo1 = new Repository(prg, "log1.txt");
        Controller ctrl1 = new Controller(repo1);

        IStatement ex2 = new CompoundStatement( new VariableDeclarationStatement("a",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new
                                ArithmeticExpression('*',new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b",new ArithmeticExpression('+',new VariableExpression("a"), new
                                        ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));

        MyIDictionary<String, Type> typeEnv2 = new MyDictionary<>();
        ex2.typecheck(typeEnv2);

        ProgramState prg2 = new ProgramState(ex2);
        InterfaceRepository repo2 = new Repository(prg2, "log2.txt");
        Controller ctrl2 = new Controller(repo2);

        //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new
                                        IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                        VariableExpression("v"))))));
        MyIDictionary<String, Type> typeEnv3 = new MyDictionary<>();
        ex3.typecheck(typeEnv3);

        ProgramState prg3 = new ProgramState(ex3);
        InterfaceRepository repo3 = new Repository(prg3, "log3.txt");
        Controller ctrl3 = new Controller(repo3);

        IStatement ex4 = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()), new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                new CompoundStatement(new openFile(new VariableExpression("varf")),
                        new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
                                new CompoundStatement(new readFile(new VariableExpression("varf"), "varc"),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                new CompoundStatement(new readFile(new VariableExpression("varf"), "varc"),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                new closeFile(new VariableExpression("varf"))))))))));


        MyIDictionary<String, Type> typeEnv4 = new MyDictionary<>();
        ex4.typecheck(typeEnv4);
        ProgramState prg4 = new ProgramState(ex4);
        InterfaceRepository repo4 = new Repository(prg4, "log4.txt");
        Controller ctrl4 = new Controller(repo4);

        IStatement ex5 = new CompoundStatement(
                new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v",new ValueExpression(new IntValue(4))),
                        new WhileStatement(
                                new RelationalExpression(">",new VariableExpression("v"),new ValueExpression(new IntValue(0))),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignmentStatement( "v",new ArithmeticExpression('-',new VariableExpression("v"),new ValueExpression(new IntValue(1))))
                                )
                        )));

        MyIDictionary<String, Type> typeEnv5 = new MyDictionary<>();
        ex5.typecheck(typeEnv5);
        ProgramState prg5 = new ProgramState(ex5 );
        Repository repo5= new Repository(prg5, "log5.txt");
        Controller ctrl5 = new Controller(repo5);

        IStatement ex6 = new CompoundStatement( new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocation("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new PrintStatement(new HeapReading(new VariableExpression("v"))),
                                new CompoundStatement(new HeapWriting("v",new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression('+', new HeapReading(new VariableExpression("v")) ,new ValueExpression(new IntValue(5))))))));
        MyIDictionary<String, Type> typeEnv6 = new MyDictionary<>();
        ex6.typecheck(typeEnv6);

        ProgramState prg6 = new ProgramState(ex6);
        Repository repo6= new Repository(prg6, "log6.txt");
        Controller ctrl6 = new Controller(repo6);

        IStatement ex7 = new CompoundStatement(
                new VariableDeclarationStatement("v",new ReferenceType(new IntType())),
                new CompoundStatement(
                        new HeapAllocation("v",new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new  IntType()))), new CompoundStatement(
                                new HeapAllocation("a",new VariableExpression("v")),new CompoundStatement(
                                new HeapAllocation("v",new ValueExpression(new IntValue(30))),
                                new PrintStatement(new HeapReading(new HeapReading( new VariableExpression("a")))))))));
        MyIDictionary<String, Type> typeEnv7 = new MyDictionary<>();
        ex7.typecheck(typeEnv7);
        ProgramState prg7 = new ProgramState(ex7);
        Repository repo7= new Repository(prg7, "log7.txt");
        Controller ctrl7 = new Controller(repo7);

        IStatement ex8 = new CompoundStatement( new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocation("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new HeapAllocation("a",new VariableExpression("v")),
                                        new CompoundStatement(new HeapWriting("a",new VariableExpression("v")),
                                                new CompoundStatement(new PrintStatement(new HeapReading(new VariableExpression("v"))),
                                                        new PrintStatement(new ArithmeticExpression('+',new HeapReading(new HeapReading(new VariableExpression("a"))),new ValueExpression(new IntValue(5))))))))));
        MyIDictionary<String, Type> typeEnv8 = new MyDictionary<>();
        ex8.typecheck(typeEnv8);
        ProgramState prg8 = new ProgramState(ex8);
        Repository repo8= new Repository(prg8, "log8.txt");
        Controller ctrl8 = new Controller(repo8);

        IStatement ex9 = new CompoundStatement( new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocation("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new HeapAllocation("a",new VariableExpression("v")),
                                        new CompoundStatement(new HeapWriting("a",new VariableExpression("v")),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new VariableExpression("a"))))))));
        MyIDictionary<String, Type> typeEnv9 = new MyDictionary<>();
        ex9.typecheck(typeEnv9);
        ProgramState prg9 = new ProgramState(ex9);
        Repository repo9= new Repository(prg9, "log9.txt");
        Controller ctrl9 = new Controller(repo9);

        IStatement ex10 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new IntType())),
                        new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new HeapAllocation("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new HeapWriting("a", new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                                new PrintStatement(new HeapReading(new VariableExpression("a"))))))),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new HeapReading(new VariableExpression("a")))))))));
        MyIDictionary<String, Type> typeEnv10 = new MyDictionary<>();
        ex10.typecheck(typeEnv10);
        ProgramState prg10 = new ProgramState(ex10);
        Repository repo10= new Repository(prg10, "log0.txt");
        Controller ctrl10 = new Controller(repo10);

        IStatement exFor = new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocation("a",new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new ForStatement("v", new ValueExpression(new IntValue(0)), new ValueExpression(new IntValue(3)),
                                new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1))),
                                new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignmentStatement("v", new ArithmeticExpression('*',new VariableExpression("v"),
                                                new HeapReading(new VariableExpression("a"))))))),
                                new PrintStatement(new HeapReading(new VariableExpression("a"))))));

        IStatement exLock = new CompoundStatement(new VariableDeclarationStatement("v1", new ReferenceType(new IntType())),
                new CompoundStatement(new VariableDeclarationStatement("v2", new ReferenceType(new IntType())),
                        new CompoundStatement(new VariableDeclarationStatement("v3",new ReferenceType(new IntType())),
                                new CompoundStatement(new VariableDeclarationStatement("cnt",new IntType()),
                                        new CompoundStatement(new HeapAllocation("v1",new ValueExpression(new IntValue(2))),
                                                new CompoundStatement(new HeapAllocation("v2", new ValueExpression(new IntValue(3))),
                                                        new CompoundStatement(new HeapAllocation("v3", new ValueExpression(new IntValue(4))),
                                                                new CompoundStatement(new NewBarrier("cnt", new HeapReading(new VariableExpression("v2"))),
                                                                        new CompoundStatement(new ForkStatement(
                                                                                new CompoundStatement(new Await("cnt"),
                                                                                        new CompoundStatement(new HeapWriting("v1", new ArithmeticExpression('*', new HeapReading(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))),
                                                                                                new PrintStatement(new HeapReading(new VariableExpression("v1")))))),
                                                                                new CompoundStatement(new ForkStatement(
                                                                                        new CompoundStatement(new Await("cnt"),
                                                                                                new CompoundStatement(new HeapWriting("v2", new ArithmeticExpression('*',new HeapReading(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                                                                                                        new CompoundStatement(new HeapWriting("v2", new ArithmeticExpression('*', new HeapReading(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                                                                                                                new PrintStatement(new HeapReading(new VariableExpression("v2"))))))),
                                                                                        new CompoundStatement(new Await("cnt"),
                                                                                                new PrintStatement(new HeapReading(new VariableExpression("v3"))))))))))))));


        MyIDictionary<String, Type> typeEnv12 = new MyDictionary<>();
        ex10.typecheck(typeEnv12);
        ProgramState prg12 = new ProgramState(exFor);
        Repository repo12= new Repository(prg12, "log12.txt");
        Controller ctrl12 = new Controller(repo12);

        MyIDictionary<String, Type> typeEnv13 = new MyDictionary<>();
        ex10.typecheck(typeEnv13);
        ProgramState prg13 = new ProgramState(exLock);
        Repository repo13= new Repository(prg13, "log13.txt");
        Controller ctrl13 = new Controller(repo13);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",ex1.toString(),ctrl1));
        menu.addCommand(new RunExample("2",ex2.toString(),ctrl2));
        menu.addCommand(new RunExample("3",ex3.toString(),ctrl3));
        menu.addCommand(new RunExample("4",ex4.toString(),ctrl4));
        menu.addCommand(new RunExample("5",ex5.toString(),ctrl5));
        menu.addCommand(new RunExample("6",ex6.toString(),ctrl6));
        menu.addCommand(new RunExample("7",ex7.toString(),ctrl7));
        menu.addCommand(new RunExample("8",ex8.toString(),ctrl8));
        menu.addCommand(new RunExample("9",ex9.toString(),ctrl9));
        menu.addCommand(new RunExample("10",ex10.toString(),ctrl10));
        menu.addCommand(new RunExample("12",exFor.toString(),ctrl12));
        menu.addCommand(new RunExample("13",exLock.toString(),ctrl13));

        menu.show();
    }
}
