package model.type;

import model.value.BooleanValue;

public class BooleanType implements TypeInterface {
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        return object instanceof BooleanType;
    }

    @Override
    public String toString() {
        return "boolean";
    }

    @Override
    public Types get() {
        return Types.BOOLEAN;
    }

    @Override
    public BooleanValue getDefaultValue() {
        return new BooleanValue(false);
    }
}
