package model.value;

import model.type.NumberType;
import model.type.TypeInterface;

public class IntegerValue implements ValueInterface {
    private int value;
    private final NumberType type = new NumberType();

    public IntegerValue() {
        setValue(type.getDefaultValue().getValue());
    }

    public IntegerValue(int value) {
        setValue(value);
    }

    @Override
    public TypeInterface getType() {
        return type;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getValueAsString() {
        return String.valueOf(value);
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof IntegerValue)) {
            return false;
        }
        return ((IntegerValue) object).value == value;
    }
}
