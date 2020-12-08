package model.value;

import model.type.StringType;
import model.type.TypeInterface;

public class StringValue implements ValueInterface {
    private String value;
    private final StringType type = new StringType();

    public StringValue() {
        setValue(type.getDefaultValue().getValue());
    }

    public StringValue(String value) {
        setValue(value);
    }

    @Override
    public TypeInterface getType() {
        return type;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getValueAsString() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("\"%s\"", value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof StringValue)) {
            return false;
        }
        return ((StringValue) object).value.equals(value);
    }
}
