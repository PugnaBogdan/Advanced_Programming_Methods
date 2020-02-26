package Model.Statement;

import Model.Collection.Dictionary.MyDictionary;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Statement.Heap.MyIHeap;
import Model.Statement.IStatement;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;
import Model.Collection.Dictionary.MyIDictionary;
import Model.Collection.List.MyIList;

import java.io.BufferedReader;
import java.util.Map;

public class ForkStatement implements IStatement{
    IStatement forked_statement;
    public ForkStatement(IStatement fork)
    {
        this.forked_statement=fork;
    }

    public IStatement deepCopy() {
        return new ForkStatement(forked_statement.deepCopy());
    }

    public ProgramState execute(ProgramState state)
    {
        //return new ProgramState( state.getStack(), state.getSymbolTable(), state.getOutput(), state.getHeapTable(), state.getFileTable(), this.forked_statement);
        MyIDictionary<String, Value> symbolsTable = state.getSymbolTable();
        MyIHeap<Integer, Value> heap = state.getHeapTable();
        MyIList<Value> output = state.getOutput();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyDictionary<String, Value> newSymbolsTable = new MyDictionary<String, Value>();
        for (Map.Entry<String, Value> entry: symbolsTable.getValues().entrySet()) {
            newSymbolsTable.put(new String(entry.getKey()), entry.getValue().deepCopy());
    }
        ProgramState new_state=new ProgramState(forked_statement);
        //new_state.setExecutionStack(state.getStack());
        new_state.setSymbolsTable(newSymbolsTable);
        new_state.setFileTable(fileTable);
        new_state.setHeapTable(heap);
        new_state.setOut(output);
        return new_state;
    }

    @Override
    public  MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        forked_statement.typecheck(typeEnv);
        return typeEnv;
    }


    @Override
    public String toString()
    {
        return "fork(" + forked_statement + ")";
    }
}
