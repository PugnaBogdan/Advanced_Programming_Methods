package Model.Value;

import Model.Type.Type;

public interface Value {

    public Type getType();
    public boolean equals(Object object);
    Value deepCopy();
}
