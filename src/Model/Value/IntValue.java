package Model.Value;

import Model.Type.Type;
import Model.Type.IntType;

public class IntValue implements Value {
    private int value;
    public IntValue(int v){
        value=v;
    }
    public int getValue(){
        return this.value;
    }
    public String toString(){
        return String.valueOf(value);
    }
    public Type getType() {
        return new IntType();
    }
    public boolean equals(int object){
        return object==value;
    }
    public Value deepCopy(){return new IntValue(new Integer(value));}
}
