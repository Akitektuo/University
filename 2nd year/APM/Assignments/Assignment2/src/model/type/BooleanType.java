package model.type;

public class BooleanType implements TypeInterface {
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        return object instanceof BooleanType;
    }

    @Override
    public String get() {
        return "boolean";
    }

    @Override
    public java.lang.Boolean getDefaultValue() {
        return false;
    }
}
