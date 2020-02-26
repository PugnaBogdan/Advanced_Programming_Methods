package Model.Value;

import Model.Type.StringType;
import Model.Type.Type;

public class StringValue implements Value {
    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    public Type getType() {
        return new StringType();
    }
    public String getValue(){
        return value;
    }
    public String toString(){
        return value;
    }
    public boolean equals(String object){
        return object.equals(value);
    }
    public Value deepCopy()
    {
        return new StringValue(new String(value));
    }
}
