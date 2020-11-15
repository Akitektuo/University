package model.value;

import model.type.BooleanType;
import model.type.TypeInterface;

public class BooleanValue implements ValueInterface {
    private boolean value;
    private final BooleanType type = new BooleanType();

    public BooleanValue() {
        setValue(type.getDefaultValue().getValue());
    }

    public BooleanValue(boolean value) {
        setValue(value);
    }

    @Override
    public TypeInterface getType() {
        return type;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public String getValueAsString() {
        return String.valueOf(value);
    }

    public void setValue(boolean value) {
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
        if (!(object instanceof BooleanValue)) {
            return false;
        }
        return ((BooleanValue) object).value == value;
    }
}
