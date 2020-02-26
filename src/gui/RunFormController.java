package gui;

import Model.Exceptions.MyException;
import Model.Collection.Dictionary.MyIDictionary;
import Model.ProgramState;
import Model.Collection.Stack.MyIStack;
import Model.Statement.IStatement;
import Model.Value.StringValue;
import Model.Value.Value;
import Controller.Controller;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class RunFormController implements Initializable {
    private Controller controller;

    @FXML
    private TableView<Map.Entry<Integer, Value>> heapTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, Value>, Integer> heapAddressColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, Value>, Value> heapValueColumn;

    @FXML
    private ListView<String> fileListView;

    @FXML
    private TableView<Map.Entry<String, Value>> symbolTableView;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, String> symbolTableVariableColumn;

    @FXML
    private TableColumn<Map.Entry<String, Value>, Value> symbolTableValueColumn;

    @FXML
    private ListView<Value> outputListView;

    @FXML
    private ListView<Integer> programStateListView;

    @FXML
    private ListView<String> executionStackListView;

    @FXML
    private TextField numberOfProgramStatesTextField;

    @FXML
    private Button executeOneStepButton;

    void setController(Controller controller){
        this.controller = controller;
        populateProgramStateIdentifiers();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        heapAddressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapValueColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));
        symbolTableVariableColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey() + ""));
        symbolTableValueColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));
        programStateListView.setOnMouseClicked(mouseEvent -> changeProgramState(getCurrentProgramState()));
        executeOneStepButton.setOnAction(actionEvent -> executeOneStep());
    }

    private List<Integer> getProgramStateIds(List<ProgramState> programStateList) {
        return programStateList.stream().map(ProgramState::getID).collect(Collectors.toList());
    }


    private void populateProgramStateIdentifiers() {
        List<ProgramState> programStates = controller.getRepo().getProgramList();
        programStateListView.setItems(FXCollections.observableList(getProgramStateIds(programStates)));
        numberOfProgramStatesTextField.setText("" + programStates.size());
    }



    private void executeOneStep() {
        if(controller == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The program was not selected", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        boolean programStateLeft = controller.getRepo().getProgramList().isEmpty();
        if(programStateLeft){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing left to execute", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        try {
            controller.oneStep();
        }
        catch(MyException exception)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK);
            alert.showAndWait();
            return;
        }

        changeProgramState(getCurrentProgramState());
        controller.removeAfterOneStep();
        populateProgramStateIdentifiers();
    }
    private void changeProgramState(ProgramState currentProgramState) {
        if(currentProgramState == null)
        {
            return;
        }
        else {
            populateExecutionStack(currentProgramState);
            populateSymbolTable(currentProgramState);
            populateOutput(currentProgramState);
            populateFileTable(currentProgramState);
            populateHeapTable(currentProgramState);
        }
    }

    private void populateHeapTable(ProgramState currentProgramState) {
        MyIDictionary<Integer, Value> heapTable = currentProgramState.getHeapTable();

        List<Map.Entry<Integer, Value>> heapTableList = new ArrayList<>(heapTable.getValues().entrySet());

        heapTableView.setItems(FXCollections.observableList(heapTableList));
        heapTableView.refresh();
    }

    private void populateFileTable(ProgramState currentProgramState) {
        MyIDictionary<StringValue, BufferedReader> fileTable = currentProgramState.getFileTable();
        List<String> fileList = new ArrayList<>();
        for (Map.Entry<StringValue, BufferedReader> entry : fileTable.getValues().entrySet())
        {

            fileList.add(entry.getKey().toString());
        }
        ObservableList<String> files = FXCollections.observableArrayList(fileList);
        fileListView.setItems(files);
        fileListView.refresh();
    }

    private void populateOutput(ProgramState currentProgramState) {
        ObservableList<Value> output = FXCollections.observableArrayList(currentProgramState.getOutput().getValues());

        outputListView.setItems(output);
        outputListView.refresh();
    }

    private void populateSymbolTable(ProgramState currentProgramState) {
        MyIDictionary<String, Value> symbolTable = currentProgramState.getSymbolTable();

        List<Map.Entry<String, Value>> symbolTableList = new ArrayList<>(symbolTable.getValues().entrySet());
        symbolTableView.setItems(FXCollections.observableList(symbolTableList));
        symbolTableView.refresh();
    }

    private void populateExecutionStack(ProgramState currentProgramState) {
        MyIStack<IStatement> executionStack = currentProgramState.getStack();

        List<String> executionStackList = new ArrayList<>();
        for(IStatement s : executionStack.getStack()){
            executionStackList.add(0,s.toString());
        }
        executionStackListView.setItems(FXCollections.observableList(executionStackList));
        executionStackListView.refresh();
    }

    private ProgramState getCurrentProgramState(){
        if(programStateListView.getSelectionModel().getSelectedIndex() == -1)
            return null;

        int currentId = programStateListView.getSelectionModel().getSelectedItem();
        return controller.getRepo().getProgramStatewithID(currentId);
    }

}