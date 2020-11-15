package model.type;

import model.value.IntegerValue;

public class NumberType implements TypeInterface {
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        return object instanceof NumberType;
    }

    @Override
    public String toString() {
        return "number";
    }

    @Override
    public Types get() {
        return Types.NUMBER;
    }

    @Override
    public IntegerValue getDefaultValue() {
        return new IntegerValue(0);
    }
}
