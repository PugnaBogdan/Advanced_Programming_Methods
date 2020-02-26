package Model.Statement.File;

import Model.Collection.Dictionary.MyIDictionary;
import Model.Exceptions.MyException;
import Model.Expresions.Expression;
import Model.ProgramState;
import Model.Statement.IStatement;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.Value;
import Model.Value.IntValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class readFile implements IStatement {
    private Expression expression;
    private String var_name;

    public readFile(Expression var, String var_name){
        this.expression = var;
        this.var_name = var_name;
    }

    @Override
    public String toString() {
        return "readFile("+ expression.toString() +", "+ var_name +")";
    }

    public void setVar_name(String var_name) {
        this.var_name = var_name;
    }

    public String getVar_name() {
        return var_name;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        if(symbolTable.isDefined(var_name)){
            if (!symbolTable.get(var_name).getType().equals(new IntType())){
                throw new MyException("Invalid variable type" + "\n");
            }
        }
        else{
            throw new MyException("Invalid variable name" + "\n");
        }

        Value value = expression.evaluate(state.getSymbolTable(),state.getHeapTable());

        if(!value.getType().equals(new StringType()))
            throw new MyException("Expression on ReadFile is not a StringType\n");

        StringValue x = (StringValue) value;
        String line;
        try{
            BufferedReader br = new BufferedReader(fileTable.get(x));
            line = br.readLine();
            if(line!=null){
                Integer t = Integer.parseInt(line);
                symbolTable.put(var_name, new IntValue(t));
                fileTable.put(x, br);
            }
            else{
                Integer d = 0;
                symbolTable.put(var_name,new IntValue(d));
                fileTable.put(x, br);
            }
        }
        catch (IOException exception)
        {
            throw new MyException("No entry associated to this value in the FileTable"+"\n");
        }


        return null;
    }
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        Type VariableType = typeEnv.get(var_name);
        Type ExpressionType = expression.typecheck(typeEnv);
        if (VariableType.equals(new IntType()))
        {
            if(ExpressionType.equals(new StringType()))
                return typeEnv;
            else
                throw new MyException("Read ");
        }
        else
            throw new MyException("File name not a string ");
    }

    public IStatement deepCopy()
    {
        return new readFile(expression,new String(var_name));
    }
}
