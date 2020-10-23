package model.type;

public class NumberType implements TypeInterface {
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        return object instanceof NumberType;
    }

    @Override
    public String get() {
        return "number";
    }

    @Override
    public Integer getDefaultValue() {
        return 0;
    }
}
