package Model.Statement;

import Model.Collection.Dictionary.MyIDictionary;
import Model.ProgramState;
import Model.Type.*;
import Model.Exceptions.MyException;
import Model.Value.*;

public class VariableDeclarationStatement implements IStatement {
    private String name;
    private Type type;

    public VariableDeclarationStatement(String name, Type type) {
        this.name = name;
        this.type = type;
    }
    public String toString(){
        return type.toString()+" "+name;
    }
    public ProgramState execute(ProgramState state) throws MyException {

        MyIDictionary<String, Value> symbolTable=state.getSymbolTable();
        symbolTable.put(name, this.type.defaultValue());
        return null;
//        MyIDictionary<String, Value> symTbl = state.getSymbolTable();
//        if(symTbl.isDefined(name))
//        {
//            throw new MyException("It is already defined");
//        }
//        else
//        {
//            if(type.equals(new BoolType()))
//            {
//                BoolValue bool=(BoolValue) type.defaultValue();
//                symTbl.put(name,bool);
//            }
//            else if(type.equals(new IntType())) {
//                IntValue inter = (IntValue) type.defaultValue();
//                symTbl.put(name, inter);
//            }
//            else if(type.equals(new StringType()))
//            {
//                StringValue string = (StringValue) type.defaultValue();
//                symTbl.put(name, string);
//            }
//            else
//            {
//                ReferenceValue reference= (ReferenceValue) type.defaultValue();
//                symTbl.put(name,reference);
//            }
//
//        }
//        return null;
    }

    public MyIDictionary<String,Type> typecheck(MyIDictionary<String, Type> typeEnv) throws
            MyException{
        typeEnv.put(name,type);
        return typeEnv;
    }


    public IStatement deepCopy()
    {
        return new VariableDeclarationStatement(new String(name),type.deepCopy());
    }

}
