package model.value;

import model.type.NumberType;
import model.type.TypeInterface;

public class IntegerValue implements ValueInterface {
    private int value;
    private final NumberType type = new NumberType();

    public IntegerValue() {
        setValue(type.getDefaultValue());
    }

    public IntegerValue(int value) {
        setValue(value);
    }

    @Override
    public TypeInterface getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
