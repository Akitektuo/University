package model.value;

import container.List;
import container.ListInterface;
import container.IdDictionary;
import model.type.ReferenceType;
import model.type.TypeInterface;
import model.type.Types;

public class ReferenceValue implements ValueInterface {
    private final TypeInterface genericType;
    private final ReferenceType type;
    private int value;
    private boolean isAddressVerified = false;

    public ReferenceValue(int value, TypeInterface genericType) {
        setValue(value);
        this.genericType = genericType;
        type = new ReferenceType(genericType);
    }

    @Override
    public TypeInterface getType() {
        return type;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String getValueAsString() {
        return String.valueOf(value);
    }

    @Override
    public String toString() {
        return String.format("@%d", value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ReferenceValue)) {
            return false;
        }
        return ((ReferenceValue) object).value == value;
    }

    public ListInterface<Integer> getRelatedAddresses(IdDictionary<ValueInterface> memoryHeap) {
        if (isAddressVerified) {
            return new List<>();
        }
        if (genericType.get() != Types.REFERENCE) {
            return new List<>(getValue());
        }

        var referenceValue = (ReferenceValue) memoryHeap.get(getValue());
        if (referenceValue == null) {
            return new List<>();
        }

        isAddressVerified = true;
        var addresses = referenceValue.getRelatedAddresses(memoryHeap);
        addresses.add(getValue());
        isAddressVerified = false;

        return addresses;
    }

    public TypeInterface getGenericType() {
        return genericType;
    }
}
