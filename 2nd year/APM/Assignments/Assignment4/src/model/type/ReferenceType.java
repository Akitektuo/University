package model.type;

import model.value.ReferenceValue;

public class ReferenceType implements TypeInterface {
    private final TypeInterface genericType;

    public ReferenceType(TypeInterface genericType) {
        this.genericType = genericType;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ReferenceType)) {
            return false;
        }
        var otherGenericType = ((ReferenceType) object).genericType;
        if (genericType == null && otherGenericType == null) {
            return true;
        }
        if (genericType == null) {
            return false;
        }
        return genericType.equals(otherGenericType);
    }

    @Override
    public String toString() {
        if (genericType == null) {
            return "Reference";
        }
        return String.format("Reference<%s>", genericType);
    }

    @Override
    public Types get() {
        return Types.REFERENCE;
    }

    @Override
    public ReferenceValue getDefaultValue() {
        return new ReferenceValue(0, genericType);
    }

    public TypeInterface getGenericType() {
        return genericType;
    }
}
