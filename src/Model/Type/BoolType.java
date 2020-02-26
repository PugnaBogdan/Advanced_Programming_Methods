package Model.Type;

import Model.Value.BoolValue;
import Model.Value.Value;

public class BoolType implements Type {
    public BoolType() {}

    @Override
    public boolean equals(Object another){
        return another instanceof BoolType;
    }

    public Value defaultValue() {
        return new BoolValue(true);
    }

    public String toString() { return "int";}

    public Type deepCopy()
    {
        return new BoolType();
    }
}
