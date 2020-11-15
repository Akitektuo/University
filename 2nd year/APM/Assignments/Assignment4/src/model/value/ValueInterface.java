package model.value;

import model.type.TypeInterface;

public interface ValueInterface {
    TypeInterface getType();

    Object getValue();

    String getValueAsString();
}
