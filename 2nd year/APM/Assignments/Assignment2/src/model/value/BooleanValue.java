package model.value;

import model.type.BooleanType;
import model.type.TypeInterface;

public class BooleanValue implements ValueInterface {
    private boolean value;
    private final BooleanType type = new BooleanType();

    public BooleanValue() {
        setValue(type.getDefaultValue());
    }

    public BooleanValue(boolean value) {
        setValue(value);
    }

    @Override
    public TypeInterface getType() {
        return type;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
