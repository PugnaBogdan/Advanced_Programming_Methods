package Model.Statement.File;


import Model.Collection.Dictionary.MyIDictionary;
import Model.Collection.Stack.MyIStack;
import Model.Exceptions.MyException;
import Model.Expresions.Expression;
import Model.ProgramState;
import Model.Statement.IStatement;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.Value;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class closeFile implements IStatement {
    private Expression var;
    public closeFile(Expression e) {
        var = e;}

    public Expression getVar() {
        return var;
    }

    public void setVar(Expression var) {
        this.var = var;
    }

    @Override
    public String toString() {
        return " closeFile(" + var.toString() + ")";
    }

    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack = state.getStack();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();


        Value value = var.evaluate(state.getSymbolTable(),state.getHeapTable());
        StringValue x = (StringValue) value;
        try{
            BufferedReader br = new BufferedReader(new FileReader(x.getValue()));
            br.close();
            fileTable.remove(x);
        }
        catch (IOException exception)
        {
            throw new MyException(" no entry associated to this value in the FileTable");
        }
        return null;
    }

    public  MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type ExpressionType = var.typecheck(typeEnv);
        if (ExpressionType.equals(new StringType())) {
            return typeEnv;
        }
        else
            throw new MyException("The Closing expression type is not a string");
    }


    public IStatement deepCopy()
    {
        return new closeFile(var);
    }
}

