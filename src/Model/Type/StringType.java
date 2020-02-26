package Model.Type;

import Model.Value.StringValue;
import Model.Value.Value;

public class StringType implements Type {
    public StringType(){}

    public String toString(){
        return "string ";
    }

    @Override
    public boolean equals(Object another){
        return another instanceof StringType;
    }

    public Value defaultValue() {
        return new StringValue("");
    }
    public Type deepCopy()
    {
        return new StringType();
    }
}
