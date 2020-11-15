package model.expression;

import model.ProgramState;
import model.value.ValueInterface;

public interface ExpressionInterface {
    ValueInterface evaluate(ProgramState programState) throws ExpressionException;
}
