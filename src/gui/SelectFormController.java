package gui;

import Model.Exceptions.MyException;
import Model.Collection.Dictionary.MyDictionary;
import Model.Collection.Dictionary.MyIDictionary;
import Model.Expresions.*;
import Model.ProgramState;
import Model.Statement.*;
import Model.Statement.File.closeFile;
import Model.Statement.File.openFile;
import Model.Statement.File.readFile;
import Model.Statement.Heap.HeapAllocation;
import Model.Statement.Heap.HeapWriting;
import Model.Type.*;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Controller.Controller;
import Repository.InterfaceRepository;
import Repository.Repository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SelectFormController implements Initializable {
    private List<IStatement> programStatements;
    private RunFormController mainWindowController;

    @FXML
    private ListView<String> programListView;

    @FXML
    private Button executeProgram;

    void setMainWindowController(RunFormController mainWindowController){
        this.mainWindowController = mainWindowController;
    }

    private void buildProgramStatements()
    {
        IStatement ex1= new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(20))), new PrintStatement(new
                        VariableExpression("v"))));

        IStatement ex2 = new CompoundStatement( new VariableDeclarationStatement("a",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new
                                ArithmeticExpression('*',new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b",new ArithmeticExpression('+',new VariableExpression("a"), new
                                        ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));

        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new
                                        IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                        VariableExpression("v"))))));

        IStatement ex4 = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()), new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                new CompoundStatement(new openFile(new VariableExpression("varf")),
                        new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
                                new CompoundStatement(new readFile(new VariableExpression("varf"), "varc"),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                new CompoundStatement(new readFile(new VariableExpression("varf"), "varc"),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                new closeFile(new VariableExpression("varf"))))))))));
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

        IStatement ex6 = new CompoundStatement( new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocation("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new PrintStatement(new HeapReading(new VariableExpression("v"))),
                                new CompoundStatement(new HeapWriting("v",new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression('+', new HeapReading(new VariableExpression("v")) ,new ValueExpression(new IntValue(5))))))));

        IStatement ex7 = new CompoundStatement(
                new VariableDeclarationStatement("v",new ReferenceType(new IntType())),
                new CompoundStatement(
                        new HeapAllocation("v",new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new  IntType()))), new CompoundStatement(
                                new HeapAllocation("a",new VariableExpression("v")),new CompoundStatement(
                                new HeapAllocation("v",new ValueExpression(new IntValue(30))),
                                new PrintStatement(new HeapReading(new HeapReading( new VariableExpression("a")))))))));

        IStatement ex8 = new CompoundStatement( new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocation("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new HeapAllocation("a",new VariableExpression("v")),
                                        new CompoundStatement(new HeapWriting("a",new VariableExpression("v")),
                                                new CompoundStatement(new PrintStatement(new HeapReading(new VariableExpression("v"))),
                                                        new PrintStatement(new ArithmeticExpression('+',new HeapReading(new HeapReading(new VariableExpression("a"))),new ValueExpression(new IntValue(5))))))))));
        IStatement ex9 = new CompoundStatement( new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocation("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new HeapAllocation("a",new VariableExpression("v")),
                                        new CompoundStatement(new HeapWriting("a",new VariableExpression("v")),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new VariableExpression("a"))))))));

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

        IStatement exSleep = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(0))),
                        new CompoundStatement(new WhileStatement(
                                new RelationalExpression("<" , new VariableExpression("v"), new ValueExpression(new IntValue(3))),
                                new CompoundStatement(new ForkStatement(
                                        new CompoundStatement(new PrintStatement( new VariableExpression("v")),
                                                new AssignmentStatement("v", new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression( new IntValue(1)))))
                                ),new AssignmentStatement("v", new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression( new IntValue(1))))

                         )), new CompoundStatement(
                                 new SleepStatement(5),
                                new PrintStatement(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression( new IntValue(10))))
                        ))
                                ));

        IStatement exFor = new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocation("a",new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new ForStatement("v", new ValueExpression(new IntValue(0)), new ValueExpression(new IntValue(3)),
                                new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1))),
                                new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignmentStatement("v", new ArithmeticExpression('*',new VariableExpression("v"),
                                                new HeapReading(new VariableExpression("a"))))))),
                                new PrintStatement(new HeapReading(new VariableExpression("a"))))));

        programStatements = new ArrayList<>(Arrays.asList(ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9,ex10,exSleep,exFor));}

    private List<String> getStringRepresentations(){
        return programStatements.stream().map(Object::toString).collect(Collectors.toList());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        buildProgramStatements();
        List<String> strings=getStringRepresentations();
        ObservableList<String>  str= FXCollections.observableList(strings);
        programListView.setItems(str);
        executeProgram.setOnAction(actionEvent -> {
            int index = programListView.getSelectionModel().getSelectedIndex();
            if(index < 0)
                return;
            ProgramState initialProgramState = new ProgramState(programStatements.get(index));
            Repository repository;
            try {
                index+=1;
                MyIDictionary<String, Type> typeEnv = new MyDictionary<>();
                initialProgramState.getStatement().typecheck(typeEnv);
                repository = new Repository(initialProgramState, "log" + index + ".txt");
                Controller ctrl = new Controller(repository);
                mainWindowController.setController(ctrl);
            }
            catch(MyException exception)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK);
                alert.showAndWait();
                return;
            }

        });
    }
}