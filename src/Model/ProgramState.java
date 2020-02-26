package Model;

import Model.Collection.Dictionary.MyDictionary;
import Model.Collection.List.MyList;
import Model.Collection.Stack.MyStack;
import Model.Collection.Dictionary.MyIDictionary;
import Model.Collection.List.MyIList;
import Model.Collection.Stack.MyIStack;
import Model.Exceptions.MyException;
import Model.Statement.Heap.MyHeap;
import Model.Statement.Heap.MyIHeap;
import Model.Statement.IStatement;
import Model.Value.Value;
import Model.Value.StringValue;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.time.chrono.IsoChronology;
import java.util.ArrayList;

public class ProgramState {
    private MyIStack<IStatement> executionStack;
    private MyIDictionary<String, Value> symbolTable;
    private MyIList<Value> output;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIHeap<Integer, Value> heapTable;
    private IStatement originalProgram;

    ///for practic------------------------------------------------
    private MyIHeap<Integer, Pair<Integer, ArrayList<Integer>>> barrierTable;
    public MyIHeap<Integer, Pair<Integer, ArrayList<Integer>>> getBarrierTable() {
        return barrierTable;
    }

    public MyIStack<IStatement> getStack(){
        return executionStack;
    }
    public MyIDictionary<String, Value> getSymbolTable(){
        return symbolTable;
    }
    public MyIList<Value> getOutput(){
        return output;
    }
    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }
    public MyIHeap<Integer, Value> getHeapTable() {
        return heapTable;
    }
    private int prgStateId;
    private static int lastID;

    public ProgramState(MyIStack<IStatement> stk,
                        MyIDictionary<String, Value> symbol,
                        MyIList<Value> ot,
                        MyIHeap<Integer, Value> heap,
                        MyIDictionary<StringValue, BufferedReader> fileTablee,
                        MyIHeap<Integer, Pair<Integer, ArrayList<Integer>>> barriertable,
                        IStatement statement

                        ){
        executionStack=stk;
        symbolTable=symbol;
        output=ot;
        heapTable = heap;
        fileTable = fileTablee;
        barrierTable = barriertable;
        this.executionStack.push(statement);
        prgStateId = getnewID();

    }

    public int getID()
    {
        return prgStateId;
    }

    public ProgramState(IStatement originalProgram){
        executionStack = new MyStack<>();
        symbolTable = new MyDictionary<>();
        output = new MyList<>();
        fileTable = new MyDictionary<>();
        heapTable =  new MyHeap<>();
        barrierTable = new MyHeap<>();
        this.originalProgram = originalProgram;
        prgStateId = getnewID();
        executionStack.push(originalProgram);
    }

    public void setHeapTable(MyIHeap<Integer, Value> heapTable) {
        this.heapTable = heapTable;
    }
    public void setExecutionStack(MyIStack<IStatement> executionStack2) {
        this.executionStack = executionStack2;
    }
    public IStatement getStatement() {
        return originalProgram;
    }

    public void setBarrierTable(MyIHeap<Integer, Pair<Integer, ArrayList<Integer>>> barrirer) {this.barrierTable = barrirer;}

    public void setSymbolsTable(MyIDictionary<String, Value> symbolsTable) {
        this.symbolTable = symbolsTable;
    }

    public void setOut(MyIList<Value> out) {
        this.output = out;
    }

    public void setFileTable(MyIDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public Boolean isNotCompleted()
    {
        if(executionStack.isEmpty())
            return false;
        return true;
    }

    public synchronized int getnewID()
    {
        lastID++;
        return lastID;
    }

    public ProgramState oneStep() throws MyException {
        if(executionStack.isEmpty())
        {
            throw new MyException("Stack is empty no more executions");
        }
        IStatement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }


    @Override
    public String toString() {
        return  "ProgramState: "+ prgStateId+ "\n"+
                "ExectutionStack:\n" +
                executionStack.toString() + "\n" +
                "SymbolTable:\n" +
                symbolTable.toString() + "\n" +
                "OutputList:\n" +
                output.toString() + "\n" +
                "FileTable:\n"+
                fileTable.toString()+"\n"+
                "HeapTable:\n"+
                heapTable.toString()+"\n"+
                "BarrierTable:\n"+
                barrierTable.toString()+"\n"+
                "\n\n\n";
    }
}
