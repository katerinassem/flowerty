package by.itechart.flowerty.persistence.model.strategy;

import by.itechart.flowerty.persistence.model.AvailableStatesStrategy;
import by.itechart.flowerty.persistence.model.Role;
import by.itechart.flowerty.persistence.model.State;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Катерина on 08.06.2015.
 */

@JsonSerialize
public class ReadyStateStrategy implements AvailableStatesStrategy {

    @Override
    public List<State> getAvailableOrderStates(String roleDescription) {
        ArrayList<State> states = new ArrayList<State> ();
        states.add(new State(State.DESCRIPTION_TYPE.CANCELED));
        states.add(new State(State.DESCRIPTION_TYPE.IMPOSSIBLE));
        if(StringUtils.equalsIgnoreCase(roleDescription, Role.ROLE_TYPE.DELIVERY_MANAGER.toString())) {
            states.add(new State(State.DESCRIPTION_TYPE.DELIVERY));
        }
        return states;
    }
}
