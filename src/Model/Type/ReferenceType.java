package Model.Type;

import Model.Value.*;
import Model.Value.Value;

public class ReferenceType implements Type {
    private Type inner;
    public ReferenceType(){}
    public ReferenceType(Type inner) {this.inner=inner;}
    public Type getInner() {return inner;}
    public boolean equals(Object another){
        return another instanceof ReferenceType;
    }
    public String toString() { return "Ref(" +inner.toString()+")";}
    public Value defaultValue() { return new ReferenceValue(0,inner);}
    public Type deepCopy() {
        return new ReferenceType(inner.deepCopy());
    }
}
