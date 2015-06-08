package by.itechart.flowerty.persistence.model;

import by.itechart.flowerty.persistence.model.strategy.NoGoStateStrategy;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Катерина on 08.06.2015.
 */
public class StateStrategyFactory {
    
    public AvailableStatesStrategy getStateStrategy(State currentState){
        if(currentState.getDescription() == State.DESCRIPTION_TYPE.CLOSED
                || currentState.getDescription() == State.DESCRIPTION_TYPE.CANCELED){
            return new NoGoStateStrategy();
        }
        String strategyName = new StringBuilder("by.itechart.flowerty.persistence.model.strategy.").append(StringUtils.capitalize(StringUtils.lowerCase(currentState.getDescription().name()))).append("StateStrategy").toString();
        try {
            Class clazz = Class.forName(strategyName);
            return (AvailableStatesStrategy)clazz.newInstance();
        }
        catch (ClassNotFoundException exception){
            return new NoGoStateStrategy();
        }
        catch (IllegalAccessException exception){
            return new NoGoStateStrategy();
        }
        catch (InstantiationException exception){
            return new NoGoStateStrategy();
        }
    }
}
