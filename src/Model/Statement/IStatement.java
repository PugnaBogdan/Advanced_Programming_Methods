package Model.Statement;
import Model.Collection.Dictionary.MyIDictionary;
import Model.ProgramState;
import Model.Exceptions.*;
import Model.Type.Type;

import java.time.chrono.IsoChronology;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException;
    MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException;
    IStatement deepCopy();
}
