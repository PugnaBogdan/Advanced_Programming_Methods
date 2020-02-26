package Model.Type;

import Model.Value.IntValue;
import Model.Value.Value;

public class IntType implements Type {
    public IntType() {}

    @Override
    public boolean equals(Object another){
        return another instanceof IntType;
    }

    public Value defaultValue() {
        return new IntValue(0);
    }

    public String toString() { return "int";}
    public Type deepCopy()
    {
        return new  IntType();
    }
}
