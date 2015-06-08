package by.itechart.flowerty.persistence.model.strategy;

import by.itechart.flowerty.persistence.model.AvailableStatesStrategy;
import by.itechart.flowerty.persistence.model.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Катерина on 08.06.2015.
 *
 * Strategy for order's states that cannot be changed to any other
 */

public class NoGoStateStrategy implements AvailableStatesStrategy {

    @Override
    public List<State> getAvailableOrderStates(String roleDescription) {
        return new ArrayList<>();
    }
}
