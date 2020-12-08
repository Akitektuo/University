package model.type;

import model.value.ValueInterface;

public interface TypeInterface {
    Types get();

    ValueInterface getDefaultValue();
}
