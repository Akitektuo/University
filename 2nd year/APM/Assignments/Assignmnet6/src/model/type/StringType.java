package model.type;

import model.value.StringValue;

public class StringType implements  TypeInterface {
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        return object instanceof StringType;
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public Types get() {
        return Types.STRING;
    }

    @Override
    public StringValue getDefaultValue() {
        return new StringValue("");
    }
}
