package Model.Type;

import Model.Value.Value;

public interface Type {
    public boolean equals(Object o);
    public Value defaultValue();
    public Type deepCopy();
}
