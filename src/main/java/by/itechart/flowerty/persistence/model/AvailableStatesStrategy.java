package by.itechart.flowerty.persistence.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Катерина on 08.06.2015.
 *
 * Strategy, that provides rules of changing the order's state (status)
 */

public interface AvailableStatesStrategy extends Cloneable, Serializable {

    List<State> getAvailableOrderStates(String roleDescription);
}
