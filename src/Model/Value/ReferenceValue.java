package Model.Value;

import Model.Type.*;
import Model.Type.Type;

public class ReferenceValue implements Value {
    private int address;
    private Type locationType;
    public ReferenceValue(int x, Type b) {
        address=x;
        locationType=b;
    }
    public int getAddr() {return address;}
    public void setAddress(int address) {
        this.address = address;
    }
    public Type getType() {
        return new ReferenceType(locationType);
    }
    public Type getLocationType(){
        return locationType;
    }
    public String toString()
    {
        return "("+Integer.toString(address)+","+locationType.toString()+")";
    }
    public Value deepCopy() {
        return new ReferenceValue(new Integer(address),locationType.deepCopy());
    }
}
