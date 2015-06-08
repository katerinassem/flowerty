package by.itechart.flowerty.persistence.model.strategy;

import by.itechart.flowerty.persistence.model.AvailableStatesStrategy;
import by.itechart.flowerty.persistence.model.Role;
import by.itechart.flowerty.persistence.model.State;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Катерина on 08.06.2015.
 */

public class ImpossibleStateStrategy implements AvailableStatesStrategy {

    @Override
    public List<State> getAvailableOrderStates(String roleDescription) {
        ArrayList<State> states = new ArrayList<State> ();
        states.add(new State(State.DESCRIPTION_TYPE.CANCELED));
        if(StringUtils.equalsIgnoreCase(roleDescription, Role.ROLE_TYPE.SUPERVISOR.toString())) {
            states.add(new State(State.DESCRIPTION_TYPE.NEW));
        }
        return states;
    }
}
